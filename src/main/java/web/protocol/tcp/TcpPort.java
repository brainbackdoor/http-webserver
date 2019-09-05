package web.protocol.tcp;

import java.util.HashMap;
import java.util.Map;

public final class TcpPort extends Port {

    private static final long serialVersionUID = 3906499626286793530L;


    public static final TcpPort SSH = new TcpPort((short) 22, "SSH");

    public static final TcpPort TELNET = new TcpPort((short) 23, "Telnet");

    public static final TcpPort HTTP = new TcpPort((short) 80, "HTTP");

    private static final Map<Short, TcpPort> registry = new HashMap<>();

    static {
        registry.put(SSH.value(), SSH);
        registry.put(TELNET.value(), TELNET);
        registry.put(HTTP.value(), HTTP);
    }

    public TcpPort(Short value, String name) {
        super(value, name);
    }

    public static TcpPort getInstance(Short value) {
        if (registry.containsKey(value)) {
            return registry.get(value);
        }
        return new TcpPort(value, "unknown");
    }

    public static TcpPort register(TcpPort port) {
        return registry.put(port.value(), port);
    }
}
