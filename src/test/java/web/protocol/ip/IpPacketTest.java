package web.protocol.ip;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.ethernet.EthernetPacket;
import web.protocol.ethernet.PacketTestHelper;
import web.protocol.ip.IpPacket.IpHeader;
import web.protocol.tcp.TcpPacket;
import web.tool.sniffer.PacketHandler;
import web.tool.sniffer.PacketNativeException;

import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.ethernet.Type.IPV4;

class IpPacketTest extends PacketTestHelper {

    PacketHandler handler;

    @BeforeEach
    void setUp() throws Exception {
        handler = getHandler();
    }

    @Test
    @DisplayName("IPv4 Packet을 생성한다.")
    void constructor() throws UnknownHostException {
        IpHeader header = createIpHeader();
        IpPacket packet = new IpPacket(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }

    @Test
    @DisplayName("IPv4 Packet을 전송한다.")
    void send() throws UnknownHostException, PacketNativeException {
        TcpPacket tcpPacket = new TcpPacket(createTcpHeader(), new SimplePacket());
        IpPacket ipPacket = new IpPacket(createIpHeader(), tcpPacket);
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(IPV4), ipPacket);
        Packet actual = handler.sendPacket(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Packet을 pcap 파일에 저장한다.")
    void save() throws PacketNativeException, UnknownHostException {
        TcpPacket tcpPacket = new TcpPacket(createTcpHeader(), new SimplePacket());
        IpPacket ipPacket = new IpPacket(createIpHeader(), tcpPacket);
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(IPV4), ipPacket);
        PacketTestHelper.save(handler, expected);
        System.out.println(expected);
        Packet actual = createEthernetPacket(read());

        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
    }

    @AfterEach
    void tearDown() {
        handler.close();
    }
}