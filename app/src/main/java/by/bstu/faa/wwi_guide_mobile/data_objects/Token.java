package by.bstu.faa.wwi_guide_mobile.data_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Token {
    @SerializedName("token")
    @Expose
    private String token;
}
