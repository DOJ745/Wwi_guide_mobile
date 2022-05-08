package by.bstu.faa.wwi_guide_mobile.api_service.data_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LoginData {
    @SerializedName("login") @Expose
    protected String login;
    @SerializedName("password") @Expose
    protected String password;
}
