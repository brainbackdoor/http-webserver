package web.protocol;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pcap4j.packet.namednumber.TcpOptionKind;
import web.protocol.TcpPacket.TcpHeader;
import web.protocol.TcpPacket.TcpHeader.TcpOption;
import web.protocol.tcp.TcpPort;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TcpPacketTest {
    private TcpPort srcPort = TcpPort.HTTP;
    private TcpPort dstPort = TcpPort.getInstance((short) 0);
    private int sequenceNumber = 1234567;
    private int acknowledgmentNumber = 7654321;
    private byte dataOffset = 15;
    private byte reserved = (byte) 11;
    private boolean urg = false;
    private boolean ack = true;
    private boolean psh = false;
    private boolean rst = true;
    private boolean syn = false;
    private boolean fin = true;
    private short window = (short) 9999;
    private short checksum = (short) 0xABCD;
    private short urgentPointer = (short) 1111;
    private List<TcpOption> options = new ArrayList<>();
    private byte[] padding = new byte[]{(byte) 0xaa};
    private Inet4Address srcAddr;
    private Inet4Address dstAddr;
    private TcpPacket packet;

    TcpPacketTest() throws UnknownHostException {
        this.srcAddr = (Inet4Address) InetAddress.getByName("192.168.0.1");
        this.dstAddr = (Inet4Address) InetAddress.getByName("192.168.0.2");
        this.options.add(new TcpNoOperationOption());


    }


    @Test
    @DisplayName("TCP packet을 생성한다.")
    void constructor() {
        TcpHeader header = new TcpHeader(srcPort, dstPort, sequenceNumber, acknowledgmentNumber, dataOffset, reserved, urg, ack, psh, rst, syn, fin, window, checksum, urgentPointer, options, padding);
        packet = new TcpPacket(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }


    class TcpNoOperationOption implements TcpOption {
        private final TcpOptionKind kind = TcpOptionKind.NO_OPERATION;

        @Override
        public TcpOptionKind getKind() {
            return kind;
        }

        @Override
        public int length() {
            return 1;
        }

        @Override
        public byte[] getRawData() {
            return new byte[]{(byte) 1};
        }

        @Override
        public String toString() {
            return "TcpNoOperationOption{" +
                    "kind=" + kind +
                    '}';
        }
    }
}