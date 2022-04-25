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
@Entity(tableName = CONSTANTS.APP_DATABASE.USER_TABLE)
public class UserEntity {
    @PrimaryKey@NonNull
    private String login;
    @ColumnInfo@NonNull
    private String password;
    @ColumnInfo@NonNull
    private String rankId;
    @ColumnInfo@NonNull
    private String countryId;
    @ColumnInfo
    private int score;
    @ColumnInfo@NonNull
    private String roles;
    @ColumnInfo@NonNull
    private String achievements;
}
