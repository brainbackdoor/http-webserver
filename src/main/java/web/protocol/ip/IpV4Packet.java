package web.protocol.ip;

import org.pcap4j.packet.namednumber.IpV4OptionType;
import web.protocol.Packet;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Iterator;

import static org.pcap4j.util.ByteArrays.*;

public class IpV4Packet implements IpPacket {

    private final IpV4Header header;
    private final Packet payload;

    public IpV4Packet(IpV4Header header, Packet payload) {
        this.header = header;
        this.payload = payload;
    }

    @Override
    public IpHeader getHeader() {
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

    public static final class IpV4Header implements IpHeader {
        private static final long serialVersionUID = -7583326842445453539L;

        private static final int VERSION_AND_IHL_OFFSET = 0;
        private static final int VERSION_AND_IHL_SIZE = BYTE_SIZE_IN_BYTES;
        private static final int TOS_OFFSET = VERSION_AND_IHL_OFFSET + VERSION_AND_IHL_SIZE;
        private static final int TOS_SIZE = BYTE_SIZE_IN_BYTES;
        private static final int TOTAL_LENGTH_OFFSET = TOS_OFFSET + TOS_SIZE;
        private static final int TOTAL_LENGTH_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int IDENTIFICATION_OFFSET = TOTAL_LENGTH_OFFSET + TOTAL_LENGTH_SIZE;
        private static final int IDENTIFICATION_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int FLAGS_AND_FRAGMENT_OFFSET_OFFSET =
                IDENTIFICATION_OFFSET + IDENTIFICATION_SIZE;
        private static final int FLAGS_AND_FRAGMENT_OFFSET_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int TTL_OFFSET =
                FLAGS_AND_FRAGMENT_OFFSET_OFFSET + FLAGS_AND_FRAGMENT_OFFSET_SIZE;
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

        private static final int MIN_IPV4_HEADER_SIZE = DST_ADDR_OFFSET + DST_ADDR_SIZE;

        private final IpVersion version;
        private final byte ihl;
        private final IpV4Tos tos;
        private final short totalLength;
        private final short identification;
        private final boolean reservedFlag;
        private final boolean dontFragmentFlag;
        private final boolean moreFragmentFlag;
        private final short fragmentOffset;
        private final byte ttl;
        private final IpNumber protocol;
        private final short headerChecksum;
        private final Inet4Address srcAddr;
        private final Inet4Address dstAddr;
//        private final List<IpV4Option> options;
//        private final byte[] padding;

        public IpV4Header(IpVersion version, byte ihl, IpV4Tos tos, short totalLength, short identification, boolean reservedFlag, boolean dontFragmentFlag, boolean moreFragmentFlag, short fragmentOffset, byte ttl, IpNumber protocol, short headerChecksum, Inet4Address srcAddr, Inet4Address dstAddr) {
            this.version = version;
            this.ihl = ihl;
            this.tos = tos;
            this.totalLength = totalLength;
            this.identification = identification;
            this.reservedFlag = reservedFlag;
            this.dontFragmentFlag = dontFragmentFlag;
            this.moreFragmentFlag = moreFragmentFlag;
            this.fragmentOffset = fragmentOffset;
            this.ttl = ttl;
            this.protocol = protocol;
            this.headerChecksum = headerChecksum;
            this.srcAddr = srcAddr;
            this.dstAddr = dstAddr;
//            this.options = options;
//            this.padding = padding;
        }


        @Override
        public IpVersion getVersion() {
            return null;
        }

        @Override
        public IpNumber getProtocol() {
            return null;
        }

        @Override
        public InetAddress getSrcAddr() {
            return null;
        }

        @Override
        public InetAddress getDstAddr() {
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
    }

    public interface IpV4Option extends Serializable {

        IpV4OptionType getType();

        int length();

        byte[] getRawData();
    }
    public interface IpV4Tos extends Serializable {
        byte value();
    }
}
