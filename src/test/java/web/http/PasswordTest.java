package web.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordTest {

    @Test
    @DisplayName("기본 비밀번호는 'chrome@example.com'")
    void constructor() {
        String expected = "chrome@example.com";

        Password expectedPassword = new Password(expected);
        Password actualDefaultPassword = new Password();

        assertThat(actualDefaultPassword).isEqualTo(expectedPassword);
    }

}