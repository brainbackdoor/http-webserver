package web.protocol.ethernet;

import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;
import web.protocol.Packet;

import java.util.Iterator;

import static org.pcap4j.util.ByteArrays.SHORT_SIZE_IN_BYTES;

public class EthernetPacket implements Packet {
    private static final long serialVersionUID = 3461432646404254300L;

    private static final int MIN_ETHERNET_PAYLOAD_LENGTH = 46; // [bytes]

    private final EthernetHeader header;
    private final Packet payload;

    public EthernetPacket(EthernetHeader header, Packet payload) {
        this.header = header;
        this.payload = payload;
    }

    @Override
    public Header getHeader() {
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
    public static final class EthernetHeader implements Header {
        private static final long serialVersionUID = -8271269099161190389L;

        private static final int DST_ADDR_OFFSET = 0;
        private static final int DST_ADDR_SIZE = MacAddress.SIZE_IN_BYTES;
        private static final int SRC_ADDR_OFFSET = DST_ADDR_OFFSET + DST_ADDR_SIZE;
        private static final int SRC_ADDR_SIZE = MacAddress.SIZE_IN_BYTES;
        private static final int TYPE_OFFSET = SRC_ADDR_OFFSET + SRC_ADDR_SIZE;
        private static final int TYPE_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int ETHERNET_HEADER_SIZE = TYPE_OFFSET + TYPE_SIZE;

        private final MacAddress dstAddr;
        private final MacAddress srcAddr;
        private final EtherType type;

        public EthernetHeader(MacAddress dstAddr, MacAddress srcAddr, EtherType type) {
            this.dstAddr = dstAddr;
            this.srcAddr = srcAddr;
            this.type = type;
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
}
