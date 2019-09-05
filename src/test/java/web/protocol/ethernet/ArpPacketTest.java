package web.protocol.ethernet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pcap4j.packet.namednumber.ArpHardwareType;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.MacAddress;
import web.protocol.ethernet.ArpPacket.ArpHeader;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;

class ArpPacketTest {

    @Test
    @DisplayName("ARP Packet을 생성한다.")
    void constructor() throws UnknownHostException {
        ArpPacket arpPacket = createArpPacket();

        assertThat(arpPacket).isNotNull();
    }

    @Test
    @DisplayName("ARP를 이용하여 공유기 주소를 알아온다.")
    void arp() {

    }

    private ArpPacket createArpPacket() throws UnknownHostException {
        ArpHeader header = new ArpHeader(
                ArpHardwareType.ETHERNET,
                EtherType.IPV4,
                (byte) MacAddress.SIZE_IN_BYTES,
                (byte) ByteArrays.INET4_ADDRESS_SIZE_IN_BYTES,
                ArpOperation.REQUEST,
                MacAddress.getByName("fe:00:00:00:00:01"),
                InetAddress.getByAddress(new byte[]{(byte) 192, (byte) 0, (byte) 2, (byte) 1}),
                MacAddress.ETHER_BROADCAST_ADDRESS,
                InetAddress.getByAddress(new byte[]{(byte) 192, (byte) 0, (byte) 2, (byte) 2}));
        return new ArpPacket(header);
    }

}