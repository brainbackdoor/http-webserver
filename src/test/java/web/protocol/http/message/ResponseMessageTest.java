package web.protocol.http.message;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.http.message.common.EntityBody;
import web.protocol.http.message.common.Headers;
import web.protocol.http.message.response.ResponseLine;
import web.protocol.http.message.response.ResponseMessage;
import web.protocol.http.message.response.ResponseMessageFactory;
import web.protocol.http.message.response.StatusCode;
import web.protocol.http.support.file.FileLoader;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.http.message.common.EntityBody.DEFAULT_KEY;
import static web.protocol.http.message.common.Version.HTTP_1_1;

class ResponseMessageTest {

    @Test
    @DisplayName("ResponseMessage는 ResponseLine, Header, EntityBody로 구성된다.")
    void constructor() {
        ResponseLine responseLine = new ResponseLine(HTTP_1_1, StatusCode.MOVED_PERMANENTLY);
        Headers headers = Headers.of(Lists.list("Location: http://www.gentle-grooming.com"));
        EntityBody entityBody = EntityBody.of("Please go to our partner site,\nwww.gentle-grooming.com");

        assertThat(new ResponseMessage(responseLine, headers, entityBody)).isNotNull();
    }

    @Test
    @DisplayName("ResponseLine과 Header는 줄바꿈 문자열로 끝난다.")
    void csrf() throws IOException {
        ResponseLine expectedResponseLine = ResponseLine.of("HTTP/1.1 301 OK");
        EntityBody expectedEntityBody = EntityBody.of("Please go to our partner site,\nwww.gentle-grooming.com\n\n");

        InputStream in = FileLoader.load("Http_GET_index_resp.txt");
        ResponseMessage message = ResponseMessageFactory.of(in);

        assertThat(message.getResponseLine()).isEqualTo(expectedResponseLine);
        assertThat(message.getEntityBody()).isEqualTo(expectedEntityBody);
    }

    @Test
    @DisplayName("Header는 EntityBody에 대한 정보를 준다.")
    void entityBody() throws IOException {
        String rawParameters = "Please go to our partner site,\nwww.gentle-grooming.com\n\n";
        EntityBody expected = EntityBody.of(rawParameters);
        InputStream in = FileLoader.load("Http_GET_index_resp.txt");
        ResponseMessage message = ResponseMessageFactory.of(in);

        assertThat(message.getEntityBody()).isEqualTo(expected);
        assertThat(message.getEntityBody().get(DEFAULT_KEY)).isEqualTo(rawParameters);
    }

}