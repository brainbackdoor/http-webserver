package web.protocol.ethernet;

import lombok.ToString;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.ip.Flag;
import web.protocol.ip.IpPacket;
import web.protocol.ip.IpPacket.IpHeader;
import web.protocol.tcp.TcpPacket;
import web.protocol.tcp.TcpPort;
import web.protocol.tcp.option.*;
import web.tool.dump.TcpDump;
import web.tool.sniffer.NetworkInterface;
import web.tool.sniffer.NetworkInterfaceService;
import web.tool.sniffer.PacketHandler;
import web.tool.sniffer.PacketNativeException;
import web.util.ByteUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static web.protocol.ethernet.EthernetPacket.EthernetHeader;
import static web.protocol.ethernet.EthernetPacket.EthernetHeader.*;
import static web.protocol.ip.ProtocolIdentifier.TCP;
import static web.protocol.ip.Version.IPV4;
import static web.protocol.tcp.Flag.SYN;

public class PacketTestHelper {
    private static final String PCAP_FILE_KEY = EthernetPacketTest.class.getName() + ".pcapFile";
    private static final String PCAP_FILE = System.getProperty(PCAP_FILE_KEY, "Dump.pcap");

    public static EthernetHeader createEthernetHeader(Type protocolType) {
        MacAddress src = MacAddress.getByName("38:f9:d3:1a:6e:24");
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
        Inet4Address src = (Inet4Address) InetAddress.getByName("192.168.6.175");
        Inet4Address dst = (Inet4Address) InetAddress.getByName("3.19.114.185");
        return IpHeader.builder()
                .version(IPV4)
                .ihl((byte) 5)
                .totalLength((short) 80)
                .identification((short) 2)
                .tos(IpV4TosTest.of())
                .protocolIdentifier(TCP)
                .ttl((byte) 100)
                .flag(Flag.DONT_FRAGMENT)
                .srcAddr(src)
                .dstAddr(dst)
                .build();
    }

    public static TcpPacket.TcpHeader createTcpHeader() {
        List<TcpPacket.TcpOption> options = new ArrayList<>();
        options.add(TcpMaximumSegmentSizeOption.builder().maxSegSize((short) 5555).build());
        options.add(TcpNoOperationOption.getInstance());
        options.add(TcpWindowScaleOption.builder().shiftCount((byte) 2).build());
        options.add(TcpSackPermittedOption.getInstance());
        options.add(TcpTimestampsOption.builder().tsValue(200).tsEchoReply(111).build());

        return TcpPacket.TcpHeader.builder()
                .dstPort(TcpPort.HTTP)
                .srcPort(TcpPort.NONE)
                .sequenceNumber(1)
                .window((short) 65535)
                .offset((byte) 15)
                .flag(SYN)
                .options(options)
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

    @ToString
    public static class IpV4TosTest implements IpPacket.Tos {

        public static IpPacket.Tos of() {
            return new IpV4TosTest();
        }

        @Override
        public byte value() {
            return 0x75;
        }
    }
}
