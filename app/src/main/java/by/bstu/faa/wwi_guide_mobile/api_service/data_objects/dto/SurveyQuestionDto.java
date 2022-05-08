package by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.base_dto.BaseQuestionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SurveyQuestionDto extends BaseQuestionDto {
    @SerializedName("eventId")@Expose
    private ArrayList<String> eventId;
    @SerializedName("armamentId")@Expose
    private String armamentId;
}
