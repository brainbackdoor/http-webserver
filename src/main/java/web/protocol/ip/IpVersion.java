package web.protocol.ip;

import web.protocol.NamedNumber;

import java.util.HashMap;
import java.util.Map;

public class IpVersion  extends NamedNumber<Byte, IpVersion> {

    private static final long serialVersionUID = 3155818580398801532L;

    public static final IpVersion IPV4 = new IpVersion((byte) 4, "IPv4");

    public IpVersion(Byte value, String name) {
        super(value, name);
        if ((value & 0xF0) != 0) {
            throw new IllegalArgumentException(
                    value + " is invalid value. " + "Version field of IP header must be between 0 and 15");
        }
    }

    private static final Map<Byte, IpVersion> registry = new HashMap<>();

    static {
        registry.put(IPV4.value(), IPV4);
    }

    @Override
    public int compareTo(IpVersion o) {
        return value().compareTo(o.value());
    }
}
