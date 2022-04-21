package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.bstu.faa.wwi_guide_mobile.data_objects.IdDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class YearDto extends IdDto {
    @SerializedName("date")@Expose
    private int date;
    @SerializedName("title")@Expose
    private String title;
    @SerializedName("img")@Expose
    private String imgUrl;
}
