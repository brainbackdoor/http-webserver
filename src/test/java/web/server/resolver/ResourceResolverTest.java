package web.server.resolver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.protocol.http.message.request.RequestMessage;
import web.protocol.http.message.request.RequestMessageFactory;
import web.protocol.http.message.response.ResponseMessage;
import web.protocol.http.message.response.ResponseMessageFactory;
import web.protocol.http.support.file.FileLoader;

import java.io.IOException;
import java.io.InputStream;

class ResourceResolverTest {
    private static final Logger log = LoggerFactory.getLogger(ResourceResolverTest.class);
    private ResourceResolver resolver = new ResourceResolver();

    @Test
    @DisplayName("파일을 잘 읽어들이는지 테스트")
    void resolve() throws IOException {
        InputStream in = FileLoader.load("Http_GET_index.txt");
        RequestMessage request = RequestMessageFactory.of(in);
        InputStream inRes = FileLoader.load("Http_GET_index_resp.txt");
        ResponseMessage response = ResponseMessageFactory.of(inRes);

        resolver.resolve(request, response);
        log.debug("{}", new String(response.getEntityBody().getBody()));
    }
}