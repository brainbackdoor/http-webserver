package web.protocol.tcp.option;

import lombok.Builder;
import lombok.ToString;
import web.protocol.tcp.TcpPacket.TcpOption;
import web.protocol.tcp.TcpPacket.TcpOptionKind;
import web.util.ByteUtils;

@ToString
@Builder
public class TcpTimestampsOption implements TcpOption {

    private final TcpOptionKind kind = TcpOptionKind.TIMESTAMPS;
    private final byte length = 10;
    private final int tsValue;
    private final int tsEchoReply;

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
        System.arraycopy(ByteUtils.toByteArray(tsValue), 0, rawData, 2, ByteUtils.INT_SIZE_IN_BYTES);
        System.arraycopy(ByteUtils.toByteArray(tsEchoReply), 0, rawData, 6, ByteUtils.INT_SIZE_IN_BYTES);
        return rawData;
    }
}
