package web.protocol.http.url.user;

import java.util.Objects;

public class UserName {
    private final String value;

    public UserName() {
        this("anonymous");
    }

    public UserName(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserName userName = (UserName) o;
        return Objects.equals(value, userName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
