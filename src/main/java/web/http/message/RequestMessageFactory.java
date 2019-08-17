package web.http.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static java.util.stream.Collectors.*;

public class RequestMessageFactory {

    public static RequestMessage of(InputStream in) throws IOException {
        RequestMessage.RequestMessageBuilder builder = RequestMessage.builder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        builder.withRequestLine(createRequestLine(reader));

        Headers headers = createdRequestHeaders(reader);
        builder.withHeaders(headers);

        if(headers.hasContentLength()) {
            int contentLength = headers.getContentLength();
            builder.withEntityBody(createRequestBody(reader, contentLength));
        }
        return builder.build();
    }

    private static RequestLine createRequestLine(BufferedReader reader) throws IOException {
        return RequestLine.of(reader.readLine());
    }

    private static Headers createdRequestHeaders(BufferedReader reader){
        return Headers.of(extractHeaders(reader));
    }

    private static List<String> extractHeaders(BufferedReader reader) {
        return reader.lines().collect(toList()).subList(0, reader.lines().toArray().length);
    }

    private static EntityBody createRequestBody(BufferedReader reader, int contentLength) throws IOException {
        return EntityBody.of(readData(reader, contentLength));
    }

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }
}
