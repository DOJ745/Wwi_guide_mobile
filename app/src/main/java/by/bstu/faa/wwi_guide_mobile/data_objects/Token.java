package by.bstu.faa.wwi_guide_mobile.data_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }
    public void setToken(String _token) { this.token = _token; }
}
