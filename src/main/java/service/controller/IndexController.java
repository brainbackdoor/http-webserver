package service.controller;

import org.assertj.core.util.Lists;
import web.protocol.http.message.common.EntityBody;
import web.protocol.http.message.common.Headers;
import web.protocol.http.message.common.Version;
import web.protocol.http.message.request.RequestMessage;
import web.protocol.http.message.response.ResponseLine;
import web.protocol.http.message.response.ResponseMessage;
import web.protocol.http.message.response.StatusCode;
import web.server.controller.AbstractController;

public class IndexController extends AbstractController {
    public static final String URL = "/index";

    @Override
    public void doGet(RequestMessage request, ResponseMessage response) {

        response.setResponseLine(ResponseLine.builder().statusCode(StatusCode.OK).version(Version.HTTP_1_1).build());
        response.setHeaders(Headers.of(Lists.list("Location: /templates/index.html")));
        response.setEntityBody(EntityBody.of("Please go to our partner site,\nwww.gentle-grooming.com"));
    }
}
