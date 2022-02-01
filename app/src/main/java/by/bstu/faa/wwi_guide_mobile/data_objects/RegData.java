package by.bstu.faa.wwi_guide_mobile.data_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class RegData extends LoginData {
    @SerializedName("name")
    @Expose
    private String countryName;
}
