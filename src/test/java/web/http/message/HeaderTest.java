package web.http.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class HeaderTest {

    @ParameterizedTest
    @MethodSource("parseHeader")
    @DisplayName("이름/값 쌍의 목록이다.")
    void constructor(String input) {
        Header header = Header.of(input);
        assertThat(header).isNotNull();
    }

    private static Stream<Arguments> parseHeader() {
        return Stream.of(
                Arguments.of("Host: localhost:8080"),
                Arguments.of("Connection: keep-alive"),
                Arguments.of("Accept-Encoding: gzip, deflate, br")
        );
    }
}