package web.protocol.tcp;

import org.junit.jupiter.api.*;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.ethernet.EthernetPacket;
import web.protocol.helper.PacketTestHelper;
import web.protocol.ip.IpPacket;
import web.protocol.tcp.TcpPacket.TcpHeader;
import web.tool.analysis.NetInfo;
import web.tool.sniffer.PacketNativeException;

import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.ethernet.EthernetPacketTest.buildEthernetPacket;
import static web.protocol.ethernet.EthernetPacketTest.createEthernetHeader;
import static web.protocol.ethernet.Type.IPV4;

class TcpPacketTest extends PacketTestHelper {

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
    @DisplayName("TCP Packet을 생성한다.")
    void constructor() {
        TcpHeader header = createTcpHeader();
        TcpPacket packet = new TcpPacket(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }

    @Test
    @Disabled
    @DisplayName("TCP Packet을 전송한다.")
    void send() throws UnknownHostException, PacketNativeException {
        TcpPacket tcpPacket = new TcpPacket(createTcpHeader(), new SimplePacket());
        IpPacket ipPacket = new IpPacket(createIpHeader(), tcpPacket);
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(IPV4), ipPacket);
        handler.sendPacket(expected);
        handler.loop(5, listener);

        assertThat(packetStorage.exist(expected)).isTrue();
    }

    @Test
    @DisplayName("TCP Packet을 pcap 파일로 저장한다.")
    void save() throws UnknownHostException, PacketNativeException {
        TcpPacket tcpPacket = new TcpPacket(createTcpHeader(), new SimplePacket());
        IpPacket ipPacket = new IpPacket(createIpHeader(), tcpPacket);
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(IPV4), ipPacket);
        PacketTestHelper.save(handler, expected);
        Packet actual = buildEthernetPacket(read());

        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
    }

    private void gotPacket(byte[] raw) {
        Packet packet = buildEthernetPacket(raw);
        packetStorage.add(packet);
    }

    @AfterEach
    void tearDown() {
        handler.close();
    }
}