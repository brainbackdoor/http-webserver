package web.protocol.tcp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TcpPacketTest extends TcpPacketHelper{

    @Test
    @DisplayName("TCP packet을 생성한다.")
    void constructor() {
        TcpPacket packet = createTcpPacket();

        assertThat(packet).isNotNull();
    }
}