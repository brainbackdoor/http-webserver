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

    //TODO: Scheme이 ftp, rtsp, telnet일 경우 사용자이름과 비밀번호를 요구한다.
    //TODO: UserName과 Password는 ':'로 구분하고, '@'문자로 URL로부터 UserName과 Password 컴포넌트를 분리한다.
    //TODO: QueryString은 '?' 문자로 URL과 구분된다.
}