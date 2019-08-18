package web.http.url;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.*;

public class PathCarving {
    public static final String DELIMITER_PATH_CARVING = ";";
    private String value;
    private List<Parameter> parameters;

    public PathCarving(String input) {
        this.parameters = extract(input).stream().map(Parameter::new).collect(toList());
    }

    public Parameter getParameter(int index) {
        return parameters.get(index);
    }

    private List<String> extract(String input) {
        List<String> splits = split(input);
        value = splits.get(0);
        splits.remove(value);
        return splits;
    }

    private ArrayList<String> split(String input) {
        return new ArrayList<>(Arrays.asList(input.split(DELIMITER_PATH_CARVING)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathCarving that = (PathCarving) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, parameters);
    }

    @Override
    public String toString() {
        return "PathCarving{" +
                "value='" + value + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
