package by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.base_dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseAnswerDto extends IdDto {
    @SerializedName("text")@Expose
    private String text;
    @SerializedName("points")@Expose
    private int points;
}
