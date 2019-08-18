package web.http.message;

import web.http.url.Path;
import web.http.url.URL;
import web.http.url.connection.Host;
import web.http.url.connection.Port;

import java.util.Objects;

public class RequestURL {
    private final URL url;

    public RequestURL(String input) {
        this.url = new URL(input);
    }

    public Host getHost() {
        return url.getHost();
    }

    public Port getPort() {
        return url.getPort();
    }

    public Path getPath() {
        return url.getPath();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestURL that = (RequestURL) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "RequestURL{" +
                "url=" + url +
                '}';
    }
}
