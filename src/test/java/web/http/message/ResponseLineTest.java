package web.http.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.http.message.common.Version;
import web.http.message.response.ReasonPhrase;
import web.http.message.response.ResponseLine;
import web.http.message.response.StatusCode;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseLineTest {
    Version version = Version.HTTP_1_1;
    StatusCode statusCode = StatusCode.MOVED_PERMANENTLY;
    ReasonPhrase reasonPhrase = new ReasonPhrase("OK");

    @Test
    @DisplayName("Version, StatusCode, ReasonPhrase로 구성된다.")
    void constructor() {
        ResponseLine responseLine = new ResponseLine(version, statusCode, reasonPhrase);
        assertThat(responseLine).isNotNull();
    }

    @Test
    @DisplayName("각 필드는 공백으로 구분된다.")
    void splitByBlank() {
        ResponseLine expected = new ResponseLine(version, statusCode, reasonPhrase);
        String input = "HTTP/1.1 301 OK";

        ResponseLine line = ResponseLine.of(input);
        assertThat(line).isEqualTo(expected);
    }
}