package web.http.message.response;

import web.http.message.MessageFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResponseMessageFactory extends MessageFactory {
    public static ResponseMessage of(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        ResponseMessage.ResponseMessageBuilder builder = ResponseMessage.builder();
        builder.withStartLine(createResponseLine(reader));
        buildEntityBody(builder, reader, buildHeaders(builder, reader));
        return builder.build();
    }

    private static ResponseLine createResponseLine(BufferedReader reader) throws IOException {
        return ResponseLine.of(reader.readLine());
    }
}
