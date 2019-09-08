package web.protocol.ip;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.pcap4j.packet.namednumber.IpV4OptionType;
import web.protocol.Packet;
import web.util.ByteUtils;

import java.io.Serializable;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import static web.util.ByteUtils.*;
import static web.util.PacketUtils.copyHeader;
import static web.util.PacketUtils.copyPayload;

@Builder
@Getter
@ToString
public class IpPacket implements Packet {

    private final IpHeader header;
    private final Packet payload;

    public IpPacket(IpHeader header, Packet payload) {
        this.header = header;
        this.payload = payload;
    }

    @Override
    public Header getHeader() {
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
    public static final class IpHeader implements Header {

        private static final int VERSION_AND_IHL_OFFSET = 0;
        private static final int VERSION_AND_IHL_SIZE = BYTE_SIZE_IN_BYTES;
        private static final int TOS_OFFSET = VERSION_AND_IHL_OFFSET + VERSION_AND_IHL_SIZE;
        private static final int TOS_SIZE = BYTE_SIZE_IN_BYTES;
        private static final int TOTAL_LENGTH_OFFSET = TOS_OFFSET + TOS_SIZE;
        private static final int TOTAL_LENGTH_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int IDENTIFICATION_OFFSET = TOTAL_LENGTH_OFFSET + TOTAL_LENGTH_SIZE;
        private static final int IDENTIFICATION_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int FLAGS_AND_FRAGMENT_OFFSET_OFFSET = IDENTIFICATION_OFFSET + IDENTIFICATION_SIZE;
        private static final int FLAGS_AND_FRAGMENT_OFFSET_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int TTL_OFFSET = FLAGS_AND_FRAGMENT_OFFSET_OFFSET + FLAGS_AND_FRAGMENT_OFFSET_SIZE;
        private static final int TTL_SIZE = BYTE_SIZE_IN_BYTES;
        private static final int PROTOCOL_OFFSET = TTL_OFFSET + TTL_SIZE;
        private static final int PROTOCOL_SIZE = BYTE_SIZE_IN_BYTES;
        private static final int HEADER_CHECKSUM_OFFSET = PROTOCOL_OFFSET + PROTOCOL_SIZE;
        private static final int HEADER_CHECKSUM_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int SRC_ADDR_OFFSET = HEADER_CHECKSUM_OFFSET + HEADER_CHECKSUM_SIZE;
        private static final int SRC_ADDR_SIZE = INET4_ADDRESS_SIZE_IN_BYTES;
        private static final int DST_ADDR_OFFSET = SRC_ADDR_OFFSET + SRC_ADDR_SIZE;
        private static final int DST_ADDR_SIZE = INET4_ADDRESS_SIZE_IN_BYTES;
        private static final int OPTIONS_OFFSET = DST_ADDR_OFFSET + DST_ADDR_SIZE;

        public static final int MIN_IPV4_HEADER_SIZE = DST_ADDR_OFFSET + DST_ADDR_SIZE; // 20 Bytes

        private final Version version;
        private final byte ihl;
        private final Tos tos;
        private final short totalLength;
        private final short identification;
        private final Flag flag;
        private final short fragmentOffset;
        private final byte ttl;
        private final ProtocolIdentifier protocolIdentifier;
        private final short headerChecksum;
        private final Inet4Address srcAddr;
        private final Inet4Address dstAddr;
        private final List<IpOption> options;
        private final byte[] padding;

        @Override
        public int length() {
            return MIN_IPV4_HEADER_SIZE + 1;
        }

        @Override
        public List<byte[]> getRawFields() {
            return getRawFields(false);
        }

        private List<byte[]> getRawFields(boolean zeroInsteadOfChecksum) {
            List<byte[]> rawFields = new ArrayList<>();
            rawFields.add(ByteUtils.toByteArray((byte) ((version.getValue() << 4) | ihl)));
            rawFields.add(new byte[]{tos.value()});
            rawFields.add(ByteUtils.toByteArray((short) length()));
            rawFields.add(ByteUtils.toByteArray(identification));
            rawFields.add(ByteUtils.toByteArray(flag.getValue()));
            rawFields.add(new byte[]{ttl});
            rawFields.add(new byte[]{protocolIdentifier.getValue()});
            rawFields.add(ByteUtils.toByteArray(zeroInsteadOfChecksum ? (short) 0 : headerChecksum));
            rawFields.add(ByteUtils.toByteArray(dstAddr));
            rawFields.add(ByteUtils.toByteArray(srcAddr));
            return rawFields;
        }
    }

    public interface IpOption extends Serializable {

        IpV4OptionType getType();

        int length();

        byte[] getRawData();
    }

    public interface Tos extends Serializable {

        byte value();
    }
}
