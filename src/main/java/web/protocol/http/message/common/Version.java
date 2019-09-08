package web.protocol.http.message.common;

import java.util.Arrays;

public enum Version {
    HTTP_1_1("HTTP/1.1");

    private String value;

    Version(String value) {
        this.value = value;
    }

    public static Version of(String input) {
        return Arrays.stream(values())
                .filter(v -> v.value.equals(input))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
