package web.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static web.http.Scheme.Type;

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

    //TODO: QueryString은 '?' 문자로 URL과 구분된다.
}