package web.protocol.arp;

import lombok.*;
import web.protocol.Packet;
import web.protocol.ethernet.MacAddress;
import web.protocol.ethernet.Type;
import web.util.ByteUtils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import static web.util.ByteUtils.*;

@ToString
@EqualsAndHashCode
public class ArpPacket implements Packet {

    private final ArpHeader header;

    public ArpPacket(ArpHeader header) {
        this.header = header;
    }

    @Override
    public Header getHeader() {
        return header;
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

    @Builder
    @ToString
    @EqualsAndHashCode
    public static final class ArpHeader implements Header {

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
        private static final int SRC_PROTOCOL_ADDR_OFFSET = SRC_HARDWARE_ADDR_OFFSET + SRC_HARDWARE_ADDR_SIZE;
        private static final int SRC_PROTOCOL_ADDR_SIZE = INET4_ADDRESS_SIZE_IN_BYTES;
        private static final int DST_HARDWARE_ADDR_OFFSET = SRC_PROTOCOL_ADDR_OFFSET + SRC_PROTOCOL_ADDR_SIZE;
        private static final int DST_HARDWARE_ADDR_SIZE = MacAddress.SIZE_IN_BYTES;
        private static final int DST_PROTOCOL_ADDR_OFFSET = DST_HARDWARE_ADDR_OFFSET + DST_HARDWARE_ADDR_SIZE;
        private static final int DST_PROTOCOL_ADDR_SIZE = INET4_ADDRESS_SIZE_IN_BYTES;

        private static final int ARP_HEADER_SIZE = DST_PROTOCOL_ADDR_OFFSET + DST_PROTOCOL_ADDR_SIZE;

        private HardwareType hardwareType;
        private Type protocolType;
        private byte hardwareAddrLength;
        private byte protocolAddrLength;
        private Opcode opcode;
        private MacAddress srcHardwareAddr;
        private InetAddress srcProtocolAddr;
        private MacAddress dstHardwareAddr;
        private InetAddress dstProtocolAddr;

        @Override
        public int length() {
            return ARP_HEADER_SIZE;
        }

        @Override
        public List<byte[]> getRawFields() {
            List<byte[]> rawFields = new ArrayList<>();
            rawFields.add(ByteUtils.toByteArray(hardwareType.getValue()));
            rawFields.add(ByteUtils.toByteArray(protocolType.getValue()));
            rawFields.add(ByteUtils.toByteArray(hardwareAddrLength));
            rawFields.add(ByteUtils.toByteArray(protocolAddrLength));
            rawFields.add(ByteUtils.toByteArray(opcode.getValue()));
            rawFields.add(ByteUtils.toByteArray(srcHardwareAddr));
            rawFields.add(ByteUtils.toByteArray(srcProtocolAddr));
            rawFields.add(ByteUtils.toByteArray(dstHardwareAddr));
            rawFields.add(ByteUtils.toByteArray(dstProtocolAddr));
            return rawFields;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum HardwareType {
        ETHERNET((short) 1, "Ethernet (10mb)");

        private short value;
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public enum Opcode {
        REQUEST((short) 1, "REQUEST"),
        REPLY((short) 2, "REPLY");

        private short value;
        private String name;
    }
}
