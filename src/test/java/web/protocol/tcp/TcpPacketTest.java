package web.protocol.tcp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;

import static org.assertj.core.api.Assertions.assertThat;

class TcpPacketTest extends TcpPacketHelper {
    PcapHandle handle;

    @BeforeEach
    void setUp() throws PcapNativeException {
        handle = getHandle();
    }

    @Test
    @DisplayName("TCP Packet을 생성한다.")
    void constructor() {
        TcpPacket packet = createTcpPacket();

        assertThat(packet).isNotNull();
    }

    @Test
    @DisplayName("TCP Packet을 전송한다.")
    void send() throws PcapNativeException, NotOpenException {
        // given
        byte[] rawData = createTcpPacket().getRawData();

        // when
        Packet sendingPacket = UnknownPacket.newPacket(rawData, 0, rawData.length);
        handle.sendPacket(sendingPacket);
        byte[] result = gotPacket(handle, rawData);

        // then
        assertThat(rawData).isEqualTo(result);
    }

    // TODO: TCP 3 way handshake로 연결한다.

    // TODO: Port Open을 확인한다.

    // TODO: 웹 요청 패킷을 pcap 파일로 저장한다.

    // TODO: DNS Packet을 전송한다.

    // TODO: DNS Packet을 통해 URL에 해당하는 IP 정보를 알아온다.

    // TODO: ICMP echo request를 전송한다.

    // TODO: traceroute를 구현한다.

    // TODO: ARP를 이용하여 공유기 주소를 알아온다.


    @AfterEach
    void tearDown() throws NotOpenException {
        handle.breakLoop();
        handle.close();
    }

    private PcapHandle getHandle() throws PcapNativeException {
        PcapNetworkInterface nif = Pcaps.getDevByName("en0");
        return nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
    }

    private byte[] gotPacket(PcapHandle handle, byte[] rawData) {
        final byte[] result = new byte[rawData.length];
        return result;
    }
}