package web.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SchemeTest {

    @Test
    @DisplayName("스킴은 알파벳으로 시작해야 한다.")
    void startWithAlphabet() {
        String validName = "http";
        String invalidName = "1http";

        assertThat(Scheme.of(validName)).isNotNull();
        assertThrows(IllegalArgumentException.class, () -> Scheme.of(invalidName));
    }

    //TODO: URL의 나머지 부분들과 첫번째 ':' 문자로 구분한다.


    @Test
    @DisplayName("스킴은 대소문자를 가리지 않는다.")
    void convertLowerCase() {
        String lowerCase = "http";
        String uppserCase = "HTTP";

        assertThat(Scheme.of(lowerCase)).isEqualTo(Scheme.of(uppserCase));
    }

    //TODO: 스킴에는 http, https, mailto, ftp, rstp, file, telnet 등이 있다.

    //TODO: http는 80, https는 443 이 기본 포트값이다.

}