package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends AppMsgResponseDto {
    @SerializedName("login")@Expose@NonNull
    private String login;
    @SerializedName("password")@Expose@NonNull
    private String password;
    @SerializedName("roles")@Expose@NonNull
    private ArrayList<String> roles;
    @SerializedName("achievements")@Expose
    private ArrayList<String> achievements;
    @SerializedName("score")@Expose@NonNull
    private int score;
    @SerializedName("rankId")@Expose@NonNull
    private String rankId;
    @SerializedName("countryId")@Expose@NonNull
    private String countryId;

    public UserDto() { }
}
