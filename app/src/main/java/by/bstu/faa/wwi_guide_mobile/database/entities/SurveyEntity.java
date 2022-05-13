package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.ArrayList;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseEntityId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity(tableName = CONSTANTS.APP_DATABASE.SURVEYS_TABLE)
public class SurveyEntity extends BaseEntityId {
    @ColumnInfo@NonNull
    private String question_text;
    @ColumnInfo@NonNull
    private ArrayList<String> answer_variants;
    @ColumnInfo@Nullable
    private String img;
    @ColumnInfo
    private int points;
}
