package web.http.message;

import web.http.message.common.EntityBody;
import web.http.message.common.Headers;

public interface MessageBuilder {
    MessageBuilder withStartLine(StartLine line);

    MessageBuilder withHeaders(Headers headers);

    MessageBuilder withEntityBody(EntityBody entityBody);
}
