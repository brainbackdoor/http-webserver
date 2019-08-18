package web.http.url;

import java.util.regex.Pattern;

public class QueryString {
    public static final String KEY_VALUE_PATTERN = "(.*)=(.*)";
    public static final String DELIMITER_QUERY_STRING = "=";

    private final String key;
    private final String value;

    public QueryString(String input) {
        validate(input);
        String[] splits = input.split(DELIMITER_QUERY_STRING);
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
            throw new IllegalArgumentException("QueryString이 key / value 쌍이 아닙니다.");
        }
    }

    private boolean isKeyValuePattern(String input) {
        Pattern pattern = Pattern.compile(KEY_VALUE_PATTERN);
        return pattern.matcher(input).find();
    }
}
