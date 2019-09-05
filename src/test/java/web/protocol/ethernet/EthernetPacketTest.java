package web.protocol.ethernet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;
import web.protocol.SimplePacket;
import web.protocol.ethernet.EthernetPacket.EthernetHeader;

import static org.assertj.core.api.Assertions.assertThat;

class EthernetPacketTest {

    @Test
    @DisplayName("Ethernet Packet을 작성한다.")
    void constructor() {
        EthernetHeader header = new EthernetHeader(MacAddress.ETHER_BROADCAST_ADDRESS, MacAddress.getByName("fe:00:00:00:00:01"), EtherType.ARP);
        EthernetPacket packet = new EthernetPacket(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }
}