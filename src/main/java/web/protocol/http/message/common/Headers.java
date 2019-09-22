package web.protocol.http.message.common;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

public class Headers {
    private List<Header> headers;

    public Headers() {
        headers = new ArrayList<>();
    }

    public Headers(List<Header> headers) {
        this.headers = headers;
    }

    public static Headers of() {
        return new Headers();
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

    public void addLocation(String url) {
        headers.add(Header.of("Location: " + url));
    }

    public Header getHeader(String name) {
        return headers.stream().filter(v -> v.isSameType(name)).findFirst().get();
    }

    public List<Header> getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return "Headers{" +
                "headers=" + headers +
                '}';
    }
}
