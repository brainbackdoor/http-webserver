package web.http.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static web.http.message.Version.HTTP_1_1;

class RequestMessageTest {

    @Test
    @DisplayName("RequestMessage는 RequestLine, Header, EntityBody로 구성된다.")
    void constructor() {
        RequestLine line = new RequestLine(Method.GET, new RequestURL("/test/hello"), HTTP_1_1);
        Header header = new Header();
        EntityBody body = new EntityBody();

        assertThat(new RequestMessage(line, header, body)).isNotNull();
    }

    //TODO: RequestLine과 Header는 줄바꿈 문자열로 끝난다.
    //TODO: Header는 EntityBody에 대한 정보를 준다.
    //TODO: Header나 EntityBody가 없더라도 HTTP Header 집합은 항상 빈 줄로 끝나야 한다.
}