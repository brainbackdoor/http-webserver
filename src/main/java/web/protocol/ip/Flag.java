package web.protocol.ip;

import lombok.Getter;

@Getter
public enum Flag {
    MORE_FRAGMENT((byte) 1),
    DONT_FRAGMENT((byte) 2),
    RESERVED((byte) 4);

    private byte value;

    Flag(byte value) {
        this.value = value;
    }
}
