package web.protocol.ethernet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.ethernet.EthernetPacket.EthernetHeader;
import web.tool.sniffer.NetworkInterface;
import web.tool.sniffer.NetworkInterfaceService;
import web.tool.sniffer.PacketHandler;

import static org.assertj.core.api.Assertions.assertThat;

class EthernetPacketTest {
    PacketHandler handler;

    @BeforeEach
    void setUp() throws Exception {
        handler = getHandler();
    }

    @Test
    @DisplayName("Ethernet Packet을 생성한다.")
    void constructor() {
        EthernetHeader header = createHeader();
        EthernetPacket packet = new EthernetPacket(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }

    @Test
    @DisplayName("Ethernet Packet을 전송한다.")
    void arp() throws Exception {
        EthernetPacket expected = new EthernetPacket(createHeader(), new SimplePacket());
        Packet actual = handler.sendPacket(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("ARP를 이용하여 공유기 주소를 알아온다.")
    void search() {
    }

    @AfterEach
    void tearDown() {
        handler.close();
    }

    private EthernetHeader createHeader() {
        MacAddress src = MacAddress.getByName("00:00:00:00:00:01");
        MacAddress dst = MacAddress.ETHER_BROADCAST_ADDRESS;
        Type protocolType = Type.ARP;

        return new EthernetHeader(dst, src, protocolType);
    }
    private PacketHandler getHandler() throws Exception {
        NetworkInterface nif = NetworkInterfaceService.findByName("en0");
        return nif.openLive(65536, NetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
    }
}