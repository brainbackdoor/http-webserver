package web.protocol.http.message.request;

import web.protocol.http.url.Path;
import web.protocol.http.url.URL;
import web.protocol.http.url.connection.Host;
import web.protocol.http.url.connection.Port;

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

    public String getFullPath() {
        return url.getFullPath();
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
