package by.bstu.faa.wwi_guide_mobile.network_service.data_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TokenData {
    @SerializedName("token")
    @Expose
    private String token;
}
