package web.http.message;


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EntityBody {
    private List<Parameter> parameters;

    public EntityBody(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public static EntityBody of(String rawParameters) {
        return new EntityBody(ParameterParser.parseParameter(rawParameters));
    }

    public String get(String key) {
        return parameters.stream().filter(v -> v.key.equals(key)).findFirst().get().value;
    }

    public static class Parameter {
        private String key;
        private String value;

        public Parameter(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Parameter parameter = (Parameter) o;
            return Objects.equals(key, parameter.key) &&
                    Objects.equals(value, parameter.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public String toString() {
            return "Parameter{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    private static class ParameterParser {
        public static final String EQUALS = "=";
        public static final String AMPERSAND = "&";
        public static final int QUERY_MIN_SIZE = 2;

        private static List<Parameter> parseParameter(String queryString) {
            return Arrays.stream(queryString.split(AMPERSAND))
                    .filter(StringUtils::isNotBlank)
                    .map(ParameterParser::parseQuery)
                    .filter(ParameterParser::hasNotValue)
                    .map(it -> new Parameter(it[0], it[1]))
                    .collect(Collectors.toList());
        }

        private static boolean hasNotValue(String[] it) {
            return it.length == QUERY_MIN_SIZE;
        }

        private static String[] parseQuery(String it) {
            return it.split(EQUALS);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityBody that = (EntityBody) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }

    @Override
    public String toString() {
        return "EntityBody{" +
                "parameters=" + parameters +
                '}';
    }
}
