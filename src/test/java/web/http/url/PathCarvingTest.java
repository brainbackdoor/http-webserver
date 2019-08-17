package web.http.url;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.http.url.PathCarving;

import static org.assertj.core.api.Assertions.assertThat;

class PathCarvingTest {

    @Test
    @DisplayName("경로조각은 별도로 파라미터를 가질 수 있다.")
    void setParameter() {
        String givenPath = "/pub;type=d";

        PathCarving pathCarving = new PathCarving(givenPath);

        assertThat(pathCarving.getParameter(0).getKey()).isEqualTo("type");
        assertThat(pathCarving.getParameter(0).getValue()).isEqualTo("d");
    }
}