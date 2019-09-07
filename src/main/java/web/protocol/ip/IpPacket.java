package web.protocol.ip;

import lombok.Builder;
import lombok.Getter;
import org.pcap4j.packet.namednumber.IpV4OptionType;
import web.protocol.Packet;
import web.util.ByteUtils;

import java.io.Serializable;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import static web.util.PacketUtils.copyHeader;
import static web.util.PacketUtils.copyPayload;

@Builder
@Getter
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
    public static final class IpHeader implements Header {

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
            return totalLength;
        }

        @Override
        public List<byte[]> getRawFields() {
            return getRawFields(false);
        }

        private List<byte[]> getRawFields(boolean zeroInsteadOfChecksum) {
            List<byte[]> rawFields = new ArrayList<>();
            rawFields.add(ByteUtils.toByteArray((byte) ((version.getValue() << 4) | ihl)));
            rawFields.add(new byte[]{tos.value()});
            rawFields.add(ByteUtils.toByteArray(totalLength));
            rawFields.add(ByteUtils.toByteArray(identification));
            rawFields.add(ByteUtils.toByteArray((short) ((flag.getValue() << 13) | fragmentOffset)));
            rawFields.add(ByteUtils.toByteArray(ttl));
            rawFields.add(ByteUtils.toByteArray(protocolIdentifier.getValue()));
            rawFields.add(ByteUtils.toByteArray(zeroInsteadOfChecksum ? (short) 0 : headerChecksum));
            rawFields.add(ByteUtils.toByteArray(srcAddr));
            rawFields.add(ByteUtils.toByteArray(dstAddr));
            for (IpOption o : options) {
                rawFields.add(o.getRawData());
            }
            rawFields.add(padding);
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
