package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class RankDto {
    @SerializedName("name")@Expose
    private String name;
    @SerializedName("points")@Expose
    private int points;
    @SerializedName("img")@Expose
    private String imgUrl;
    @SerializedName("countryId")@Expose
    private String countryId;
}