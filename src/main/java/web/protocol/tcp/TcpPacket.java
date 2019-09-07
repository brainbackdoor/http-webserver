package web.protocol.tcp;

import lombok.Builder;
import lombok.Getter;
import web.protocol.Packet;
import web.util.ByteUtils;

import java.util.ArrayList;
import java.util.List;

import static web.util.PacketUtils.copyHeader;
import static web.util.PacketUtils.copyPayload;

@Builder
@Getter
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
            return rawFields;
        }

    }
}
