package web.http.url;

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

        Path path = Path.of(givenPath);

        assertThat(path.get(1)).isEqualTo(new PathCarving(expectedFirstPath));
        assertThat(path.get(2)).isEqualTo(new PathCarving(expectedSecondPath));
    }



    @Test
    @DisplayName("각 경로조각은 별도로 파라미터를 가질 수 있다.")
    void setParameterSeveral() {
        String givenPath = "/hammers;type=d/index.html;graphics=true";

        Path path = Path.of(givenPath);

        assertThat(path.get(1).getParameter(0).getKey()).isEqualTo("type");
        assertThat(path.get(1).getParameter(0).getValue()).isEqualTo("d");

        assertThat(path.get(2).getParameter(0).getKey()).isEqualTo("graphics");
        assertThat(path.get(2).getParameter(0).getValue()).isEqualTo("true");
    }
}