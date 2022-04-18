package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class BaseAnswerDto {
    @SerializedName("text")@Expose
    private String text;
    @SerializedName("points")@Expose
    private int points;
}
