package web.protocol.arp;

import org.junit.jupiter.api.*;
import web.protocol.Packet;
import web.protocol.arp.ArpPacket.ArpHeader;
import web.protocol.arp.ArpPacket.HardwareType;
import web.protocol.arp.ArpPacket.Opcode;
import web.protocol.ethernet.EthernetPacket;
import web.protocol.ethernet.MacAddress;
import web.protocol.ethernet.Type;
import web.protocol.helper.PacketTestHelper;
import web.tool.analysis.NetInfo;
import web.util.ByteUtils;

import java.net.Inet4Address;
import java.net.InetAddress;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.arp.ArpPacket.ArpHeader.*;
import static web.protocol.ethernet.EthernetPacket.EthernetHeader.ETHERNET_HEADER_SIZE;
import static web.protocol.ethernet.EthernetPacketTest.buildEthernetHeader;
import static web.protocol.ethernet.EthernetPacketTest.createEthernetHeader;
import static web.protocol.ethernet.Type.ARP;
import static web.protocol.ethernet.Type.IPV4;

class ArpPacketTest extends PacketTestHelper {

    @BeforeEach
    void setUp() throws Exception {
        NetInfo netInfo = new NetInfo();
        nicName = netInfo.getNic();
        macAddress = netInfo.getMacAddress();
        localIp = netInfo.getIp();
        handler = getHandler(nicName);
        listener = packet -> gotPacket(packet);
        packetStorage =  new PacketStorage();
    }

    @Test
    @Disabled
    @DisplayName("ARP를 이용하여 공유기 주소를 알아온다.")
    void search() throws Exception {
        ArpPacket.ArpHeader header = ArpPacket.ArpHeader.builder()
                .dstHardwareAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
                .srcHardwareAddr(MacAddress.getByName(macAddress))
                .hardwareType(ArpPacket.HardwareType.ETHERNET)
                .protocolType(IPV4)
                .hardwareAddrLength((byte) 6)
                .protocolAddrLength((byte) 4)
                .opcode(ArpPacket.Opcode.REQUEST)
                .srcProtocolAddr(InetAddress.getByName(localIp))
                .dstProtocolAddr(InetAddress.getByName("3.19.114.185"))
                .build();
        ArpPacket arpPacket = new ArpPacket(header);
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), arpPacket);
        handler.sendPacket(expected);
        handler.loop(5, listener);

        assertThat(packetStorage.exist(expected)).isTrue();
    }

    public static Packet buildArpPacket(byte[] rawData) {
        EthernetPacket.EthernetHeader ethernetHeader = buildEthernetHeader(rawData);
        ArpHeader arpHeader = buildArpHeader(rawData);

        ArpPacket arpPacket = new ArpPacket(arpHeader);
        return new EthernetPacket(ethernetHeader, arpPacket);
    }

    private static ArpHeader buildArpHeader(byte[] rawData) {
        HardwareType hardwareType = HardwareType.getInstance(ByteUtils.getShort(rawData, ETHERNET_HEADER_SIZE + HARDWARE_TYPE_OFFSET));
        Type protocolType = Type.getInstance(ByteUtils.getShort(rawData, ETHERNET_HEADER_SIZE + PROTOCOL_TYPE_OFFSET));
        byte hardwareAddrLength = ByteUtils.getByte(rawData, ETHERNET_HEADER_SIZE + HW_ADDR_LENGTH_OFFSET);
        byte protocolAddrLength = ByteUtils.getByte(rawData, ETHERNET_HEADER_SIZE + PROTO_ADDR_LENGTH_OFFSET);
        Opcode opcode = Opcode.getInstance(ByteUtils.getShort(rawData, ETHERNET_HEADER_SIZE + OPERATION_OFFSET));
        MacAddress srcHardwareAddr = ByteUtils.getMacAddress(rawData, ETHERNET_HEADER_SIZE + SRC_HARDWARE_ADDR_OFFSET);
        Inet4Address srcProtocolAddr = ByteUtils.getInet4Address(rawData, ETHERNET_HEADER_SIZE + SRC_PROTOCOL_ADDR_OFFSET);
        MacAddress dstHardwareAddr = ByteUtils.getMacAddress(rawData, ETHERNET_HEADER_SIZE + DST_HARDWARE_ADDR_OFFSET);
        Inet4Address dstProtocolAddr = ByteUtils.getInet4Address(rawData, ETHERNET_HEADER_SIZE + DST_PROTOCOL_ADDR_OFFSET);

        return ArpHeader.builder()
                .hardwareType(hardwareType)
                .protocolType(protocolType)
                .hardwareAddrLength(hardwareAddrLength)
                .protocolAddrLength(protocolAddrLength)
                .opcode(opcode)
                .srcHardwareAddr(srcHardwareAddr)
                .srcProtocolAddr(srcProtocolAddr)
                .dstHardwareAddr(dstHardwareAddr)
                .dstProtocolAddr(dstProtocolAddr)
                .build();
    }

    private void gotPacket(byte[] raw) {
        Packet packet = buildArpPacket(raw);
        packetStorage.add(packet);
    }

    @AfterEach
    void tearDown() {
        handler.close();
    }
}