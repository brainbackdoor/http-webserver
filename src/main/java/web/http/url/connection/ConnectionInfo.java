package web.http.url.connection;

import web.http.url.Scheme;

public class ConnectionInfo {
    private Host host;
    private Port port;

    private ConnectionInfo(Host host, Port port) {
        this.host = host;
        this.port = port;
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
}
