package web.http.message;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.http.support.file.FileLoader;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static web.http.message.Version.HTTP_1_1;

class RequestMessageTest {

    @Test
    @DisplayName("RequestMessage는 RequestLine, Header, EntityBody로 구성된다.")
    void constructor() {
        RequestLine line = new RequestLine(Method.GET, new RequestURL("/test/hello"), HTTP_1_1);
        Headers header = Headers.of(Lists.list("Host: localhost:8080"));
        EntityBody body = EntityBody.of(StringUtils.EMPTY);

        assertThat(new RequestMessage(line, header, body)).isNotNull();
    }

    @Test
    @DisplayName("RequestLine과 Header는 줄바꿈 문자열로 끝난다.")
    void csrf() throws IOException {
        RequestLine expectedRequestLine = RequestLine.of("GET /index.html HTTP/1.1");

        InputStream in = FileLoader.load("Http_GET_index.txt");
        RequestMessage message = RequestMessageFactory.of(in);

        assertThat(message.getRequestLine()).isEqualTo(expectedRequestLine);
        assertThat(message.getEntityBody()).isEqualTo(null);
    }

    @Test
    @DisplayName("Header는 EntityBody에 대한 정보를 준다. (Content-Length)")
    void entityBody() throws IOException {
        EntityBody expected = EntityBody.of("userId=javajigi&password=password&name=JaeSung");
        InputStream in = FileLoader.load("Http_POST.txt");
        RequestMessage message = RequestMessageFactory.of(in);

        assertThat(message.getEntityBody()).isEqualTo(expected);
        assertThat(message.getEntityBody().get("userId")).isEqualTo("javajigi");
    }
}