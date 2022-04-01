package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode @Getter @Setter
@Entity(tableName = CONSTANTS.APP_DATABASE.USER_TABLE)
public class UserEntity {
    @PrimaryKey @NonNull
    private String login;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "rankId")
    private String rankId;

    @ColumnInfo(name = "countryId")
    private String countryId;

    @ColumnInfo(name = "score")
    private int score;

    @ColumnInfo(name = "roles")
    private ArrayList<String> roles;

    @ColumnInfo(name = "achievements")
    private ArrayList<String> achievements;
}
