package web.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryStringTest {

    @Test
    @DisplayName("질의 문자열은 '=' 문자를 기준으로 이름과 값의 쌍을 가진다.")
    void constructor() {
        String given = "item=12731";
        String invalidGiven = "item?12731";

        QueryString queryString = new QueryString(given);

        assertThat(queryString.getKey()).isEqualTo("item");
        assertThat(queryString.getValue()).isEqualTo("12731");

        assertThrows(IllegalArgumentException.class, () -> new QueryString(invalidGiven));
    }
}