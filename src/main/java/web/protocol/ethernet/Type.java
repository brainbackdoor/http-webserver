package web.protocol.ethernet;

import lombok.Getter;

@Getter
public enum Type {

    IPV4((short) 0x0800, "IPv4"),
    ARP((short) 0x0806, "ARP");

    private short value;
    private String name;

    Type(short value, String name) {
        this.value = value;
        this.name = name;
    }
}