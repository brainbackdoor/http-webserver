package web.http.url;

import web.http.url.connection.ConnectionInfo;
import web.http.url.connection.Host;
import web.http.url.connection.Port;
import web.http.url.user.Password;
import web.http.url.user.UserInfo;
import web.http.url.user.UserName;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.*;
import static web.http.url.Scheme.Type;

public class URL {
    public static final String DELIMITER_SCHEME = "://";
    public static final String DELIMITER_USER_INFO = "@";
    public static final String DELIMITER_QUERY_STRING = "&";
    public static final String DELIMITER_QUERY_STRINGS = "?";
    private Scheme scheme;
    private UserInfo userInfo;
    private ConnectionInfo connectionInfo;
    private Path path;
    private List<QueryString> queryStrings;

    public URL(String input) {
        if(isAbsolutePath(input)) {
            String[] data = input.split(DELIMITER_SCHEME);
            this.scheme = Scheme.of(data[0]);

            data = extractUserInfo(data);

            this.connectionInfo = ConnectionInfo.of(scheme, data[1]);
            this.path = Path.of(splitPath(data[1]));

            extractQueryStrings(data);
        } else {
            connectionInfo = ConnectionInfo.of();
            path = Path.of(input);
        }

    }

    private boolean isAbsolutePath(String data) {
        return data.contains(DELIMITER_SCHEME);
    }

    private String[] extractUserInfo(String[] data) {
        if (isRequiredToUserData()) {
            this.userInfo = UserInfo.of(data[1]);
            data = splitUserInfo(data);
        }
        return data;
    }

    private void extractQueryStrings(String[] data) {
        if (existQueryString(data)) {
            String[] queryStrings = splitQueryString(data[1]);
            this.queryStrings =
                    Arrays.stream(queryStrings)
                            .map(QueryString::new)
                            .collect(toList());
        }
    }

    private String[] splitUserInfo(String[] data) {
        return !isAnonymous(data[1]) ? data[1].split(DELIMITER_USER_INFO) : data;
    }

    private String splitPath(String s) {
        return s.split("\\?")[0];
    }

    private String[] splitQueryString(String s) {
        return s.split("\\?")[1].split(DELIMITER_QUERY_STRING);
    }

    private boolean existQueryString(String[] splitedInput) {
        return splitedInput[1].contains(DELIMITER_QUERY_STRINGS);
    }

    private boolean isAnonymous(String detachedScheme) {
        return !detachedScheme.contains(DELIMITER_USER_INFO);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URL url = (URL) o;
        return Objects.equals(scheme, url.scheme) &&
                Objects.equals(userInfo, url.userInfo) &&
                Objects.equals(connectionInfo, url.connectionInfo) &&
                Objects.equals(path, url.path) &&
                Objects.equals(queryStrings, url.queryStrings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheme, userInfo, connectionInfo, path, queryStrings);
    }

    @Override
    public String toString() {
        return "URL{" +
                "scheme=" + scheme +
                ", userInfo=" + userInfo +
                ", connectionInfo=" + connectionInfo +
                ", path=" + path +
                ", queryStrings=" + queryStrings +
                '}';
    }
}
