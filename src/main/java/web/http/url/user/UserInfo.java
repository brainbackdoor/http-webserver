package web.http.url.user;

public class UserInfo {
    public static final String DELIMITER_USER_INFO = "@";
    public static final String DEVIDE_USERNAME_PASSWORD = ":";
    private UserName userName;
    private Password password;

    private UserInfo(final UserName userName, final Password password) {
        this.userName = userName;
        this.password = password;
    }

    public static UserInfo of(String detachedScheme) {
        if (!isAnonymous(detachedScheme)) {
            String[] userData = getUserData(detachedScheme);
            userData = splitByColon(userData[0]);

            UserName userName = new UserName(userData[0]);
            Password password = new Password(userData[1]);
            return new UserInfo(userName, password);
        }
        return new UserInfo(new UserName(), new Password());
    }

    private static String[] splitByColon(String userDatum) {
        return userDatum.split(DEVIDE_USERNAME_PASSWORD);
    }

    private static String[] getUserData(String s) {
        return s.split(DELIMITER_USER_INFO);
    }

    private static boolean isAnonymous(String detachedScheme) {
        return !detachedScheme.contains("@");
    }

    public UserName getUserName() {
        return userName;
    }

    public Password getPassword() {
        return password;
    }
}
