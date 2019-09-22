package web.protocol.http.message.request;

import lombok.Builder;
import lombok.Getter;
import web.protocol.http.message.common.EntityBody;
import web.protocol.http.message.common.Headers;
import web.protocol.http.message.common.Version;

import static web.protocol.http.message.request.Method.GET;
import static web.protocol.http.message.request.Method.POST;

/**
 * 요청 메시지는 웹 서버에 어떤 동작을 요구한다.
 * <p>
 * Created by brainbackdoor on 2019-08-18.
 */

@Builder
@Getter
public class RequestMessage {
    private final RequestLine requestLine;
    private final Headers headers;
    private final EntityBody entityBody;

    public RequestMessage(RequestLine requestLine, Headers headers, EntityBody entityBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.entityBody = entityBody;
    }

    public Version getVersion() {
        return requestLine.getVersion();
    }

    public Method getMethod(){
        return requestLine.getMethod();
    }

    public boolean isPost() {
        return getMethod().equalsTo(POST.name());
    }

    public boolean isGet() {
        return getMethod().equalsTo(GET.name());
    }

    public String getPath() {
        return requestLine.getRequestURL().getFullPath();
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

