package web.http;

import java.util.regex.Pattern;

public class Scheme {
    public static final String RULE_SCHEME_START_WITH_ALPHABET = "^[a-zA-Z]";
    private final String name;

    private Scheme(String name) {
        validate(name);
        this.name = name;
    }

    public static Scheme of(String name) {
        return new Scheme(name);
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
}
