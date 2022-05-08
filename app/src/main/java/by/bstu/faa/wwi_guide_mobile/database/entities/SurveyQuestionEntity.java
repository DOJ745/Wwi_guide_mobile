package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseQuestionEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity(tableName = CONSTANTS.APP_DATABASE.SURVEYS_QUESTIONS_TABLE,
        foreignKeys = {
        @ForeignKey(entity = EventEntity.class, parentColumns = "id", childColumns = "eventId"),
                @ForeignKey(entity = ArmamentEntity.class, parentColumns = "id", childColumns = "armamentId")
})
public class SurveyQuestionEntity extends BaseQuestionEntity {
    @ColumnInfo@NonNull
    private String eventId;
    @ColumnInfo@NonNull
    private String armamentId;
}
