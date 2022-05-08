package by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.base_dto.BaseDataDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArmamentDto extends BaseDataDto {
    @SerializedName("category")@Expose
    private String category;
}
