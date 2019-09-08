package web.protocol.tcp;

import lombok.Getter;

@Getter
public enum Flag {

    FIN((byte) 1),
    SYN((byte) 2),
    RST((byte) 4),
    PSH((byte) 8),
    ACK((byte) 16),
    URG((byte) 32);

    private byte value;

    Flag(byte value) {
        this.value = value;
    }
}
