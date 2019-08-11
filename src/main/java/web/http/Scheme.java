package web.http;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class Scheme {
    public static final String RULE_SCHEME_START_WITH_ALPHABET = "^[a-zA-Z]";
    private final Type name;

    private Scheme(String name) {
        validate(name);
        this.name = Type.of(name.toUpperCase());
    }

    public static Scheme of(String name) {
        return new Scheme(name);
    }

    public int getPortNumber() {
        return name.port;
    }

    private void validate(String name) {
        if(!isStartWithAlphabet(name)) {
            throw new IllegalArgumentException("스킴은 알파벳으로 시작해야 한다.");
        }
    }

    private boolean isStartWithAlphabet(String name) {
        Pattern pattern = Pattern.compile(RULE_SCHEME_START_WITH_ALPHABET);
        return pattern.matcher(name).find();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scheme scheme = (Scheme) o;
        return Objects.equals(name, scheme.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public enum Type {
        HTTP(80),
        HTTPS(443),
        MAILTO(Integer.MIN_VALUE),
        FTP(Integer.MIN_VALUE),
        RSTP(Integer.MIN_VALUE),
        FILE(Integer.MIN_VALUE),
        TELNET(Integer.MIN_VALUE);

        private int port;

        Type(int port) {
            this.port = port;
        }

        static Type of(String name) {
            return Arrays.stream(values()).filter(type -> type.name().equals(name)).findFirst().get();
        }
    }
}
