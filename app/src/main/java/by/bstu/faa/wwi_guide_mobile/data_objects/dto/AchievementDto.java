package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class AchievementDto {
    @SerializedName("name")@Expose
    private String name;
    @SerializedName("description")@Expose
    private String description;
    @SerializedName("img")@Expose
    private String img;
}
