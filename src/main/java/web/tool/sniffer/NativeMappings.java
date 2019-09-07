package web.tool.sniffer;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NativeMappings {
    private static final ByteOrder NATIVE_BYTE_ORDER = ByteOrder.nativeOrder();

    static final String PCAP_LIB_NAME =
            System.getProperty(
                    NativeMappings.class.getPackage().getName() + ".pcapLibName",
                    Platform.isWindows() ? "wpcap" : "pcap");

    static final Map<String, Object> NATIVE_LOAD_LIBRARY_OPTIONS = new HashMap<>();

    static {
        Native.register(NativeMappings.class, NativeLibrary.getInstance(PCAP_LIB_NAME));

        final Map<String, String> funcMap = new HashMap<>();
        funcMap.put("pcap_set_rfmon", "pcap_set_rfmon");
        funcMap.put("strioctl", "strioctl");
        funcMap.put("dos_pcap_stats_ex", "pcap_stats_ex");
        funcMap.put("win_pcap_stats_ex", "pcap_stats_ex");
        funcMap.put(
                "pcap_open_offline_with_tstamp_precision", "pcap_open_offline_with_tstamp_precision");
        funcMap.put("pcap_open_dead_with_tstamp_precision", "pcap_open_dead_with_tstamp_precision");
        funcMap.put("pcap_set_tstamp_precision", "pcap_set_tstamp_precision");
        funcMap.put("pcap_set_immediate_mode", "pcap_set_immediate_mode");

        NATIVE_LOAD_LIBRARY_OPTIONS.put(
                Library.OPTION_FUNCTION_MAPPER,
                (FunctionMapper) (library, method) -> funcMap.get(method.getName()));
    }

    static native void pcap_close(Pointer p);

    static native int pcap_findalldevs(PointerByReference alldevsp, NetworkInterface.Errbuf errbuf);

    static native void pcap_freealldevs(Pointer alldevsp);

    static native Pointer pcap_open_live(
            String device, int snaplen, int promisc, int to_ms, NetworkInterface.Errbuf errbuf);

    static native int pcap_sendpacket(Pointer p, byte[] buf, int size);

    public static native void pcap_dump_close(Pointer p);

    static native Pointer pcap_dump_open(Pointer p, String fname);

    public static native void pcap_dump(Pointer user, pcap_pkthdr header, byte[] packet);

    public static native Pointer pcap_open_offline(String fname, NetworkInterface.Errbuf errbuf);

    static native int pcap_next_ex(Pointer p, PointerByReference h, PointerByReference data);

    private static short getFamilyByPlatform(short data) {
        return (isWindowsType()) ? data : formatByteOrder(data);
    }

    private static short formatByteOrder(short data) {
        if (NATIVE_BYTE_ORDER.equals(ByteOrder.BIG_ENDIAN)) {
            return (short) (0xFF & data);
        }

        return (short) (0xFF & (data >> 8));
    }

    private static boolean isWindowsType() {
        if (Platform.isMac()
                || Platform.isFreeBSD()
                || Platform.isOpenBSD()
                || Platform.iskFreeBSD()) {
            return false;
        }
        return true;
    }

    @Getter
    @NoArgsConstructor
    public static class pcap_if extends Structure {

        public pcap_if.ByReference next;
        public String name;
        public String description;
        public pcap_addr.ByReference addresses;
        public int flags;

        public pcap_if(Pointer p) {
            super(p);
            read();
        }

        public static class ByReference extends pcap_if implements Structure.ByReference {
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("next", "name", "description", "addresses", "flags");
        }
    }

    @Getter
    @NoArgsConstructor
    public static class pcap_addr extends Structure {

        public pcap_addr.ByReference next;
        public sockaddr.ByReference addr;
        public sockaddr.ByReference netmask;
        public sockaddr.ByReference broadaddr;
        public sockaddr.ByReference dstaddr;

        public pcap_addr(Pointer p) {
            super(p);
            read();
        }

        public static class ByReference extends pcap_addr implements Structure.ByReference {
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("next", "addr", "netmask", "broadaddr", "dstaddr");
        }
    }

    @Getter
    @NoArgsConstructor
    public static class sockaddr extends Structure {
        public short sa_family;
        public byte[] sa_data = new byte[14];

        public sockaddr(Pointer p) {
            super(p);
            read();
        }

        public static class ByReference extends sockaddr implements Structure.ByReference {
        }

        short getSaFamily() {
            return getFamilyByPlatform(sa_family);
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("sa_family", "sa_data");
        }
    }

    @Getter
    @NoArgsConstructor
    public static class sockaddr_ll extends Structure {

        public short sll_family;
        public short sll_protocol;
        public int sll_ifindex;
        public short sll_hatype;
        public byte sll_pkttype;
        public byte sll_halen;
        public byte[] sll_addr = new byte[8];

        public sockaddr_ll(Pointer p) {
            super(p);
            read();
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("sll_family", "sll_protocol", "sll_ifindex", "sll_hatype", "sll_pkttype", "sll_halen", "sll_addr");
        }

        short getSaFamily() {
            return getFamilyByPlatform(sll_family);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class sockaddr_dl extends Structure {

        public byte sdl_len;
        public byte sdl_family;
        public short sdl_index;
        public byte sdl_type;
        public byte sdl_nlen;
        public byte sdl_alen;
        public byte sdl_slen;
        public byte[] sdl_data = new byte[46];

        public sockaddr_dl(Pointer p) {
            super(p);
            read();
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("sdl_len", "sdl_family", "sdl_index", "sdl_type", "sdl_nlen", "sdl_alen", "sdl_slen", "sdl_data");
        }

        byte[] getAddress() {
            return getPointer().getByteArray(8 + (0xFF & sdl_nlen), 0xFF & sdl_alen);
        }
    }

    @NoArgsConstructor
    public static class pcap_pkthdr extends Structure {

        public static final int TS_OFFSET;
        public static final int CAPLEN_OFFSET;
        public static final int LEN_OFFSET;

        public TimeVal ts;
        public int caplen;
        public int len;

        static {
            pcap_pkthdr ph = new pcap_pkthdr();
            TS_OFFSET = ph.fieldOffset("ts");
            CAPLEN_OFFSET = ph.fieldOffset("caplen");
            LEN_OFFSET = ph.fieldOffset("len");
        }

        public pcap_pkthdr(Pointer p) {
            super(p);
            read();
        }

        public static class ByReference extends pcap_pkthdr implements Structure.ByReference {
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("ts", "caplen", "len");
        }

        static NativeLong getTvSec(Pointer p) {
            return p.getNativeLong(TS_OFFSET + TimeVal.TV_SEC_OFFSET);
        }

        static NativeLong getTvUsec(Pointer p) {
            return p.getNativeLong(TS_OFFSET + TimeVal.TV_USEC_OFFSET);
        }

        static int getCaplen(Pointer p) {
            return p.getInt(CAPLEN_OFFSET);
        }

        static int getLen(Pointer p) {
            return p.getInt(LEN_OFFSET);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class TimeVal extends Structure {

        private static final int TV_SEC_OFFSET;
        private static final int TV_USEC_OFFSET;

        public NativeLong tv_sec;
        public NativeLong tv_usec;

        static {
            TimeVal tv = new TimeVal();
            TV_SEC_OFFSET = tv.fieldOffset("tv_sec");
            TV_USEC_OFFSET = tv.fieldOffset("tv_usec");
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("tv_sec", "tv_usec");
        }
    }
}
