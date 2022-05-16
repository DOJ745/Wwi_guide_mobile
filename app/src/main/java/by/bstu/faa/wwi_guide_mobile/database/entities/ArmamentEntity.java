package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseDataEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseEntityId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@Entity(tableName = CONSTANTS.APP_DATABASE.ARMAMENT_TABLE, foreignKeys = {
        @ForeignKey(entity = AchievementEntity.class, parentColumns = "id", childColumns = "achievementId"),
        @ForeignKey(entity = SurveyEntity.class, parentColumns = "id", childColumns = "surveyId")
})
public class ArmamentEntity extends BaseDataEntity {
    @ColumnInfo@NonNull
    private String category;
    @ColumnInfo@Nullable
    private String subcategory;
}
