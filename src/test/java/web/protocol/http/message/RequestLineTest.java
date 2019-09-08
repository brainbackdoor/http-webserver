package web.protocol.http.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.http.message.common.Version;
import web.protocol.http.message.request.Method;
import web.protocol.http.message.request.RequestLine;
import web.protocol.http.message.request.RequestURL;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    Method method = Method.GET;
    RequestURL url = new RequestURL("/test/hello");
    Version version = Version.HTTP_1_1;

    @Test
    @DisplayName("Method, RequestURL, Version으로 구성된다.")
    void constructor() {
        RequestLine actual = new RequestLine(method, url, version);
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("각 필드는 공백으로 구분된다.")
    void splitByBlank() {
        String input = "GET /test/hello HTTP/1.1";
        RequestLine expected = new RequestLine(method, url, version);

        RequestLine line = RequestLine.of(input);
        assertThat(line).isEqualTo(expected);
    }
}