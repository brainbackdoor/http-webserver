package web.server.controller.exception;

import web.protocol.http.message.request.Method;

public class NotSupportedHttpMethodException extends RuntimeException {

    public NotSupportedHttpMethodException(Method method) {
        super("Not Supported HttpMethod :" + method);
    }

}
