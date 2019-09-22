package web.server.controller;

import web.protocol.http.message.request.RequestMessage;
import web.protocol.http.message.response.ResponseMessage;

public interface Controller {
    void action(RequestMessage request, ResponseMessage response);
}
