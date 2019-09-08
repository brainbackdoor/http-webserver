package web.protocol.tcp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.ethernet.EthernetPacket;
import web.protocol.ethernet.PacketTestHelper;

import web.protocol.ip.IpPacket;
import web.protocol.tcp.TcpPacket.TcpHeader;
import web.tool.sniffer.PacketHandler;
import web.tool.sniffer.PacketNativeException;

import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.ethernet.Type.IPV4;

class TcpPacketTest extends PacketTestHelper {

    PacketHandler handler;

    @BeforeEach
    void setUp() throws Exception {
        handler = getHandler();
    }


    @Test
    @DisplayName("TCP Packet을 생성한다.")
    void constructor() {
        TcpHeader header = createTcpHeader();
        TcpPacket packet = new TcpPacket(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }

    @Test
    @DisplayName("TCP Packet을 전송한다.")
    void send() throws UnknownHostException, PacketNativeException {
        TcpPacket tcpPacket = new TcpPacket(createTcpHeader(), new SimplePacket());
        IpPacket ipPacket = new IpPacket(createIpHeader(), tcpPacket);
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(IPV4), ipPacket);
        Packet actual = handler.sendPacket(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("TCP Packet을 pcap 파일로 저장한다.")
    void save() throws UnknownHostException, PacketNativeException {
        TcpPacket tcpPacket = new TcpPacket(createTcpHeader(), new SimplePacket());
        IpPacket ipPacket = new IpPacket(createIpHeader(), tcpPacket);
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(IPV4), ipPacket);
        PacketTestHelper.save(handler, expected);
        Packet actual = createEthernetPacket(read());

        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
    }

    @Test
    @DisplayName("TCP 3 way handshake로 연결한다.")
    void connection() {

    }

    // TODO: Port Open을 확인한다

    // TODO: DNS Packet을 전송한다.

    // TODO: DNS Packet을 통해 URL에 해당하는 IP 정보를 알아온다.

    // TODO: ICMP echo request를 전송한다.

    // TODO: traceroute를 구현한다.


    // TODO: SYN Flooding
    // TODO: TCP Slicing
    // TODO: TCP to HTTP

    @AfterEach
    void tearDown() {
        handler.close();
    }

}