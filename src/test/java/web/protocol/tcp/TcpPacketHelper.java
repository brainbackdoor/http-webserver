package web.protocol.tcp;

import org.pcap4j.packet.namednumber.TcpOptionKind;
import web.protocol.SimplePacket;

import java.util.ArrayList;
import java.util.List;

public class TcpPacketHelper {

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
    private List<TcpPacket.TcpHeader.TcpOption> options = new ArrayList<>();
    private byte[] padding = new byte[]{(byte) 0xaa};

    public TcpPacket createTcpPacket() {
        this.options.add(new TcpNoOperationOption());
        TcpPacket.TcpHeader header = new TcpPacket.TcpHeader(srcPort, dstPort, sequenceNumber, acknowledgmentNumber, dataOffset, reserved, urg, ack, psh, rst, syn, fin, window, checksum, urgentPointer, options, padding);
        return new TcpPacket(header, new SimplePacket());
    }


    class TcpNoOperationOption implements TcpPacket.TcpHeader.TcpOption {
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
