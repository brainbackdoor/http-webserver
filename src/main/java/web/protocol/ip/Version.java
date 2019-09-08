package web.protocol.ip;

import lombok.Getter;

@Getter
public enum Version {

    IPV4((byte) 4, "IPv4");

    private byte value;
    private String name;

    Version(byte value, String name) {
        this.value = value;
        this.name = name;
    }
}
