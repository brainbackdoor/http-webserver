package web.util;

import org.apache.commons.lang3.StringUtils;
import web.protocol.ethernet.MacAddress;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.List;
import java.util.regex.Pattern;

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

    private static final Pattern NO_SEPARATOR_HEX_STRING_PATTERN =
            Pattern.compile("\\A([0-9a-fA-F][0-9a-fA-F])+\\z");

    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

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

    public static byte getByte(byte[] array, int offset) {
        validateBounds(array, offset, BYTE_SIZE_IN_BYTES);
        return array[offset];
    }

    public static Inet4Address getInet4Address(byte[] array, int offset) {
        return getInet4Address(array, offset, ByteOrder.BIG_ENDIAN);
    }

    public static Inet4Address getInet4Address(byte[] array, int offset, ByteOrder byteOrder) {
        validateBounds(array, offset, INET4_ADDRESS_SIZE_IN_BYTES);

        try {
            return (byteOrder.equals(LITTLE_ENDIAN))
                    ? (Inet4Address) InetAddress.getByAddress(reverse(getSubArray(array, offset, INET4_ADDRESS_SIZE_IN_BYTES)))
                    : (Inet4Address) InetAddress.getByAddress(getSubArray(array, offset, INET4_ADDRESS_SIZE_IN_BYTES));
        } catch (UnknownHostException e) {
            throw new AssertionError(e);
        }
    }

    public static byte[] toByteArray(int value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] toByteArray(int value, ByteOrder byteOrder) {
        return (byteOrder.equals(LITTLE_ENDIAN)) ?
                new byte[]{
                        (byte) (value),
                        (byte) (value >> BYTE_SIZE_IN_BITS * 1),
                        (byte) (value >> BYTE_SIZE_IN_BITS * 2),
                        (byte) (value >> BYTE_SIZE_IN_BITS * 3)} :
                new byte[]{
                        (byte) (value >> BYTE_SIZE_IN_BITS * 3),
                        (byte) (value >> BYTE_SIZE_IN_BITS * 2),
                        (byte) (value >> BYTE_SIZE_IN_BITS * 1),
                        (byte) (value)};
    }

    private static Integer sumLength(List<byte[]> arrs) {
        return arrs.stream().map(c -> c.length).collect(summingInt(Integer::intValue));
    }

    public static byte[] toByteArray(byte value) {
        return new byte[]{value};
    }

    public static byte[] toByteArray(short value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] toByteArray(short value, ByteOrder byteOrder) {
        return (byteOrder.equals(LITTLE_ENDIAN))
                ? new byte[]{(byte) (value), (byte) (value >> BYTE_SIZE_IN_BITS * 1)}
                : new byte[]{(byte) (value >> BYTE_SIZE_IN_BITS * 1), (byte) (value)};
    }

    public static byte[] toByteArray(MacAddress value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] toByteArray(MacAddress value, ByteOrder byteOrder) {
        return (byteOrder.equals(LITTLE_ENDIAN)) ? reverse(value.getAddress()) : value.getAddress();
    }

    public static byte[] toByteArray(InetAddress value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] toByteArray(InetAddress value, ByteOrder byteOrder) {
        return (byteOrder.equals(LITTLE_ENDIAN)) ? reverse(value.getAddress()) : value.getAddress();
    }

    public static byte[] reverse(byte[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[array.length - i - 1];
        }

        return result;
    }

    public static byte[] parseByteArray(String hexString, String separator) {
        if (hexString == null || separator == null) {
            throw new IllegalArgumentException("hexString 혹은 separator은 null일 수 없습니다.");
        }

        String result = getHexString(splitPrefix(hexString), separator);
        return convertToByteArray(result);
    }

    public static byte[] clone(byte[] array) {
        byte[] clone = new byte[array.length];
        System.arraycopy(array, 0, clone, 0, array.length);
        return clone;
    }

    public static String toHexString(byte[] array, String separator) {
        return toHexString(array, separator, 0, array.length);
    }

    public static String toHexString(byte[] array, String separator, int offset, int length) {
        validateBounds(array, offset, length);

        return String.valueOf(makeHexChars(array, separator, offset, length));
    }

    public static MacAddress getMacAddress(byte[] array, int offset) {
        return getMacAddress(array, offset, ByteOrder.BIG_ENDIAN);
    }

    public static MacAddress getMacAddress(byte[] array, int offset, ByteOrder byteOrder) {
        validate(array, offset, byteOrder);

        return getMacAddressByFormat(array, offset, byteOrder);
    }

    public static byte[] getSubArray(byte[] array, int offset, int length) {
        validateBounds(array, offset, length);

        byte[] subArray = new byte[length];
        System.arraycopy(array, offset, subArray, 0, length);
        return subArray;
    }

    public static short getShort(byte[] array, int offset) {
        return getShort(array, offset, ByteOrder.BIG_ENDIAN);
    }

    public static short getShort(byte[] array, int offset, ByteOrder byteOrder) {
        validateBounds(array, offset, SHORT_SIZE_IN_BYTES);

        if (byteOrder == null) {
            throw new NullPointerException(" byteOrder: " + byteOrder);
        }

        return getShortByFormat(array, offset, byteOrder);
    }

    private static short getShortByFormat(byte[] array, int offset, ByteOrder byteOrder) {
        return (byteOrder.equals(LITTLE_ENDIAN))
                ? (short) (((array[offset + 1]) << (BYTE_SIZE_IN_BITS * 1)) | ((0xFF & array[offset])))
                : (short) (((array[offset]) << (BYTE_SIZE_IN_BITS * 1)) | ((0xFF & array[offset + 1])));
    }

    private static byte[] convertToByteArray(String result) {
        int arrayLength = result.length() / 2;
        byte[] array = new byte[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            array[i] = (byte) Integer.parseInt(result.substring(i * 2, i * 2 + 2), 16);
        }
        return array;
    }

    private static String splitPrefix(String hexString) {
        return (hexString.startsWith("0x")) ? hexString.substring(2) : hexString;
    }

    private static String getHexString(String hexString, String separator) {
        return (separator.isEmpty()) ? getHexString(hexString) : separate(hexString, separator);
    }

    private static boolean isMatched(String hexString) {
        return isMatched(hexString, NO_SEPARATOR_HEX_STRING_PATTERN);
    }

    private static boolean isMatched(String hexString, Pattern pattern) {
        return !pattern.matcher(hexString).matches();
    }

    private static String makePattern(String separator) {
        StringBuilder patternSb = new StringBuilder(60);
        patternSb
                .append("\\A[0-9a-fA-F][0-9a-fA-F](")
                .append(Pattern.quote(separator))
                .append("[0-9a-fA-F][0-9a-fA-F])*\\z");
        return patternSb.toString();
    }

    private static void validate(byte[] array, int offset, ByteOrder byteOrder) {
        validateBounds(array, offset, MacAddress.SIZE_IN_BYTES);

        if (byteOrder == null) {
            throw new NullPointerException(" byteOrder: " + byteOrder);
        }
    }

    private static MacAddress getMacAddressByFormat(byte[] array, int offset, ByteOrder byteOrder) {
        return (byteOrder.equals(LITTLE_ENDIAN))
                ? MacAddress.getByAddress(reverse(getSubArray(array, offset, MacAddress.SIZE_IN_BYTES)))
                : MacAddress.getByAddress(getSubArray(array, offset, MacAddress.SIZE_IN_BYTES));
    }

    public static void validateBounds(byte[] arr, int offset, int len) {
        if (arr == null) {
            throw new NullPointerException("arr must not be null.");
        }

        if (arr.length == 0) {
            throw new IllegalArgumentException("arr is empty.");
        }

        if (len == 0) {
            throw new IllegalArgumentException("length is zero.");
        }

        if (offset < 0 || len < 0 || offset + len > arr.length) {
            throw new ArrayIndexOutOfBoundsException("arr.length: " + arr.length);
        }
    }

    private static char[] makeHexChars(byte[] array, String separator, int offset, int length) {
        return (separator.length() != 0)
                ? makeHexCharsBySeparator(array, separator, offset, length)
                : makeHexCharsByNone(array, offset, length);
    }

    private static char[] makeHexCharsByNone(byte[] array, int offset, int length) {
        char[] hexChars = new char[length * 2];

        for (int i = 0, cur = 0; i < length; i++) {
            cur = makeHexChar(hexChars, cur, array[offset + i]);
        }

        return hexChars;
    }

    private static char[] makeHexCharsBySeparator(byte[] array, String separator, int offset, int length) {
        char[] hexChars;
        char[] sepChars = separator.toCharArray();
        hexChars = new char[length * 2 + sepChars.length * (length - 1)];
        int cur = 0;
        int i = 0;
        for (; i < length - 1; i++) {
            cur = makeHexChar(hexChars, cur, array[offset + i]);
            cur = nextSeparator(hexChars, sepChars, cur);
        }
        int v = array[offset + i] & 0xFF;
        hexChars[cur] = HEX_CHARS[v >>> 4];
        hexChars[cur + 1] = HEX_CHARS[v & 0x0F];
        return hexChars;
    }

    private static int nextSeparator(char[] hexChars, char[] sepChars, int cur) {
        for (int j = 0; j < sepChars.length; j++) {
            hexChars[cur] = sepChars[j];
            cur++;
        }
        return cur;
    }

    private static int makeHexChar(char[] hexChars, int cur, byte b) {
        int v = b & 0xFF;
        hexChars[cur] = HEX_CHARS[v >>> 4];
        cur++;
        hexChars[cur] = HEX_CHARS[v & 0x0F];
        cur++;
        return cur;
    }

    private static String separate(String hexString, String separator) {
        if (isMatched(hexString, Pattern.compile(makePattern(separator)))) {
            throw new IllegalArgumentException("invalid hex string");
        }

        return hexString.replaceAll(Pattern.quote(separator), StringUtils.EMPTY);
    }

    private static String getHexString(String hexString) {
        if (isMatched(hexString)) {
            throw new IllegalArgumentException("invalid hex string");
        }

        return hexString;
    }
}
