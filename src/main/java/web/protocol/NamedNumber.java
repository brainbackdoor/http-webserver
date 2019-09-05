package web.protocol;

import java.io.Serializable;

public abstract class NamedNumber<T extends Number, U extends NamedNumber<T, ?>>
        implements Comparable<U>, Serializable {

    private static final long serialVersionUID = 3858426889927624965L;

    private final T value;
    private final String name;

    protected NamedNumber(T value, String name) {
        if (value == null) {
            throw new IllegalArgumentException("value is null.");
        }
        if (name == null) {
            throw new IllegalArgumentException("name is null.");
        }
        this.value = value;
        this.name = name;
    }

    public T value() {
        return value;
    }

    public String name() {
        return name;
    }

    public String valueAsString() {
        return value.toString();
    }

    @Override
    public abstract int compareTo(U o);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(70);
        return sb.append(valueAsString()).append(" (").append(name).append(")").toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + value.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NamedNumber<?, ?> other = (NamedNumber<?, ?>) obj;
        if (!name.equals(other.name)) {
            return false;
        }
        if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }
}
