package web.http.url.connection;

import java.util.Objects;

public class Port {
    private final int number;

    private Port(int number) {
        this.number = number;
    }

    public static Port of(int number) {
        return new Port(number);
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

    @Override
    public String toString() {
        return "Port{" +
                "number=" + number +
                '}';
    }
}
