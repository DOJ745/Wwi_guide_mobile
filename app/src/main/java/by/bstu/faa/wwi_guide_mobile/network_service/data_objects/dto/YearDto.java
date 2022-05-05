package by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.base_dto.IdDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class YearDto extends IdDto {
    @SerializedName("date")@Expose
    private int date;
    @SerializedName("title")@Expose@NonNull
    private String title;
    @SerializedName("img")@Expose@NonNull
    private String imgUrl;
}
