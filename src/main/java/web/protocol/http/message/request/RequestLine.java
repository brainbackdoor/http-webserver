package web.protocol.http.message.request;

import lombok.Getter;
import web.protocol.http.message.common.Version;

import java.util.Objects;

@Getter
public class RequestLine {
    public static final String DELIMITER_REQUEST_LINE = " ";
    private final Method method;
    private final RequestURL requestURL;
    private final Version version;

    public RequestLine(Method method, RequestURL requestURL, Version version) {
        this.method = method;
        this.requestURL = requestURL;
        this.version = version;
    }

    public static RequestLine of(String input) {
        String[] splitValue = input.split(DELIMITER_REQUEST_LINE);
        Method method = Method.of(splitValue[0]);
        RequestURL requestURL = new RequestURL(splitValue[1]);
        Version version = Version.of(splitValue[2]);
        return new RequestLine(method, requestURL, version);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return method == that.method &&
                Objects.equals(requestURL, that.requestURL) &&
                version == that.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, requestURL, version);
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", requestURL=" + requestURL +
                ", version=" + version +
                '}';
    }
}
