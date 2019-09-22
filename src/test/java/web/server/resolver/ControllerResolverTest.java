package web.server.resolver;

import org.junit.jupiter.api.Test;
import web.protocol.http.message.request.RequestMessage;
import web.protocol.http.message.request.RequestMessageFactory;
import web.protocol.http.message.response.ResponseMessage;
import web.protocol.http.message.response.ResponseMessageFactory;
import web.protocol.http.support.file.FileLoader;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.http.message.response.StatusCode.OK;

class ControllerResolverTest {
    private ControllerResolver resolver = new ControllerResolver();

    @Test
    void index() throws IOException {
        // given
        InputStream in = FileLoader.load("Http_GET_index.txt");
        RequestMessage request = RequestMessageFactory.of(in);
        InputStream inRes = FileLoader.load("Http_GET_index_resp.txt");
        ResponseMessage response = ResponseMessageFactory.of(inRes);

        // when
        resolver.resolve(request, response);

        // then
        assertThat(response.getStatusCode()).isEqualByComparingTo(OK);
    }

}