package web.http;

import web.http.connection.ConnectionInfo;
import web.http.connection.Host;
import web.http.connection.Port;
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
    private UserInfo userInfo;
    private ConnectionInfo connectionInfo;
    private Path path;
    private List<QueryString> queryStrings;

    public URL(String input) {
        String[] data = input.split(DELIMITER_SCHEME);

        this.scheme = Scheme.of(data[0]);

        if (isRequiredToUserData()) {
            this.userInfo = UserInfo.of(data[1]);
            data = splitUserInfo(data);
        }


        this.connectionInfo = ConnectionInfo.of(scheme, data[1]);
        this.path = new Path(splitPath(data[1]));

        if (existQueryString(data)) {
            String[] queryStrings = splitQueryString(data[1]);
            this.queryStrings = Arrays.stream(queryStrings).map(QueryString::new).collect(toList());
        }
    }

    private String[] splitUserInfo(String[] data) {
        return !isAnonymous(data[1]) ? data[1].split("@") : data;
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
        return connectionInfo.getHost();
    }

    public Port getPort() {
        return connectionInfo.getPort();
    }

    public Path getPath() {
        return path;
    }
}
