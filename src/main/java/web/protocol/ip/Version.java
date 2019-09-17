package web.protocol.ip;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Version {

    IPV4((byte) 4, "IPv4");

    private byte value;
    private String name;

    Version(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Version getInstance(byte value) {
        return Arrays.stream(Version.values()).filter(v -> v.value == value).findFirst().get();
    }
}
