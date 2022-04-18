package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@Entity(tableName = CONSTANTS.APP_DATABASE.USER_TABLE)
public class UserEntity {
    @PrimaryKey
    @NonNull
    private String login;

    @ColumnInfo
    private String password;

    @ColumnInfo
    private String rankId;

    @ColumnInfo
    private String countryId;

    @ColumnInfo
    private int score;

    @ColumnInfo
    private String roles;

    @ColumnInfo
    private String achievements;
}
