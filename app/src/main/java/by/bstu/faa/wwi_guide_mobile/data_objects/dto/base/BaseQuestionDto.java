package by.bstu.faa.wwi_guide_mobile.data_objects.dto.base;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class BaseQuestionDto {
    @SerializedName("text")@Expose
    private String text;
    @SerializedName("img")@Expose@Nullable
    private String img;
}
