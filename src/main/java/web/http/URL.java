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
        if(isRequiredToUserData()){
            setUserData(splitedInput);
        }
        if(existQueryString(splitedInput)) {
            String[] queryStrings = splitedInput[1].split("\\?")[1].split("&");
            this.queryStrings = Arrays.stream(queryStrings).map(QueryString::new).collect(toList());
        }
    }

    private boolean existQueryString(String[] splitedInput) {
        return splitedInput[1].contains("?");
    }

    private void setUserData(String[] detachedScheme) {
        if(!isAnonymous(detachedScheme[1])) {
            detachedScheme = detachedScheme[1].split("@");
            String[] userData = detachedScheme[0].split(":");
            this.userName = new UserName(userData[0]);
            this.password = new Password(userData[1]);
            return;
        }
        this.userName = new UserName();
        this.password = new Password();
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
}
