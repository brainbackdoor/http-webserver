package web.protocol.tcp;

import web.protocol.NamedNumber;

public abstract class Port extends NamedNumber<Short, Port> {
    public Port(Short value, String name) {
        super(value, name);
    }

    public int valueAsInt() {
        return 0xFFFF & value();
    }

    @Override
    public String valueAsString() {
        return String.valueOf(valueAsInt());
    }

    @Override
    public int compareTo(Port o) {
        return value().compareTo(o.value());
    }
}
