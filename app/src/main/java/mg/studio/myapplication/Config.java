package mg.studio.myapplication;

/**
 * This class sets the URL to connect to the server
 */
public class Config {
    /**
     * URL for login
     */
    public String URL_LOGIN = "https://studio.mg/student/api-login-android/login.php";

    /**
     * URL for register
     */
    public String URL_REGISTER = "https://studio.mg/student/api-login-android/register.php";

    /**
     * Gets the login URL
     * @return the login URL
     */
    public String getLoginUrl() {
        return URL_LOGIN;
    }

    /**
     * Gets the register URL
     * @return the register URL
     */
    public String getRegisterUrl() {
        return URL_REGISTER;
    }
}
