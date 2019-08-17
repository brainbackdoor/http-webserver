package web.http.url;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.*;

public class Path {
    public static final String DELIMITER_PATH = "/";
    private List<PathCarving> values;


    private Path(String value) {
        List<String> splitedValues = Arrays.asList(value.split(DELIMITER_PATH));

        this.values = splitedValues.stream().map(PathCarving::new).collect(toList());
    }

    public static Path of(String value) {
        return new Path(value);
    }

    public PathCarving get(int index) {
        return values.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return Objects.equals(values, path.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }
}
