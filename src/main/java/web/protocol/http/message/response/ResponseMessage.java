package web.protocol.http.message.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import web.protocol.http.message.common.EntityBody;
import web.protocol.http.message.common.Headers;

import java.io.IOException;

import static web.protocol.http.message.common.Version.HTTP_1_1;
import static web.protocol.http.message.response.StatusCode.*;

@Builder
@Getter
@Setter
public class ResponseMessage {
    private ResponseLine responseLine;
    private Headers headers;
    private EntityBody entityBody;

    public ResponseMessage(ResponseLine responseLine, Headers headers, EntityBody entityBody) {
        this.responseLine = responseLine;
        this.headers = headers;
        this.entityBody = entityBody;
    }

    public void redirect(String url) {
        this.responseLine = new ResponseLine(HTTP_1_1, FOUND);
        this.headers.addLocation(url);
    }

    public StatusCode getStatusCode() {
        return responseLine.getStatusCode();
    }

    public void ok(byte[] body) {
        responseLine.setStatusCode(OK);
        entityBody.setBody(body);
    }

    public void notFound(IOException exception) {
        responseLine.setStatusCode(NOT_FOUND);
        entityBody.setBody(exception.getMessage().getBytes());
    }
}

