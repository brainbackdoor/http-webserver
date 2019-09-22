package web.server.resolver;

import web.protocol.http.message.request.RequestMessage;
import web.protocol.http.message.response.ResponseMessage;
import web.util.FileUtils;

public class RequestResolver {
    private Resolver controllerResolver = new ControllerResolver();
    private Resolver resourceResolver = new ResourceResolver();

    public void resolve(RequestMessage request, ResponseMessage response) {
        if(FileUtils.hasComma(request.getPath())) {
            resourceResolver.resolve(request, response);
            return;
        }

        controllerResolver.resolve(request, response);
    }
}
