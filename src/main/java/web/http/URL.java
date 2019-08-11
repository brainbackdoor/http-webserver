package web.http;

import static web.http.Scheme.Type;

public class URL {
    private Scheme scheme;
    private Host host;
    private Port port;
    private UserName userName;
    private Password password;
    private Path path;
    private QueryString queryString;

    public URL(String input) {
        String[] splitedInput = input.split("://");
        this.scheme = Scheme.of(splitedInput[0]);
    }

    public Type getScheme() {
        return this.scheme.getType();
    }

    public int getPortNumber() {
        return this.scheme.getPortNumber();
    }
}
