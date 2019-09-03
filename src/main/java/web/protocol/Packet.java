package web.protocol;

import java.io.Serializable;

public interface Packet extends Iterable<Packet>, Serializable {

    Header getHeader();

    Packet getPayload();

    int length();

    byte[] getRawData();

    <T extends Packet> T get(Class<T> clazz);

    Packet getOuterOf(Class<? extends Packet> clazz);

    <T extends Packet> boolean contains(Class<T> clazz);

    Builder getBuilder();

    interface Builder extends Iterable<Builder> {

        <T extends Packet.Builder> T get(Class<T> clazz);

        Builder getOuterOf(Class<? extends Builder> clazz);

        Builder payloadBuilder(Builder payloadBuilder);

        Builder getPayloadBuilder();

        Packet build();
    }

    interface Header extends Serializable {

        int length();

        byte[] getRawData();
    }
}
