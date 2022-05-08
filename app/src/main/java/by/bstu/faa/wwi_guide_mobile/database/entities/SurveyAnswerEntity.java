package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseAnswerEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity(tableName = CONSTANTS.APP_DATABASE.SURVEYS_ANSWERS_TABLE,
        foreignKeys = {
        @ForeignKey(entity = SurveyQuestionEntity.class, parentColumns = "id", childColumns = "surveyQuestionId")
})
public class SurveyAnswerEntity extends BaseAnswerEntity {
    @ColumnInfo@NonNull
    private String surveyQuestionId;
}
