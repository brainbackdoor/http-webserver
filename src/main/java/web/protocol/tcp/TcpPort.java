package web.protocol.tcp;

import lombok.Getter;

@Getter
public enum TcpPort {

    NONE((short) 53661, "NONE"),
    SSH((short) 22, "SSH"),
    TELNET((short) 23, "Telnet"),
    HTTP((short) 80, "HTTP");

    private short value;
    private String name;

    TcpPort(short value, String name) {
        this.value = value;
        this.name = name;
    }
}
