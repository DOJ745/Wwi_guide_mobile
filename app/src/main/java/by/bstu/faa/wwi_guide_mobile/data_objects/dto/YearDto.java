package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class YearDto {
    @SerializedName("date")@Expose
    private int date;
    @SerializedName("title")@Expose
    private String title;
    @SerializedName("img")@Expose
    private String imgUrl;
}
