package web.protocol.ip;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.SimplePacket;
import web.protocol.ip.IpV4Packet.IpV4Header;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;

class IpV4PacketTest {

    @Test
    @DisplayName("IPv4 Packet을 생성한다.")
    void constructor() throws UnknownHostException {
        IpV4Header header = new IpV4Header(IpVersion.IPV4,
                (byte) 9,
                null,
                (short) 44,
                (short) 123,
                true,
                false,
                true,
                (short) 0,
                (byte) 111,
                IpNumber.TCP,
                (short) 0xEEEE,
                (Inet4Address)
                        InetAddress.getByAddress(new byte[] {(byte) 192, (byte) 0, (byte) 2, (byte) 1}),
                (Inet4Address)
                        InetAddress.getByAddress(new byte[] {(byte) 192, (byte) 0, (byte) 2, (byte) 1}));
        IpV4Packet packet = new IpV4Packet(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }

}