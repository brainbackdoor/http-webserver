package web.http.url;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import web.http.url.Scheme;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.http.url.Scheme.Type;

class SchemeTest {

    @Test
    @DisplayName("스킴은 알파벳으로 시작해야 한다.")
    void startWithAlphabet() {
        String validName = "http";
        String invalidName = "1http";

        assertThat(Scheme.of(validName)).isNotNull();
        assertThrows(IllegalArgumentException.class, () -> Scheme.of(invalidName));
    }

    @Test
    @DisplayName("스킴은 대소문자를 가리지 않는다.")
    void convertLowerCase() {
        String lowerCase = "http";
        String uppserCase = "HTTP";

        assertThat(Scheme.of(lowerCase)).isEqualTo(Scheme.of(uppserCase));
    }

    @ParameterizedTest
    @EnumSource(Type.class)
    @DisplayName("스킴에는 http, https, mailto, ftp, rstp, file, telnet 등이 있다.")
    void schemeType(Type type) {
        Scheme scheme = Scheme.of(type.name());

        assertThat(scheme).isNotNull();
    }

    @ParameterizedTest
    @CsvSource({"http,80", "https,443"})
    @DisplayName("http는 80, https는 443 이 기본 포트값이다.")
    void checkDefaultPortAboutSpecificScheme(String name, int port) {
        Scheme scheme = Scheme.of(name);

        assertThat(scheme.getPortNumber()).isEqualTo(port);
    }

}