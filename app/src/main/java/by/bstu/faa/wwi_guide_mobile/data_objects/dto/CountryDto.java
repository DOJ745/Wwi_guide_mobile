package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.bstu.faa.wwi_guide_mobile.data_objects.IdDto;
import lombok.Data;

@Data
public class CountryDto extends IdDto {
    public CountryDto() { super(); }

    @SerializedName("name")@Expose
    private String name;
    @SerializedName("img")@Expose
    private String flagUrl;
}
