package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseDataEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@ToString
@Entity(tableName = CONSTANTS.APP_DATABASE.EVENTS_TABLE,
        foreignKeys = {
        @ForeignKey(entity = YearEntity.class, parentColumns = "id", childColumns = "yearId"),
                @ForeignKey(entity = AchievementEntity.class, parentColumns = "id", childColumns = "achievementId"),
                @ForeignKey(entity = SurveyEntity.class, parentColumns = "id", childColumns = "surveyId")
})
public class EventEntity extends BaseDataEntity {
    @Getter@Setter
    private String yearId;
}
