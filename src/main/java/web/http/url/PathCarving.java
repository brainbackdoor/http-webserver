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
        List<String> splitedValue = new ArrayList<>(Arrays.asList(input.split(DELIMITER_PATH_CARVING)));
        value = splitedValue.get(0);
        splitedValue.remove(value);
        this.parameters = splitedValue.stream().map(Parameter::new).collect(toList());
    }

    public Parameter getParameter(int index) {
        return parameters.get(index);
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
}
