package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.bstu.faa.wwi_guide_mobile.data_objects.dto.base_dto.BaseDataDto;
import lombok.Data;

@Data
public class EventDto extends BaseDataDto {
    @SerializedName("yearId")@Expose
    private String yearId;
}
