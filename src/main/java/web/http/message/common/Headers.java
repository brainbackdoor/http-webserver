package web.http.message.common;

import java.util.List;

import static java.util.stream.Collectors.*;

public class Headers {
    private List<Header> headers;

    public Headers(List<Header> headers) {
        this.headers = headers;
    }

    public static Headers of(List<String> inputs) {
        return new Headers(inputs.stream().map(Header::of).collect(toList()));
    }

    public int getContentLength() {
        return Integer.parseInt(headers.stream().filter(Header::isContentLength).findFirst().get().getValue());
    }

    public boolean hasContentLength() {
        return headers.stream().filter(Header::isContentLength).findAny().isPresent();
    }

    @Override
    public String toString() {
        return "Headers{" +
                "headers=" + headers +
                '}';
    }
}
