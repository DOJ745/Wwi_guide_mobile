package by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.base_dto.IdDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SurveyDto extends IdDto {
    @SerializedName("question_text")@Expose
    private String question_text;
    @SerializedName("answer_variants")@Expose
    private ArrayList<String> answer_variants;
    @SerializedName("img")@Expose
    private String img;
    @SerializedName("points")@Expose
    private int points;
}
