package by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.base_dto;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseDataDto extends IdDto {
    @SerializedName("title")@Expose
    private String title;
    @SerializedName("text_paragraphs")@Expose
    protected ArrayList<String> text_paragraphs;
    @SerializedName("images")@Expose
    protected ArrayList<String> images;
    @SerializedName("images_titles")@Expose
    protected ArrayList<String> images_titles;
    @SerializedName("achievementId")@Expose
    private String achievementId;
    @SerializedName("surveyId")@Expose
    private String surveyId;
}
