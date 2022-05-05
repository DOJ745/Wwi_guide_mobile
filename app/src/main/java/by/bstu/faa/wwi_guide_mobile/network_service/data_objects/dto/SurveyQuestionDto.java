package by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto;

import com.google.gson.annotations.SerializedName;

import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.base_dto.BaseQuestionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SurveyQuestionDto extends BaseQuestionDto {
    @SerializedName("itemId")
    private String itemId;
}
