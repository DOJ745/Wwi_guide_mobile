package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends AppMsgResponseDto {
    @SerializedName("login")@Expose
    private String login;
    @SerializedName("password")@Expose
    private String password;
    @SerializedName("roles")@Expose
    private ArrayList<String> roles;
    @SerializedName("achievements")@Expose
    private ArrayList<String> achievements;
    @SerializedName("score")@Expose
    private int score;
    @SerializedName("rankId")@Expose
    private String rankId;
    @SerializedName("countryId")@Expose
    private String countryId;
}
