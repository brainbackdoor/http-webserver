package web.protocol.ethernet;

import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.ip.Flag;
import web.protocol.ip.IpPacket;
import web.protocol.ip.IpPacket.IpHeader;
import web.tool.dump.TcpDump;
import web.tool.sniffer.NetworkInterface;
import web.tool.sniffer.NetworkInterfaceService;
import web.tool.sniffer.PacketHandler;
import web.tool.sniffer.PacketNativeException;
import web.util.ByteUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static web.protocol.ethernet.EthernetPacket.EthernetHeader;
import static web.protocol.ethernet.EthernetPacket.EthernetHeader.DST_ADDR_OFFSET;
import static web.protocol.ethernet.EthernetPacket.EthernetHeader.SRC_ADDR_OFFSET;
import static web.protocol.ethernet.EthernetPacket.EthernetHeader.TYPE_OFFSET;
import static web.protocol.ip.ProtocolIdentifier.TCP;
import static web.protocol.ip.Version.IPV4;

public class PacketTestHelper {
    private static final String PCAP_FILE_KEY = EthernetPacketTest.class.getName() + ".pcapFile";
    private static final String PCAP_FILE = System.getProperty(PCAP_FILE_KEY, "Dump.pcap");

    public static EthernetHeader createEthernetHeader(Type protocolType) {
        MacAddress src = MacAddress.getByName("00:00:00:00:00:01");
        MacAddress dst = MacAddress.ETHER_BROADCAST_ADDRESS;

        return new EthernetHeader(dst, src, protocolType);
    }

    public static Packet createEthernetPacket(byte[] rawData) {
        MacAddress dstAddr = ByteUtils.getMacAddress(rawData, DST_ADDR_OFFSET);
        MacAddress srcAddr = ByteUtils.getMacAddress(rawData, SRC_ADDR_OFFSET);
        Type type = Type.getInstance(ByteUtils.getShort(rawData, TYPE_OFFSET));

        EthernetHeader header = new EthernetHeader(dstAddr, srcAddr, type);
        return new EthernetPacket(header, new SimplePacket());
    }

    public static IpHeader createIpHeader() throws UnknownHostException {
        Inet4Address src = (Inet4Address) InetAddress.getByAddress(new byte[]{(byte) 192, (byte) 0, (byte) 2, (byte) 1});
        Inet4Address dst = (Inet4Address) InetAddress.getByAddress(new byte[]{(byte) 168, (byte) 126, (byte) 63, (byte) 1});
        return IpHeader.builder()
                .version(IPV4)
                .tos(IpV4TosTest.of())
                .protocolIdentifier(TCP)
                .flag(Flag.DONT_FRAGMENT)
                .srcAddr(src)
                .dstAddr(dst)
                .build();
    }

    public static PacketHandler getHandler() throws Exception {
        NetworkInterface nif = NetworkInterfaceService.findByName("en0");
        return nif.openLive(65536, NetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
    }

    public static byte[] read() throws PacketNativeException {
        PacketHandler handler = TcpDump.openOffline(PCAP_FILE);
        byte[] rawData = handler.getNextRawPacket();
        handler.close();
        return rawData;
    }

    public static void save(PacketHandler handler, EthernetPacket packet) throws PacketNativeException {
        TcpDump dumper = handler.dumpOpen(PCAP_FILE);
        dumper.dump(packet);
        dumper.close();
    }

    public static class IpV4TosTest implements IpPacket.Tos {

        public static IpPacket.Tos of() {
            return new IpV4TosTest();
        }

        @Override
        public byte value() {
            return 0x01;
        }
    }

}
