package web.protocol.ethernet;

import lombok.Builder;
import org.pcap4j.util.MacAddress;
import web.protocol.Packet;
import web.util.ByteUtils;

import java.util.ArrayList;
import java.util.List;

import static web.util.PacketUtils.copyHeader;
import static web.util.PacketUtils.copyPayload;

public class EthernetPacket implements Packet {

    private final EthernetHeader header;
    private final Packet payload;

    public EthernetPacket(EthernetHeader header, Packet payload) {
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
    public static final class EthernetHeader implements Header {

        private static final int ETHERNET_HEADER_SIZE = 14;

        private final MacAddress dstAddr;
        private final MacAddress srcAddr;
        private final Type type;

        @Override
        public int length() {
            return ETHERNET_HEADER_SIZE;
        }


        @Override
        public List<byte[]> getRawFields() {
            List<byte[]> rawFields = new ArrayList<>();
            rawFields.add(ByteUtils.toByteArray(dstAddr));
            rawFields.add(ByteUtils.toByteArray(srcAddr));
            rawFields.add(ByteUtils.toByteArray(type.getValue()));
            return rawFields;
        }
    }
}
