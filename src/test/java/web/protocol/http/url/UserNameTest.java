package web.protocol.http.url;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.http.url.user.UserName;

import static org.assertj.core.api.Assertions.assertThat;

class UserNameTest {

    @Test
    @DisplayName("기본 사용자 이름은 'anonymous'")
    void constructor() {
        String expected = "anonymous";

        UserName expectedName = new UserName(expected);
        UserName actualDefaultName = new UserName();

        assertThat(actualDefaultName).isEqualTo(expectedName);
    }
}