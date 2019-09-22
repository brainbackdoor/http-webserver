package service.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.http.message.request.RequestMessage;
import web.protocol.http.message.request.RequestMessageFactory;
import web.protocol.http.message.response.ResponseMessage;
import web.protocol.http.message.response.ResponseMessageFactory;
import web.protocol.http.support.file.FileLoader;
import web.server.controller.Controller;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class IndexControllerTest {

    @Test
    @DisplayName("Index page 요청")
    void index() throws IOException {
        // given
        InputStream in = FileLoader.load("Http_GET_index.txt");
        RequestMessage request = RequestMessageFactory.of(in);
        InputStream inRes = FileLoader.load("Http_GET_index_resp.txt");
        ResponseMessage response = ResponseMessageFactory.of(inRes);
        Controller indexController = new IndexController();

        // when
        indexController.action(request, response);

        // then
        assertThat(response.getStatusCode());
        assertThat(response.getHeaders().getHeader("Location").getValue()).isEqualTo("/static/index.html");

    }

}