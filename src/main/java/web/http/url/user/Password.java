package web.http.url.user;

import java.util.Objects;

public class Password {
    private final String value;

    public Password() {
        this("chrome@example.com");
    }

    public Password(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
