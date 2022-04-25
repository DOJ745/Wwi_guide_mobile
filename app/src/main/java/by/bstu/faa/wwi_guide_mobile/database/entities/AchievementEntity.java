package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@Entity(tableName = CONSTANTS.APP_DATABASE.ACHIEVEMENTS_TABLE)
public class AchievementEntity {
    @PrimaryKey@NonNull
    private String name;
    @ColumnInfo@NonNull
    private String description;
    @ColumnInfo@NonNull
    private String img;
    @ColumnInfo@NonNull
    private int points;
}
