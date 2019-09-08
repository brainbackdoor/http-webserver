package web.protocol.ip;

import lombok.Getter;

@Getter
public enum  ProtocolIdentifier {

    ICMP_V4((byte) 1, "ICMPv4"),
    TCP((byte) 6, "TCP"),
    UDP((byte) 17, "UDP");

    private byte value;
    private String name;

    ProtocolIdentifier(byte value, String name) {
        this.value = value;
        this.name = name;
    }
}
