package web.protocol.tcp.option;

import lombok.Builder;
import lombok.ToString;
import web.protocol.tcp.TcpPacket.TcpOption;
import web.protocol.tcp.TcpPacket.TcpOptionKind;

import static web.protocol.tcp.TcpPacket.TcpOptionKind.MAXIMUM_SEGMENT_SIZE;

@ToString
@Builder
public class TcpMaximumSegmentSizeOption implements TcpOption {

    private final TcpOptionKind kind = MAXIMUM_SEGMENT_SIZE;
    private final byte length;
    private final short maxSegSize;

    @Override
    public TcpOptionKind getKind() {
        return kind;
    }

    @Override
    public int length() {
        return 4;
    }

    @Override
    public byte[] getRawData() {
        byte[] rawData = new byte[length()];
        rawData[0] = kind.getValue();
        rawData[1] = length;
        rawData[2] = (byte) (maxSegSize >> 8);
        rawData[3] = (byte) maxSegSize;
        return rawData;
    }
}
