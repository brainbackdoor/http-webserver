package web.protocol.http.url;

import java.util.regex.Pattern;

public class Parameter {

    public static final String KEY_VALUE_PATTERN = "(.*)=(.*)";
    public static final String DELIMITER_PARAMETER = "=";

    private final String key;
    private final String value;

    public Parameter(String input) {
        validate(input);
        String[] splits = input.split(DELIMITER_PARAMETER);
        this.key = splits[0];
        this.value = splits[1];
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    private void validate(String input) {
        if (!isKeyValuePattern(input)) {
            throw new IllegalArgumentException("Parameter가 key / value 쌍이 아닙니다.");
        }
    }

    private boolean isKeyValuePattern(String input) {
        Pattern pattern = Pattern.compile(KEY_VALUE_PATTERN);
        return pattern.matcher(input).find();
    }

}
