package web.server;

import org.hyperic.sigar.SigarException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.tool.analysis.NetInfo;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;

import static org.assertj.core.api.Assertions.assertThat;

class ServerTest {

    private Inet4Address bindAddress;
    private static final int DEFAULT_BACK_LOG = 200;

    @BeforeEach
    void setUp() throws SigarException, IOException {
        NetInfo netInfo = new NetInfo();
        bindAddress = (Inet4Address) InetAddress.getByName(netInfo.getIp());

    }

    @Test
    @DisplayName("Connection이 연결되었는지 테스트")
    void connection() throws IOException {
        int port = 8080;
        ServerSocket socket = new ServerSocket(port, DEFAULT_BACK_LOG, bindAddress);

        assertThat(socket.isBound()).isTrue();
        assertThat(socket.isClosed()).isFalse();
        assertThat(socket.getLocalPort()).isEqualTo(port);
        socket.close();
    }
}