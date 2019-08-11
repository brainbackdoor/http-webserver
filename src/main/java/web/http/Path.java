package web.http;

import java.util.Arrays;
import java.util.List;

public class Path {
    public static final String DELIMITER_PATH = "/";
    private List<String> values;

    public Path(String value) {
        this.values = Arrays.asList(value.split(DELIMITER_PATH));
    }

    public String get(int index) {
        return values.get(index);
    }
}
