package web.protocol.ethernet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.ethernet.EthernetPacket.EthernetHeader;
import web.tool.sniffer.PacketHandler;
import web.tool.sniffer.PacketNativeException;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.ethernet.PacketTestHelper.*;
import static web.protocol.ethernet.Type.ARP;

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
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), new SimplePacket());
        Packet actual = handler.sendPacket(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Ethernet Packet을 pcap 파일에 저장한다.")
    void save() throws PacketNativeException {
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), new SimplePacket());
        PacketTestHelper.save(handler, expected);
        Packet actual = createEthernetPacket(read());

        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
    }

    @Test
    @DisplayName("ARP를 이용하여 공유기 주소를 알아온다.")
    void search() {
    }

    @AfterEach
    void tearDown() {
        handler.close();
    }
}