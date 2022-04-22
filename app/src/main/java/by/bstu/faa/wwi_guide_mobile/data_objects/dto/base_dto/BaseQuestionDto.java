package by.bstu.faa.wwi_guide_mobile.data_objects.dto.base_dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseQuestionDto extends IdDto {
    @SerializedName("text")@Expose
    private String text;
    @SerializedName("img")@Expose@Nullable
    private String img;
}
