package web.http.connection;

import java.util.Objects;
import java.util.regex.Pattern;

public class Host {
    private static String IP_ADDRESS_PATTERN =
            "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

    private final String address;

    private Host(final String address) {
        this.address = address;
    }

    public static Host of(final String name) {
        return new Host(name);
    }

    public static boolean isIpAddress(final String name) {
        Pattern pattern = Pattern.compile(IP_ADDRESS_PATTERN);
        return pattern.matcher(name).find();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return Objects.equals(address, host.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
