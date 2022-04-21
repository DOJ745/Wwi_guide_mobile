package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@Entity(tableName = CONSTANTS.APP_DATABASE.YEARS_TABLE)
public class YearEntity {
    @PrimaryKey@NonNull
    private int date;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String img;
}
