package web.http.message;

/**
 * 요청 메시지는 웹 서버에 어떤 동작을 요구한다.
 * <p>
 * Created by brainbackdoor on 2019-08-18.
 */

public class RequestMessage {
    private final StartLine requestLine;
    private final Headers headers;
    private final EntityBody entityBody;

    public RequestMessage(StartLine requestLine, Headers headers, EntityBody entityBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.entityBody = entityBody;
    }

    public StartLine getRequestLine() {
        return requestLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public EntityBody getEntityBody() {
        return entityBody;
    }

    public static RequestMessageBuilder builder() {
        return new RequestMessageBuilder();
    }

    public static final class RequestMessageBuilder implements MessageBuilder {
        private StartLine requestLine;
        private Headers headers;
        private EntityBody entityBody;

        private RequestMessageBuilder() {
        }

        public MessageBuilder withStartLine(StartLine line) {
            this.requestLine = line;
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

        public RequestMessage build() {
            return new RequestMessage(requestLine, headers, entityBody);
        }
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                ", entityBody=" + entityBody +
                '}';
    }
}
