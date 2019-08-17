package web.http.url.connection;

import web.http.url.Scheme;

import java.util.Objects;

import static web.http.url.Scheme.Type.HTTP;

public class ConnectionInfo {
    public static final String DEFAULT_HOST = "127.0.0.1";
    private Host host;
    private Port port;

    private ConnectionInfo(Host host, Port port) {
        this.host = host;
        this.port = port;
    }

    public static ConnectionInfo of() {
        return new ConnectionInfo(setHost(DEFAULT_HOST), setPort(Scheme.of(), HTTP.name()));
    }

    public static ConnectionInfo of(Scheme scheme, String data) {
        return new ConnectionInfo(setHost(data), setPort(scheme, data));
    }

    private static Host setHost(String s) {
        return Host.of(s.split("/")[0].split(":")[0]);
    }

    private static Port setPort(Scheme scheme, String s) {
        return Scheme.Type.hasDefaultPortNumber(scheme.getType())
                ? retrievePortNumber(scheme)
                : Port.of(extractPortNumber(s));
    }

    private static Port retrievePortNumber(Scheme scheme) {
        return Port.of(getPortNumber(scheme));
    }

    private static int extractPortNumber(String s) {
        return Integer.parseInt(s.split("/")[0].split(":")[1]);
    }

    public static int getPortNumber(Scheme scheme) {
        return scheme.getPortNumber();
    }

    public Host getHost() {
        return host;
    }

    public Port getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionInfo that = (ConnectionInfo) o;
        return Objects.equals(host, that.host) &&
                Objects.equals(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }
}
