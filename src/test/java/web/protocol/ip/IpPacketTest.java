package web.protocol.ip;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

class IpPacketTest {

    @Test
    @DisplayName("IPv4 Packet을 생성한다.")
    void constructor() throws UnknownHostException {
//        IpHeader header = new IpHeader(Version.IPV4,
//                (byte) 9,
//                null,
//                (short) 44,
//                (short) 123,
//                true,
//                false,
//                true,
//                (short) 0,
//                (byte) 111,
//                ProtocolIdentifier.TCP,
//                (short) 0xEEEE,
//                (Inet4Address)
//                        InetAddress.getByAddress(new byte[] {(byte) 192, (byte) 0, (byte) 2, (byte) 1}),
//                (Inet4Address)
//                        InetAddress.getByAddress(new byte[] {(byte) 192, (byte) 0, (byte) 2, (byte) 1}));
//        IpPacket packet = new IpPacket(header, new SimplePacket());
//
//        assertThat(packet).isNotNull();
    }

}