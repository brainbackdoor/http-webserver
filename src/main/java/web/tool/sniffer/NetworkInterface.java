package web.tool.sniffer;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pcap4j.core.Inets;
import web.protocol.ethernet.MacAddress;
import web.util.ByteUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static web.tool.sniffer.PacketHandler.TimestampPrecision.MICRO;

@Getter
public class NetworkInterface {
    private static final int LOOPBACK_CONDITION = 0x00000001;
    private static final int UP_CONDITION = 0x00000002;
    private static final int RUNNING_CONDITION = 0x00000004;

    private final List<MacAddress> macAddresses = new ArrayList<>();
    private final String name;
    private final String description;
    private final boolean loopBack;
    private final boolean up;
    private final boolean running;
    private final boolean local;

    private NetworkInterface(NativeMappings.pcap_if pif, boolean local) {
        this.name = pif.getName();
        this.description = pif.getDescription();

        for (NativeMappings.pcap_addr pcapAddr = pif.getAddresses(); pcapAddr != null; pcapAddr = pcapAddr.getNext()) {
            if (pcapAddr.getAddr() == null
                    && pcapAddr.getNetmask() == null
                    && pcapAddr.getBroadaddr() == null
                    && pcapAddr.getDstaddr() == null) {
                continue;
            }

            short sa_family = getSaFamily(pcapAddr); // Never get here.


            if (Platform.isLinux() && sa_family == Inets.AF_PACKET) {
                NativeMappings.sockaddr_ll sll = new NativeMappings.sockaddr_ll(pcapAddr.getAddr().getPointer());
                byte[] addr = sll.getSll_addr();
                int addrLength = sll.getSll_halen() & 0xFF;
                if (addrLength == 6) {
                    macAddresses.add(ByteUtils.getMacAddress(addr, 0));
                } else if (addr.length == 0) {
                    continue;
                } else {
                    addrLength = addrLength <= addr.length ? addrLength : addr.length;
                    macAddresses.add(
                            MacAddress.getByAddress(ByteUtils.getSubArray(addr, 0, addrLength)));
                }
            } else if ((Platform.isMac() || Platform.isFreeBSD() || Platform.isOpenBSD())
                    || Platform.iskFreeBSD() && sa_family == Inets.AF_LINK) {
                NativeMappings.sockaddr_dl sdl = new NativeMappings.sockaddr_dl(pcapAddr.getAddr().getPointer());
                byte[] addr = sdl.getAddress();
                if (addr.length == 6) {
                    macAddresses.add(MacAddress.getByAddress(addr));
                } else if (addr.length == 0) {
                    continue;
                } else {
                    macAddresses.add(MacAddress.getByAddress(addr));
                }
            }
        }

        this.loopBack = (pif.getFlags() & LOOPBACK_CONDITION) != 0;
        this.up = (pif.getFlags() & UP_CONDITION) != 0;
        this.running = (pif.getFlags() & RUNNING_CONDITION) != 0;
        this.local = local;
    }

    private short getSaFamily(NativeMappings.pcap_addr pcapAddr) {
        return pcapAddr.getAddr() != null
                ? pcapAddr.getAddr().getSaFamily()
                : pcapAddr.getNetmask() != null
                ? pcapAddr.getNetmask().getSaFamily()
                : pcapAddr.getBroadaddr() != null
                ? pcapAddr.getBroadaddr().getSaFamily()
                : pcapAddr.getDstaddr() != null
                ? pcapAddr.getDstaddr().getSaFamily()
                /* default */ : Inets.AF_UNSPEC;
    }

    static NetworkInterface of(NativeMappings.pcap_if pif, boolean local) {
        return new NetworkInterface(pif, local);
    }

    @Getter
    @NoArgsConstructor
    public static class Errbuf extends Structure {

        public byte[] buf = new byte[DEFAULT_ERR_BUF_SIZE];

        private static final int DEFAULT_ERR_BUF_SIZE = 256;

        public int length() {
            return toString().length();
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("buf");
        }

        @Override
        public String toString() {
            return Native.toString(buf);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum PromiscuousMode {
        PROMISCUOUS(1),
        NON_PROMISCUOUS(0);

        private final int value;
    }

    public PacketHandler openLive(int snaplen, PromiscuousMode mode, int timeoutMillis) throws Exception {
        Errbuf errbuf = new Errbuf();
        Pointer handle = NativeMappings.pcap_open_live(name, snaplen, mode.getValue(), timeoutMillis, errbuf);

        if (isNullOrBlank(errbuf, handle)) {
            throw new PacketNativeException("Handler 혹은 ErrBuf가 비어있습니다");
        }

        return PacketHandler.builder()
                .handle(handle)
                .timestampPrecision(MICRO)
                .build();
    }

    private boolean isNullOrBlank(Errbuf errbuf, Pointer handle) {
        return handle == null || errbuf.length() != 0;
    }
}
