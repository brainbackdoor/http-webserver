package web.http;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;
import static web.http.Scheme.Type;

public class URL {
    private Scheme scheme;
    private Host host;
    private Port port;
    private UserName userName;
    private Password password;
    private Path path;
    private List<QueryString> queryStrings;

    public URL(String input) {
        String[] splitedInput = input.split("://");

        this.scheme = Scheme.of(splitedInput[0]);

        if (isRequiredToUserData()) {
            splitedInput = setUserData(splitedInput);
        }

        this.host = setHost(splitedInput[1]);
        this.port = setPort(splitedInput[1]);

        if (existQueryString(splitedInput)) {
            String[] queryStrings = splitQueryString(splitedInput[1]);
            this.queryStrings = Arrays.stream(queryStrings).map(QueryString::new).collect(toList());
        }
    }

    private Port setPort(String s) {
        return Type.hasDefaultPortNumber(getScheme())
                ? retrievePortNumber()
                : new Port(extractPortNumber(s));
    }

    private Port retrievePortNumber() {
        return new Port(getPortNumber());
    }

    private int extractPortNumber(String s) {
        return Integer.parseInt(s.split("/")[0].split(":")[1]);
    }

    private Host setHost(String s) {
        return Host.of(s.split("/")[0].split(":")[0]);
    }

    private String[] splitQueryString(String s) {
        return s.split("\\?")[1].split("&");
    }

    private boolean existQueryString(String[] splitedInput) {
        return splitedInput[1].contains("?");
    }

    private String[] setUserData(String[] detachedScheme) {
        if (!isAnonymous(detachedScheme[1])) {
            detachedScheme = detachedScheme[1].split("@");
            String[] userData = detachedScheme[0].split(":");
            this.userName = new UserName(userData[0]);
            this.password = new Password(userData[1]);
            return detachedScheme;
        }
        this.userName = new UserName();
        this.password = new Password();
        return detachedScheme;
    }

    private boolean isAnonymous(String detachedScheme) {
        return !detachedScheme.contains("@");
    }

    public boolean isRequiredToUserData() {
        return Type.isRequiredToUserData(getScheme());
    }

    public Type getScheme() {
        return this.scheme.getType();
    }

    public int getPortNumber() {
        return this.scheme.getPortNumber();
    }

    public UserName getUserName() {
        return userName;
    }

    public Password getPassword() {
        return password;
    }

    public List<QueryString> getQueryStrings() {
        return queryStrings;
    }

    public Host getHost() {
        return host;
    }

    public Port getPort() {
        return port;
    }
}
