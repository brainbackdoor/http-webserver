package web.http.url;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.http.url.PathCarving;
import web.http.url.URL;
import web.http.url.connection.Host;
import web.http.url.connection.Port;
import web.http.url.user.Password;
import web.http.url.user.UserName;

import static org.assertj.core.api.Assertions.assertThat;
import static web.http.url.Scheme.Type;

class URLTest {

    @Test
    @DisplayName("Scheme은 URL의 나머지 부분들과 첫번째 ':' 문자로 구분한다.")
    void splitScheme() {
        String given = "http://www.joes-hardware.com:80/index.html";
        URL url = new URL(given);

        assertThat(url.getScheme()).isEqualTo(Type.HTTP);
    }

    @Test
    @DisplayName("Scheme이 ftp, rtsp, telnet일 경우 사용자이름과 비밀번호를 요구한다.")
    void demandUserDataAccordingToScheme() {
        String expectedTrue = "ftp://www.joes-hardware.com:80/index.html";
        String expectedFalse = "http://www.joes-hardware.com:80/index.html";

        URL actualTrueUrl = new URL(expectedTrue);
        URL actualFalseUrl = new URL(expectedFalse);

        assertThat(actualTrueUrl.isRequiredToUserData()).isTrue();
        assertThat(actualFalseUrl.isRequiredToUserData()).isFalse();
    }

    @Test
    @DisplayName("UserName과 Password는 ':'로 구분하고, '@'문자로 URL로부터 UserName과 Password 컴포넌트를 분리한다.")
    void splitUserData() {
        String given = "ftp://joe:joespasswd@www.joes-hardware.com:80/index.html";
        URL url = new URL(given);

        assertThat(url.getUserName()).isEqualTo(new UserName("joe"));
        assertThat(url.getPassword()).isEqualTo(new Password("joespasswd"));
    }

    @Test
    @DisplayName("UserName과 Password 기본값을 확인한다.")
    void splitUserDataDefault() {
        String given = "ftp://www.joes-hardware.com:80/index.html";
        URL url = new URL(given);

        assertThat(url.getUserName()).isEqualTo(new UserName());
        assertThat(url.getPassword()).isEqualTo(new Password());
    }

    @Test
    @DisplayName("QueryString은 '?' 문자로 URL과 구분된다.")
    void setQueryString() {
        String given = "http://www.joes-hardware.com/inventory-check.cgi?item=12731";
        URL url = new URL(given);

        assertThat(url.getQueryStrings().get(0).getKey()).isEqualTo("item");
        assertThat(url.getQueryStrings().get(0).getValue()).isEqualTo("12731");
    }

    @Test
    @DisplayName("Host와 Port 정보를 추출한다.")
    void setHostAndPort() {
        String givenTrue = "http://www.joes-hardware.com:80/index.html";
        URL url = new URL(givenTrue);

        assertThat(url.getHost()).isEqualTo(Host.of("www.joes-hardware.com"));
        assertThat(url.getPort()).isEqualTo(Port.of(80));
    }

    @Test
    @DisplayName("Path 정보를 추출한다.")
    void setPath() {
        String givenTrue = "http://www.joes-hardware.com:80/seasonal/index.html";
        URL url = new URL(givenTrue);

        assertThat(url.getPath().get(1)).isEqualTo(new PathCarving("seasonal"));
        assertThat(url.getPath().get(2)).isEqualTo(new PathCarving("index.html"));
    }
}