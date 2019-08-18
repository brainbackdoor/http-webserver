package web.http.message;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageFactory {
    protected static Headers createdHeaders(BufferedReader reader) throws IOException {
        return Headers.of(extractHeaders(reader));
    }

    protected static EntityBody createEntityBody(BufferedReader reader, int contentLength) throws IOException {
        return EntityBody.of(readData(reader, contentLength));
    }

    protected static Headers buildHeaders(MessageBuilder builder, BufferedReader reader) throws IOException {
        Headers headers = createdHeaders(reader);
        builder.withHeaders(headers);
        return headers;
    }

    protected static void buildEntityBody(MessageBuilder builder, BufferedReader reader, Headers headers) throws IOException {
        if (headers.hasContentLength()) {
            int contentLength = headers.getContentLength();
            builder.withEntityBody(createEntityBody(reader, contentLength));
        }
    }

    private static List<String> extractHeaders(BufferedReader reader) throws IOException {
        List<String> headers = new ArrayList<>();
        String line = reader.readLine();
        while (!StringUtils.EMPTY.equals(line)) {
            headers.add(line);
            line = reader.readLine();
        }
        return headers;
    }

    private static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }
}
