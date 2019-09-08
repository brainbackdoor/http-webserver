package web.protocol.http.message;

import web.protocol.http.message.common.EntityBody;
import web.protocol.http.message.common.Headers;

public interface MessageBuilder {
    MessageBuilder withStartLine(StartLine line);

    MessageBuilder withHeaders(Headers headers);

    MessageBuilder withEntityBody(EntityBody entityBody);
}
