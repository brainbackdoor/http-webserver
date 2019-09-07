package web.protocol.http.url;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParameterTest {
    @Test
    @DisplayName("파라미터는 '=' 문자를 기준으로 이름과 값의 쌍을 가진다.")
    void constructor() {
        String given = "protocolType=d";
        String invalidGiven = "protocolType?d";

        Parameter parameter = new Parameter(given);

        assertThat(parameter.getKey()).isEqualTo("protocolType");
        assertThat(parameter.getValue()).isEqualTo("d");

        assertThrows(IllegalArgumentException.class, () -> new Parameter(invalidGiven));
    }
}