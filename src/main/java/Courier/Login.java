package Courier;

public class Login {
    private String login;
    private String password;

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static Login from(CourierInfo courierInfo) {
        return new Login(courierInfo.getLogin(), courierInfo.getPassword());
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
