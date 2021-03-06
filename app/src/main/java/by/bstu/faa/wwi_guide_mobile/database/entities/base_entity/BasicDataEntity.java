package by.bstu.faa.wwi_guide_mobile.database.entities.base_entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;

import java.util.ArrayList;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
public abstract class BasicDataEntity extends BaseEntityId {
    @ColumnInfo
    @NonNull
    protected String title;
    @ColumnInfo
    @NonNull
    protected ArrayList<String> text_paragraphs;
    @ColumnInfo
    @NonNull
    protected ArrayList<String> images;
    @ColumnInfo
    @NonNull
    protected ArrayList<String> images_titles;
    @ColumnInfo
    @Nullable
    protected String surveyId;
    @ColumnInfo
    @Nullable
    private String achievementId;
}
