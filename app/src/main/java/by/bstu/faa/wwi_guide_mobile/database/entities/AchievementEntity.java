package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

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
@Entity(tableName = CONSTANTS.APP_DATABASE.ACHIEVEMENTS_TABLE)
public class AchievementEntity extends BaseEntityId {
    @ColumnInfo@NonNull
    private String name;
    @ColumnInfo@NonNull
    private String description;
    @ColumnInfo@NonNull
    private String img;
    @ColumnInfo
    private int points;
}
