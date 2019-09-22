package web.protocol.http.message.request;

import web.protocol.http.message.MessageFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestMessageFactory extends MessageFactory {

    public static RequestMessage of(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        RequestMessage.RequestMessageBuilder builder = RequestMessage.builder();
        builder.requestLine(createRequestLine(reader));
        buildEntityBody(builder, reader, buildHeaders(builder, reader));
        return builder.build();
    }

    private static RequestLine createRequestLine(BufferedReader reader) throws IOException {
        return RequestLine.of(reader.readLine());
    }
}
