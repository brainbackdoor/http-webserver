package web.protocol.ethernet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.arp.ArpPacket;
import web.protocol.ethernet.EthernetPacket.EthernetHeader;
import web.protocol.ip.IpPacket;
import web.tool.sniffer.PacketHandler;
import web.tool.sniffer.PacketNativeException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.ethernet.PacketTestHelper.*;
import static web.protocol.ethernet.Type.ARP;
import static web.protocol.ethernet.Type.IPV4;

class EthernetPacketTest {
    PacketHandler handler;

    @BeforeEach
    void setUp() throws Exception {
        handler = getHandler();
    }

    @Test
    @DisplayName("Ethernet Packet을 생성한다.")
    void constructor() {
        EthernetHeader header = createEthernetHeader(ARP);
        EthernetPacket packet = new EthernetPacket(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }

    @Test
    @DisplayName("Ethernet Packet을 전송한다.")
    void send() throws Exception {
        IpPacket ipPacket = new IpPacket(createIpHeader(), new SimplePacket());
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), ipPacket);
        Packet actual = handler.sendPacket(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Ethernet Packet을 pcap 파일에 저장한다.")
    void save() throws PacketNativeException, UnknownHostException {
        IpPacket ipPacket = new IpPacket(createIpHeader(), new SimplePacket());
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), ipPacket);
        PacketTestHelper.save(handler, expected);
        Packet actual = createEthernetPacket(read());

        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
    }

    @Test
    @DisplayName("ARP를 이용하여 공유기 주소를 알아온다.")
    void search() throws PacketNativeException, UnknownHostException {
        ArpPacket.ArpHeader header = ArpPacket.ArpHeader.builder()
                .dstHardwareAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
                .srcHardwareAddr(MacAddress.getByName("38:f9:d3:1a:6e:24"))
                .hardwareType(ArpPacket.HardwareType.ETHERNET)
                .protocolType(IPV4)
                .hardwareAddrLength((byte) 6)
                .protocolAddrLength((byte) 4)
                .opcode(ArpPacket.Opcode.REQUEST)
                .srcProtocolAddr(InetAddress.getByName("3.19.114.185"))
                .dstProtocolAddr(InetAddress.getByName("192.0.2.100"))
                .build();
        ArpPacket arpPacket = new ArpPacket(header);
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), arpPacket);
        Packet actual = handler.sendPacket(expected);

        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
    }

    @AfterEach
    void tearDown() {
        handler.close();
    }
}