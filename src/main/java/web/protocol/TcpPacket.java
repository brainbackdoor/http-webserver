package web.protocol;

import org.pcap4j.packet.namednumber.TcpOptionKind;
import web.protocol.tcp.Port;
import web.protocol.tcp.TcpPort;
import web.protocol.tcp.TransportPacket;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.pcap4j.util.ByteArrays.INT_SIZE_IN_BYTES;
import static org.pcap4j.util.ByteArrays.SHORT_SIZE_IN_BYTES;

public class TcpPacket implements TransportPacket {

    private static final long serialVersionUID = 7904566782140471299L;

    private final TcpHeader header;
    private final Packet payload;


    public TcpPacket(TcpHeader header, Packet payload) {
        this.header = header;
        this.payload = payload;
    }

    @Override
    public TransportHeader getHeader() {
        return null;
    }

    @Override
    public Packet getPayload() {
        return null;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public byte[] getRawData() {
        return new byte[0];
    }

    @Override
    public <T extends Packet> T get(Class<T> clazz) {
        return null;
    }

    @Override
    public Packet getOuterOf(Class<? extends Packet> clazz) {
        return null;
    }

    @Override
    public <T extends Packet> boolean contains(Class<T> clazz) {
        return false;
    }

    @Override
    public Builder getBuilder() {
        return null;
    }

    @Override
    public Iterator<Packet> iterator() {
        return null;
    }

    @Override
    public String toString() {
        return "TcpPacket{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }

    public static final class TcpHeader implements TransportHeader {
        private static final long serialVersionUID = -795185420055823677L;

        private static final int SRC_PORT_OFFSET = 0;
        private static final int SRC_PORT_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int DST_PORT_OFFSET = SRC_PORT_OFFSET + SRC_PORT_SIZE;
        private static final int DST_PORT_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int SEQUENCE_NUMBER_OFFSET = DST_PORT_OFFSET + DST_PORT_SIZE;
        private static final int SEQUENCE_NUMBER_SIZE = INT_SIZE_IN_BYTES;
        private static final int ACKNOWLEDGMENT_NUMBER_OFFSET =
                SEQUENCE_NUMBER_OFFSET + SEQUENCE_NUMBER_SIZE;
        private static final int ACKNOWLEDGMENT_NUMBER_SIZE = INT_SIZE_IN_BYTES;
        private static final int DATA_OFFSET_AND_RESERVED_AND_CONTROL_BITS_OFFSET =
                ACKNOWLEDGMENT_NUMBER_OFFSET + ACKNOWLEDGMENT_NUMBER_SIZE;
        private static final int DATA_OFFSET_AND_RESERVED_AND_CONTROL_BITS_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int WINDOW_OFFSET =
                DATA_OFFSET_AND_RESERVED_AND_CONTROL_BITS_OFFSET
                        + DATA_OFFSET_AND_RESERVED_AND_CONTROL_BITS_SIZE;
        private static final int WINDOW_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int CHECKSUM_OFFSET = WINDOW_OFFSET + WINDOW_SIZE;
        private static final int CHECKSUM_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int URGENT_POINTER_OFFSET = CHECKSUM_OFFSET + CHECKSUM_SIZE;
        private static final int URGENT_POINTER_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int OPTIONS_OFFSET = URGENT_POINTER_OFFSET + URGENT_POINTER_SIZE;

        private static final int MIN_TCP_HEADER_SIZE = URGENT_POINTER_OFFSET + URGENT_POINTER_SIZE;

        private static final int IPV4_PSEUDO_HEADER_SIZE = 12;
        private static final int IPV6_PSEUDO_HEADER_SIZE = 40;

        private final TcpPort srcPort;
        private final TcpPort dstPort;
        private final int sequenceNumber;
        private final int acknowledgmentNumber;
        private final byte dataOffset;
        private final byte reserved;
        private final boolean urg;
        private final boolean ack;
        private final boolean psh;
        private final boolean rst;
        private final boolean syn;
        private final boolean fin;
        private final short window;
        private final short checksum;
        private final short urgentPointer;
        private final List<TcpOption> options;
        private final byte[] padding;

        public TcpHeader(TcpPort srcPort, TcpPort dstPort, int sequenceNumber, int acknowledgmentNumber, byte dataOffset, byte reserved, boolean urg, boolean ack, boolean psh, boolean rst, boolean syn, boolean fin, short window, short checksum, short urgentPointer, List<TcpOption> options, byte[] padding) {
            this.srcPort = srcPort;
            this.dstPort = dstPort;
            this.sequenceNumber = sequenceNumber;
            this.acknowledgmentNumber = acknowledgmentNumber;
            this.dataOffset = dataOffset;
            this.reserved = reserved;
            this.urg = urg;
            this.ack = ack;
            this.psh = psh;
            this.rst = rst;
            this.syn = syn;
            this.fin = fin;
            this.window = window;
            this.checksum = checksum;
            this.urgentPointer = urgentPointer;
            this.options = options;
            this.padding = padding;
        }

        @Override
        public Port getSrcPort() {
            return null;
        }

        @Override
        public Port getDstPort() {
            return null;
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public byte[] getRawData() {
            return new byte[0];
        }

        @Override
        public String toString() {
            return "TcpHeader{" +
                    "srcPort=" + srcPort +
                    ", dstPort=" + dstPort +
                    ", sequenceNumber=" + sequenceNumber +
                    ", acknowledgmentNumber=" + acknowledgmentNumber +
                    ", dataOffset=" + dataOffset +
                    ", reserved=" + reserved +
                    ", urg=" + urg +
                    ", ack=" + ack +
                    ", psh=" + psh +
                    ", rst=" + rst +
                    ", syn=" + syn +
                    ", fin=" + fin +
                    ", window=" + window +
                    ", checksum=" + checksum +
                    ", urgentPointer=" + urgentPointer +
                    ", options=" + options +
                    ", padding=" + Arrays.toString(padding) +
                    '}';
        }

        public interface TcpOption extends Serializable {

            TcpOptionKind getKind();

            int length();

            byte[] getRawData();
        }
    }
}
