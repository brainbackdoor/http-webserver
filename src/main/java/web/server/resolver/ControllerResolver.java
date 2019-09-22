package web.server.resolver;

import service.controller.IndexController;
import web.protocol.http.message.request.RequestMessage;
import web.protocol.http.message.response.ResponseMessage;
import web.server.controller.Controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerResolver implements Resolver {
    private static final Map<String, Controller> CONTROLLERS  = new HashMap<>();

    static {
        CONTROLLERS.put(IndexController.URL, new IndexController());
    }

    @Override
    public void resolve(RequestMessage requestMessage, ResponseMessage responseMessage) {
        Controller controller = CONTROLLERS.get(requestMessage.getPath());
        controller.action(requestMessage, responseMessage);
    }
}
