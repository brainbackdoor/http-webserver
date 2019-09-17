package web.protocol.ip;

import org.junit.jupiter.api.*;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.ethernet.EthernetPacket;
import web.protocol.helper.PacketTestHelper;
import web.protocol.ip.IpPacket.IpHeader;
import web.tool.analysis.NetInfo;
import web.tool.sniffer.PacketNativeException;

import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.ethernet.EthernetPacketTest.*;
import static web.protocol.ethernet.Type.IPV4;

class IpPacketTest extends PacketTestHelper {

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
    @DisplayName("IPv4 Packet을 생성한다.")
    void constructor() throws UnknownHostException {
        IpHeader header = createIpHeader();
        IpPacket packet = new IpPacket(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }

    @Test
    @Disabled
    @DisplayName("IPv4 Packet을 전송한다.")
    void send() throws UnknownHostException, PacketNativeException {
        IpPacket ipPacket = new IpPacket(createIpHeader(), new SimplePacket());
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(IPV4), ipPacket);
        handler.sendPacket(expected);
        handler.loop(5, listener);

        assertThat(packetStorage.exist(expected)).isTrue();
    }


    @Test
    @DisplayName("IP Packet을 pcap 파일에 저장한다.")
    void save() throws PacketNativeException, UnknownHostException {
        IpPacket ipPacket = new IpPacket(createIpHeader(), new SimplePacket());
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(IPV4), ipPacket);
        PacketTestHelper.save(handler, expected);
        Packet actual = buildEthernetPacket(read());

        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
    }

    public static Packet buildIpPacket(byte[] rawData) {
        EthernetPacket.EthernetHeader ethernetHeader = buildEthernetHeader(rawData);
        IpHeader ipHeader = buildIpHeader(rawData);

        IpPacket ipPacket = new IpPacket(ipHeader, new SimplePacket());
        return new EthernetPacket(ethernetHeader, ipPacket);
    }

    private static IpHeader buildIpHeader(byte[] rawData) {
        return null;
    }

    private void gotPacket(byte[] raw) {
        Packet packet = buildIpPacket(raw);
        packetStorage.add(packet);
    }

    @AfterEach
    void tearDown() {
        handler.close();
    }
}