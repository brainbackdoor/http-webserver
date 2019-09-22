package web.server.controller;

import web.protocol.http.message.request.RequestMessage;
import web.protocol.http.message.response.ResponseMessage;
import web.server.controller.exception.NotSupportedHttpMethodException;

public abstract class AbstractController implements Controller {
    @Override
    public final void action(RequestMessage request, ResponseMessage response) {
        if(request.isPost()) {
            doPost(request, response);
            return;
        }

        if(request.isGet()) {
            doGet(request, response);
            return;
        }

    }

    public void doPost(RequestMessage request, ResponseMessage response) {
        throw new NotSupportedHttpMethodException(request.getMethod());
    }

    public void doGet(RequestMessage request, ResponseMessage response) {
        throw new NotSupportedHttpMethodException(request.getMethod());
    }
}
