package web.server.resolver;

import web.protocol.http.message.request.RequestMessage;
import web.protocol.http.message.response.ResponseMessage;

public interface Resolver {
    void resolve(RequestMessage requestMessage, ResponseMessage responseMessage);
}
