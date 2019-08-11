package web.http;

import java.util.Objects;

public class Port {
    private final int number;

    public Port(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Port port = (Port) o;
        return number == port.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
