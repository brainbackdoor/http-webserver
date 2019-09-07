package web.util;

import org.pcap4j.util.MacAddress;

import java.net.InetAddress;
import java.nio.ByteOrder;
import java.util.List;

import static java.nio.ByteOrder.LITTLE_ENDIAN;
import static java.util.stream.Collectors.*;

public class ByteUtils {

    public static final int BYTE_SIZE_IN_BYTES = 1;

    public static final int SHORT_SIZE_IN_BYTES = 2;

    public static final int INT_SIZE_IN_BYTES = 4;

    public static final int LONG_SIZE_IN_BYTES = 8;

    public static final int INET4_ADDRESS_SIZE_IN_BYTES = 4;

    public static final int INET6_ADDRESS_SIZE_IN_BYTES = 16;

    public static final int BYTE_SIZE_IN_BITS = 8;

    public static byte[] concatenate(List<byte[]> arrs) {
        int length = sumLength(arrs);

        return concatenate(arrs, new byte[length]);
    }

    private static byte[] concatenate(List<byte[]> arrs, byte[] result) {
        int destPos = 0;
        for (byte[] arr : arrs) {
            System.arraycopy(arr, 0, result, destPos, arr.length);
            destPos += arr.length;
        }
        return result;
    }

    public static byte[] toByteArray(int value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] toByteArray(int value, ByteOrder byteOrder) {
        if (byteOrder.equals(LITTLE_ENDIAN)) {
            return new byte[]{
                    (byte) (value),
                    (byte) (value >> BYTE_SIZE_IN_BITS * 1),
                    (byte) (value >> BYTE_SIZE_IN_BITS * 2),
                    (byte) (value >> BYTE_SIZE_IN_BITS * 3),
            };
        } else {
            return new byte[]{
                    (byte) (value >> BYTE_SIZE_IN_BITS * 3),
                    (byte) (value >> BYTE_SIZE_IN_BITS * 2),
                    (byte) (value >> BYTE_SIZE_IN_BITS * 1),
                    (byte) (value)
            };
        }
    }

    private static Integer sumLength(List<byte[]> arrs) {
        return arrs.stream().map(c -> c.length).collect(summingInt(Integer::intValue));
    }

    public static byte[] toByteArray(short value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] toByteArray(short value, ByteOrder byteOrder) {
        if (byteOrder.equals(LITTLE_ENDIAN)) {
            return new byte[]{(byte) (value), (byte) (value >> BYTE_SIZE_IN_BITS * 1)};
        }
        return new byte[]{(byte) (value >> BYTE_SIZE_IN_BITS * 1), (byte) (value)};
    }

    public static byte[] toByteArray(MacAddress value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] toByteArray(MacAddress value, ByteOrder byteOrder) {
        if (byteOrder.equals(LITTLE_ENDIAN)) {
            return reverse(value.getAddress());
        }

        return value.getAddress();
    }

    public static byte[] toByteArray(InetAddress value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] toByteArray(InetAddress value, ByteOrder byteOrder) {
        if (byteOrder.equals(LITTLE_ENDIAN)) {
            return reverse(value.getAddress());
        }

        return value.getAddress();
    }

    public static byte[] reverse(byte[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[array.length - i - 1];
        }

        return result;
    }
}
