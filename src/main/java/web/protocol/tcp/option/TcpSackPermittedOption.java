package web.protocol.tcp.option;

import lombok.Builder;
import lombok.ToString;
import web.protocol.tcp.TcpPacket.TcpOption;
import web.protocol.tcp.TcpPacket.TcpOptionKind;

@ToString
@Builder
public class TcpSackPermittedOption implements TcpOption {

    private static final TcpSackPermittedOption INSTANCE = new TcpSackPermittedOption();

    private static final TcpOptionKind kind = TcpOptionKind.SACK_PERMITTED;
    private static final byte length = 2;

    public static TcpSackPermittedOption getInstance() {
        return INSTANCE;
    }

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
        return rawData;
    }
}
