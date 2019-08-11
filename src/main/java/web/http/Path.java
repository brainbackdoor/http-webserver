package web.http;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

public class Path {
    public static final String DELIMITER_PATH = "/";
    private List<PathCarving> values;


    public Path(String value) {
        List<String> splitedValues = Arrays.asList(value.split(DELIMITER_PATH));

        this.values = splitedValues.stream().map(PathCarving::new).collect(toList());
    }

    public PathCarving get(int index) {
        return values.get(index);
    }
}
