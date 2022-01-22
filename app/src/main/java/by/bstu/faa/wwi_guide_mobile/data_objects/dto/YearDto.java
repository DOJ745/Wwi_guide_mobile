package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class YearDto {
    @SerializedName("date")@Expose
    int date;
    @SerializedName("title")@Expose
    String title;
    @SerializedName("img")@Expose
    String imgUrl;
}
