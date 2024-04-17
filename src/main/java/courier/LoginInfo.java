package courier;

public class LoginInfo {
    private String login;
    private String password;

    public LoginInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static LoginInfo from(CourierInfo courierInfo) {
        return new LoginInfo(courierInfo.getLogin(), courierInfo.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
