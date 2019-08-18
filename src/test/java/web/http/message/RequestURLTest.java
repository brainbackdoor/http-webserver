package web.http.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.http.message.request.RequestURL;
import web.http.url.Path;
import web.http.url.URL;
import web.http.url.connection.Host;
import web.http.url.connection.Port;

import static org.assertj.core.api.Assertions.assertThat;

class RequestURLTest {

    @Test
    @DisplayName("완전한 URL인 경우를 테스트한다.")
    void absoluteUrl() {
        String url = "http://www.joes-hardware.com:80/index.html";
        Path expected = new URL(url).getPath();

        assertThat(new RequestURL(url).getPath()).isEqualTo(expected);
    }

    @Test
    @DisplayName("완전한 URL이 아닐 경우 Host/Port가 자신을 가리키는 것으로 간주한다.")
    void relativeUrl() {
        String url = "/index.html";
        Host expectedHost = Host.of("127.0.0.1");
        Port expectedPort = Port.of(80);

        assertThat(new RequestURL(url).getHost()).isEqualTo(expectedHost);
        assertThat(new RequestURL(url).getPort()).isEqualTo(expectedPort);
    }
}