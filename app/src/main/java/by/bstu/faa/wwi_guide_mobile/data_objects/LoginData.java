package by.bstu.faa.wwi_guide_mobile.data_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("password")
    @Expose
    private String password;

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }

    public void setLogin(String _login) {
        this.login = _login;
    }
    public void setPassword(String _password) {
        this.password = _password;
    }
}
