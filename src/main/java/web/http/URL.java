package web.http;

import web.http.user.Password;
import web.http.user.UserInfo;
import web.http.user.UserName;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;
import static web.http.Scheme.Type;

public class URL {
    public static final String DELIMITER_SCHEME = "://";
    private Scheme scheme;
    private Host host;
    private Port port;
    //    private UserName userName;
//    private Password password;
    private UserInfo userInfo;
    private Path path;
    private List<QueryString> queryStrings;

    public URL(String input) {
        String[] data = input.split(DELIMITER_SCHEME);

        this.scheme = Scheme.of(data[0]);

        if (isRequiredToUserData()) {
            this.userInfo = UserInfo.of(data[1]);
            data = splitUserInfo(data);
        }

        this.host = setHost(data[1]);
        this.port = setPort(data[1]);
        this.path = new Path(splitPath(data[1]));

        if (existQueryString(data)) {
            String[] queryStrings = splitQueryString(data[1]);
            this.queryStrings = Arrays.stream(queryStrings).map(QueryString::new).collect(toList());
        }
    }

    private String[] splitUserInfo(String[] data) {
        return !isAnonymous(data[1]) ? data[1].split("@") : data;
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

    private String splitPath(String s) {
        return s.split("\\?")[0];
    }

    private String[] splitQueryString(String s) {
        return s.split("\\?")[1].split("&");
    }

    private boolean existQueryString(String[] splitedInput) {
        return splitedInput[1].contains("?");
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
        return userInfo.getUserName();
    }

    public Password getPassword() {
        return userInfo.getPassword();
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

    public Path getPath() {
        return path;
    }
}
