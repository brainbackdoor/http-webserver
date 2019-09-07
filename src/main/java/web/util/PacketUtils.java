package web.util;

import web.protocol.Packet;
import web.protocol.Packet.Header;

public class PacketUtils {

    public static int copyHeader(Packet packet, byte[] result, int dstPos) {
        Header header = packet.getHeader();
        if (header != null) {
            System.arraycopy(getRawData(header), 0, result, dstPos, header.length());
            dstPos += header.length();
        }
        return dstPos;
    }

    public static void copyPayload(Packet packet, byte[] result, int dstPos) {
        Packet payload = packet.getPayload();
        if (payload != null) {
            System.arraycopy(payload.getRawData(), 0, result, dstPos, payload.length());
        }
    }

    private static byte[] getRawData(Header header) {
        byte[] rawData = ByteUtils.concatenate(header.getRawFields());

        byte[] copy = new byte[rawData.length];
        System.arraycopy(rawData, 0, copy, 0, copy.length);
        return copy;
    }
}
