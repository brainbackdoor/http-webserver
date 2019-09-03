package web.protocol;

import java.util.Iterator;

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

    @Override
    public String toString() {
        return "SimplePacket{}";
    }
}
