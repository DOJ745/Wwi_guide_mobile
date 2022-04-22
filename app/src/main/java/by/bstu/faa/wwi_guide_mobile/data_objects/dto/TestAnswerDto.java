package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.bstu.faa.wwi_guide_mobile.data_objects.dto.base_dto.BaseAnswerDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestAnswerDto extends BaseAnswerDto {
    @SerializedName("isTrue")@Expose
    private boolean isTrue;
    @SerializedName("testQuestionId")@Expose
    private String testQuestionId;
}
