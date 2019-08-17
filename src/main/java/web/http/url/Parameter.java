package web.http.url;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.regex.Pattern;

public class Parameter {

    public static final String KEY_VALUE_PATTERN = "(.*)=(.*)";
    public static final String DELIMITER_PARAMETER = "=";
    private Map<String, String> value = Maps.newHashMap();

    public Parameter(String input) {
        validate(input);
        String[] splitedInput = input.split(DELIMITER_PARAMETER);
        value.put(splitedInput[0], splitedInput[1]);
    }

    public String getKey() {
        return String.valueOf(value.keySet().toArray()[0]);
    }

    public String getValue() {
        return String.valueOf(value.values().toArray()[0]);
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