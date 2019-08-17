package web.http.message;

/**
 * 요청 메시지는 웹 서버에 어떤 동작을 요구한다.
 *
 * Created by brainbackdoor on 2019-08-18.
 */

public class RequestMessage {
    private final RequestLine requestLine;
    private final Header header;
    private final EntityBody entityBody;

    public RequestMessage(RequestLine requestLine, Header header, EntityBody entityBody) {
        this.requestLine = requestLine;
        this.header = header;
        this.entityBody = entityBody;
    }
}
