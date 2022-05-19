package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseEntityId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@Entity(tableName = CONSTANTS.APP_DATABASE.TEST_THEME_TABLE
/*, foreignKeys = {
        @ForeignKey(entity = AchievementEntity.class, parentColumns = "id", childColumns = "achievementId")
}*/
)
public class TestThemeEntity extends BaseEntityId {
    @ColumnInfo@NonNull
    private String name;
    @ColumnInfo@Nullable
    private String achievementId;
}
