package web.protocol.http.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import web.protocol.http.message.common.Header;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HeaderTest {

    @ParameterizedTest
    @MethodSource("parseHeader")
    @DisplayName("이름/값 쌍의 목록이다.")
    void constructor(String input) {
        Header header = Header.of(input);
        assertThat(header).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("exceptionHeader")
    @DisplayName("등록되지 않은 헤더타입의 경우")
    void exception(String input) {
        assertThrows(IllegalArgumentException.class, () -> Header.of(input));
    }

    private static Stream<Arguments> parseHeader() {
        return Stream.of(
                Arguments.of("Host: localhost:8080"),
                Arguments.of("Connection: keep-alive"),
                Arguments.of("Accept-Encoding: gzip, deflate, br")
        );
    }

    private static Stream<Arguments> exceptionHeader() {
        return Stream.of(
                Arguments.of("Hosts: localhost:8080"),
                Arguments.of("InValidConnection: keep-alive"),
                Arguments.of("AcceptEncoding: gzip, deflate, br")
        );
    }
}