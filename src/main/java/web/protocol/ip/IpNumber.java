package web.protocol.ip;

import web.protocol.NamedNumber;

import java.util.HashMap;
import java.util.Map;

public class IpNumber extends NamedNumber<Byte, IpNumber> {
    public static final IpNumber ICMPV4 = new IpNumber((byte) 1, "ICMPv4");
    public static final IpNumber TCP = new IpNumber((byte) 6, "TCP");
    public static final IpNumber UDP = new IpNumber((byte) 17, "UDP");

    private static final Map<Byte, IpNumber> registry = new HashMap<>();

    static {
        registry.put(ICMPV4.value(), ICMPV4);
        registry.put(TCP.value(), TCP);
        registry.put(UDP.value(), UDP);
    }

    public IpNumber(Byte value, String name) {
        super(value, name);
    }

    @Override
    public int compareTo(IpNumber o) {
        return value().compareTo(o.value());
    }
}
