package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@Data
public class BaseDataDto {
    @SerializedName("title")@Expose
    private String title;
    @SerializedName("text")@Expose
    private String text;
    @SerializedName("images")@Expose
    private ArrayList<String> images;
}
