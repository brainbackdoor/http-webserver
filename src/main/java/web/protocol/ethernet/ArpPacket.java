package web.protocol.ethernet;

import org.pcap4j.packet.namednumber.ArpHardwareType;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;
import web.protocol.Packet;

import java.net.InetAddress;
import java.util.Iterator;

import static org.pcap4j.util.ByteArrays.*;

public class ArpPacket implements Packet {
    private static final long serialVersionUID = -7754807127571498700L;

    private final ArpHeader header;

    public ArpPacket(ArpHeader header) {
        this.header = header;
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

    public static final class ArpHeader implements Header {
        private static final long serialVersionUID = -6744946002881067732L;

        private static final int HARDWARE_TYPE_OFFSET = 0;
        private static final int HARDWARE_TYPE_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int PROTOCOL_TYPE_OFFSET = HARDWARE_TYPE_OFFSET + HARDWARE_TYPE_SIZE;
        private static final int PROTOCOL_TYPE_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int HW_ADDR_LENGTH_OFFSET = PROTOCOL_TYPE_OFFSET + PROTOCOL_TYPE_SIZE;
        private static final int HW_ADDR_LENGTH_SIZE = BYTE_SIZE_IN_BYTES;
        private static final int PROTO_ADDR_LENGTH_OFFSET = HW_ADDR_LENGTH_OFFSET + HW_ADDR_LENGTH_SIZE;
        private static final int PROTO_ADDR_LENGTH_SIZE = BYTE_SIZE_IN_BYTES;
        private static final int OPERATION_OFFSET = PROTO_ADDR_LENGTH_OFFSET + PROTO_ADDR_LENGTH_SIZE;
        private static final int OPERATION_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int SRC_HARDWARE_ADDR_OFFSET = OPERATION_OFFSET + OPERATION_SIZE;
        private static final int SRC_HARDWARE_ADDR_SIZE = MacAddress.SIZE_IN_BYTES;
        private static final int SRC_PROTOCOL_ADDR_OFFSET =
                SRC_HARDWARE_ADDR_OFFSET + SRC_HARDWARE_ADDR_SIZE;
        private static final int SRC_PROTOCOL_ADDR_SIZE = INET4_ADDRESS_SIZE_IN_BYTES;
        private static final int DST_HARDWARE_ADDR_OFFSET =
                SRC_PROTOCOL_ADDR_OFFSET + SRC_PROTOCOL_ADDR_SIZE;
        private static final int DST_HARDWARE_ADDR_SIZE = MacAddress.SIZE_IN_BYTES;
        private static final int DST_PROTOCOL_ADDR_OFFSET =
                DST_HARDWARE_ADDR_OFFSET + DST_HARDWARE_ADDR_SIZE;
        private static final int DST_PROTOCOL_ADDR_SIZE = INET4_ADDRESS_SIZE_IN_BYTES;
        private static final int ARP_HEADER_SIZE = DST_PROTOCOL_ADDR_OFFSET + DST_PROTOCOL_ADDR_SIZE;

        private final ArpHardwareType hardwareType;
        private final EtherType protocolType;
        private final byte hardwareAddrLength;
        private final byte protocolAddrLength;
        private final ArpOperation operation;
        private final MacAddress srcHardwareAddr;
        private final InetAddress srcProtocolAddr;
        private final MacAddress dstHardwareAddr;
        private final InetAddress dstProtocolAddr;

        public ArpHeader(ArpHardwareType hardwareType, EtherType protocolType, byte hardwareAddrLength, byte protocolAddrLength, ArpOperation operation, MacAddress srcHardwareAddr, InetAddress srcProtocolAddr, MacAddress dstHardwareAddr, InetAddress dstProtocolAddr) {
            this.hardwareType = hardwareType;
            this.protocolType = protocolType;
            this.hardwareAddrLength = hardwareAddrLength;
            this.protocolAddrLength = protocolAddrLength;
            this.operation = operation;
            this.srcHardwareAddr = srcHardwareAddr;
            this.srcProtocolAddr = srcProtocolAddr;
            this.dstHardwareAddr = dstHardwareAddr;
            this.dstProtocolAddr = dstProtocolAddr;
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
