package web.http.message.response;

import java.util.Objects;

public class ReasonPhrase {
    private final String value;

    public ReasonPhrase(String value) {
        this.value = value;
    }

    public static ReasonPhrase of(String value) {
        return new ReasonPhrase(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReasonPhrase that = (ReasonPhrase) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
