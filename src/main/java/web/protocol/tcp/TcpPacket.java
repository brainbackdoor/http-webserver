package web.protocol.tcp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import web.protocol.Packet;
import web.util.ByteUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static web.util.PacketUtils.copyHeader;
import static web.util.PacketUtils.copyPayload;

@Builder
@Getter
@ToString
public class TcpPacket implements TransportPacket {

    private final TcpHeader header;
    private final Packet payload;

    public TcpPacket(TcpHeader header, Packet payload) {
        this.header = header;
        this.payload = payload;
    }

    @Override
    public TransportHeader getHeader() {
        return header;
    }

    @Override
    public Packet getPayload() {
        return payload;
    }

    @Override
    public int length() {
        return getHeader().length() + getPayload().length();
    }

    @Override
    public byte[] getRawData() {
        byte[] result = new byte[length()];

        int nextPosition = copyHeader(this, result, 0);
        copyPayload(this, result, nextPosition);
        return result;
    }

    @Builder
    @Getter
    @ToString
    public static final class TcpHeader implements TransportHeader {

        private static final int DEFAULT_TCP_HEADER_SIZE = 20;

        private final TcpPort srcPort;
        private final TcpPort dstPort;
        private final int sequenceNumber;
        private final int acknowledgmentNumber;
        private final byte offset;
        private final byte reserved;
        private final Flag flag;
        private final short window;
        private final short checksum;
        private final short urgentPointer;
        private final List<TcpOption> options;


        @Override
        public TcpPort getSrcPort() {
            return srcPort;
        }

        @Override
        public TcpPort getDstPort() {
            return dstPort;
        }

        @Override
        public int length() {
            return DEFAULT_TCP_HEADER_SIZE;
        }

        @Override
        public List<byte[]> getRawFields() {
            return getRawFields(false);
        }

        private List<byte[]> getRawFields(boolean zeroInsteadOfChecksum) {
            List<byte[]> rawFields = new ArrayList<>();
            rawFields.add(ByteUtils.toByteArray(srcPort.getValue()));
            rawFields.add(ByteUtils.toByteArray(dstPort.getValue()));
            rawFields.add(ByteUtils.toByteArray(sequenceNumber));
            rawFields.add(ByteUtils.toByteArray(acknowledgmentNumber));
            rawFields.add(ByteUtils.toByteArray((short) ((offset << 12) | (reserved << 6) | flag.getValue())));
            rawFields.add(ByteUtils.toByteArray(window));
            rawFields.add(ByteUtils.toByteArray(zeroInsteadOfChecksum ? (short) 0 : checksum));
            rawFields.add(ByteUtils.toByteArray(urgentPointer));
            for (TcpOption o : options) {
                rawFields.add(o.getRawData());
            }
            return rawFields;
        }

    }

    public interface TcpOption extends Serializable {
        TcpOptionKind getKind();

        int length();

        byte[] getRawData();
    }

    @Getter
    @AllArgsConstructor
    public enum TcpOptionKind {
        NO_OPERATION((byte) 1, "No Operation"),
        MAXIMUM_SEGMENT_SIZE((byte) 2, "Maximum Segment Size"),
        WINDOW_SCALE((byte) 3, "Window Scale"),
        SACK_PERMITTED((byte) 4, "SACK Permitted"),
        TIMESTAMPS((byte) 8, "Timestamps");

        private byte value;
        private String name;
    }
}
