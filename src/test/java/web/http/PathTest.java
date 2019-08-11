package web.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {

    @Test
    @DisplayName("경로 컴포넌트는 '/'를 기준으로 경로 조각으로 나눌 수 있다.")
    void splitBySlashWhenConstructor() {
        String givenPath = "/seasonal/index.html";
        String expectedFirstPath = "seasonal";
        String expectedSecondPath = "index.html";

        Path path = new Path(givenPath);

        assertThat(path.get(1)).isEqualTo(expectedFirstPath);
        assertThat(path.get(2)).isEqualTo(expectedSecondPath);
    }
}