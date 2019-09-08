package web.protocol.tcp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.SimplePacket;
import web.protocol.ethernet.PacketTestHelper;

import static org.assertj.core.api.Assertions.assertThat;

class TcpPacketTest extends PacketTestHelper {

    @Test
    @DisplayName("TCP Packet을 생성한다.")
    void constructor() {
        TcpPacket.TcpHeader header = createTcpHeader();
        TcpPacket packet = new TcpPacket(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }



    @Test
    @DisplayName("TCP Packet을 전송한다.")
    void send() {

    }

    @Test
    @DisplayName("TCP 3 way handshake로 연결한다.")
    void connection() {

    }

    @Test
    @DisplayName("웹 요청 패킷을 pcap 파일로 저장한다.")
    void save() {

    }

    // TODO: Port Open을 확인한다

    // TODO: DNS Packet을 전송한다.

    // TODO: DNS Packet을 통해 URL에 해당하는 IP 정보를 알아온다.

    // TODO: ICMP echo request를 전송한다.

    // TODO: traceroute를 구현한다.


    // TODO: SYN Flooding
    // TODO: TCP Slicing
    // TODO: TCP to HTTP

}