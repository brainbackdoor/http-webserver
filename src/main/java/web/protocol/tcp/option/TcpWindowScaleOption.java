package web.protocol.tcp.option;

import lombok.Builder;
import lombok.ToString;
import web.protocol.tcp.TcpPacket.TcpOption;
import web.protocol.tcp.TcpPacket.TcpOptionKind;

@ToString
@Builder
public class TcpWindowScaleOption implements TcpOption {
    private final TcpOptionKind kind = TcpOptionKind.WINDOW_SCALE;
    private final byte length = 3;
    private final byte shiftCount;

    @Override
    public TcpOptionKind getKind() {
        return kind;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public byte[] getRawData() {
        byte[] rawData = new byte[length()];
        rawData[0] = kind.getValue();
        rawData[1] = length;
        rawData[2] = shiftCount;
        return rawData;
    }
}
