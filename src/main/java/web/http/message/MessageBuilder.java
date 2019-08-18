package web.http.message;

public interface MessageBuilder {
    MessageBuilder withStartLine(StartLine line);

    MessageBuilder withHeaders(Headers headers);

    MessageBuilder withEntityBody(EntityBody entityBody);
}
