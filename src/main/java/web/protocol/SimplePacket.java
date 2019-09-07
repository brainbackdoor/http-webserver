package web.protocol;

import lombok.ToString;

@ToString
public class SimplePacket implements Packet {

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
        byte[] bytes = new byte[50];
        bytes[0] = 1;
        bytes[1] = 2;
        bytes[2] = 3;
        return bytes;
    }
}
