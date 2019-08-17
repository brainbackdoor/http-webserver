package web.http.message;


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityBody {
    private List<Parameter> parameters;

    public EntityBody(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public static EntityBody of(String rawParameters) {
        return new EntityBody(ParameterParser.parseParameter(rawParameters));
    }

    public static class Parameter {
        private String key;
        private String value;

        public String getValue() {
            return value;
        }

        public Parameter(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class ParameterParser {
        public static final String EQUALS = "=";
        public static final String AMPERSAND = "&";
        public static final int QUERY_MIN_SIZE = 2;
        public static final int QUERY_LIST_PREDICATE = 1;

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
}
