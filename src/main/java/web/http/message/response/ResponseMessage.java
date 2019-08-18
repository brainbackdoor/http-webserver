package web.http.message.response;

import web.http.message.common.EntityBody;
import web.http.message.common.Headers;
import web.http.message.MessageBuilder;
import web.http.message.StartLine;

public class ResponseMessage {
    private StartLine responseLine;
    private Headers headers;
    private EntityBody entityBody;

    public ResponseMessage(StartLine responseLine, Headers headers, EntityBody entityBody) {
        this.responseLine = responseLine;
        this.headers = headers;
        this.entityBody = entityBody;
    }

    public StartLine getResponseLine() {
        return responseLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public EntityBody getEntityBody() {
        return entityBody;
    }

    public static ResponseMessageBuilder builder() {
        return new ResponseMessageBuilder();
    }

    public static final class ResponseMessageBuilder implements MessageBuilder {
        private StartLine responseLine;
        private Headers headers;
        private EntityBody entityBody;

        private ResponseMessageBuilder() {
        }

        public MessageBuilder withStartLine(StartLine responseLine) {
            this.responseLine = responseLine;
            return this;
        }

        public MessageBuilder withHeaders(Headers headers) {
            this.headers = headers;
            return this;
        }

        public MessageBuilder withEntityBody(EntityBody entityBody) {
            this.entityBody = entityBody;
            return this;
        }

        public ResponseMessage build() {
            return new ResponseMessage(responseLine, headers, entityBody);
        }
    }
}
