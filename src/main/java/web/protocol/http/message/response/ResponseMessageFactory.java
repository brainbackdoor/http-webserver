package web.protocol.http.message.response;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import web.protocol.http.message.MessageFactory;
import web.protocol.http.message.common.EntityBody;
import web.protocol.http.message.common.Header;
import web.protocol.http.message.common.Headers;
import web.protocol.http.message.request.RequestMessage;

import java.io.*;

public class ResponseMessageFactory extends MessageFactory {
    private static final String CRLF = "\r\n";
    private static final String CONTENT_LENGTH = "Content-Length: ";

    public static ResponseMessage of(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        ResponseMessage.ResponseMessageBuilder builder = ResponseMessage.builder();
        builder.responseLine(createResponseLine(reader));
        buildEntityBody(builder, reader, buildHeaders(builder, reader));
        return builder.build();
    }

    public static ResponseMessage of(RequestMessage requestMessage) {
        ResponseMessage.ResponseMessageBuilder builder = ResponseMessage.builder();
        builder.responseLine(createResponseLine(requestMessage));
        builder.headers(Headers.of());
        builder.entityBody(EntityBody.of());
        return builder.build();
    }

    private static ResponseLine createResponseLine(RequestMessage requestMessage) {
        return ResponseLine.of(requestMessage.getVersion());
    }

    private static ResponseLine createResponseLine(BufferedReader reader) throws IOException {
        return ResponseLine.of(reader.readLine());
    }

    public static void response(ResponseMessage message, DataOutputStream dos) throws IOException {
        String responseLine = getResponseLine(message);
        String headers = getHeader(message);
        dos.writeBytes(responseLine);
        dos.writeBytes(headers);
        dos.write(message.getEntityBody().getBody(), 0, message.getEntityBody().getBodyLength());
        dos.flush();
    }

    public static String getResponseLine(ResponseMessage message) {
        StringBuilder stringBuilder = new StringBuilder();
        ResponseLine responseLine = message.getResponseLine();
        stringBuilder.append(responseLine.getVersion().getValue())
                .append(StringUtils.SPACE).append(responseLine.getStatusCode().getValue())
                .append(StringUtils.SPACE).append(responseLine.getStatusCode().name());
        return stringBuilder.toString();
    }

    public static String getHeader(ResponseMessage message) {
        StringBuilder stringBuilder = new StringBuilder();
        addHeaders(message.getHeaders(), stringBuilder);
        addContentLength(message, stringBuilder);
        return addEmptyLine(stringBuilder);
    }

    private static void addHeaders(Headers headers, StringBuilder stringBuilder) {
        for (Header header : headers.getHeaders()) {
            stringBuilder.append(header.getType().getValue() + " : " + header.getValue()).append(CRLF);
        }
    }

    private static String addEmptyLine(StringBuilder stringBuilder) {
        return stringBuilder.append(CRLF).toString();
    }

    private static void addContentLength(ResponseMessage message, StringBuilder stringBuilder) {
        stringBuilder.append(CONTENT_LENGTH).append(message.getEntityBody().getBodyLength()).append(CRLF);
    }

}
