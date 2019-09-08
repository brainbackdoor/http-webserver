package web.protocol.ethernet;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import web.util.ByteUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class MacAddress {

    public static final int SIZE_IN_BYTES = 6;

    protected static final Pattern HEX_SEPARATOR_PATTERN = Pattern.compile("([^0-9a-fA-F])");

    public static final MacAddress ETHER_BROADCAST_ADDRESS =
            MacAddress.getByAddress(
                    new byte[] {(byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255});

    private final byte[] address;

    public static MacAddress getByName(String name) {
        Matcher m = HEX_SEPARATOR_PATTERN.matcher(name);
        m.find();
        return getByName(name, m.group(1));
    }

    public static MacAddress getByName(String name, String separator) {
        return getByAddress(ByteUtils.parseByteArray(name, separator));
    }

    public static MacAddress getByAddress(byte[] address) {
        if (address.length != SIZE_IN_BYTES) {
            return null;
//            throw new IllegalArgumentException("MacAddress Size가 맞지 않습니다.");
        }
        return new MacAddress(ByteUtils.clone(address));
    }

    @Override
    public String toString() {
        return ByteUtils.toHexString(address, ":");
    }
}