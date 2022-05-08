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
@Entity(tableName = CONSTANTS.APP_DATABASE.YEARS_TABLE)
public class YearEntity extends BaseEntityId {
    @ColumnInfo
    private int date;
    @ColumnInfo@NonNull
    private String title;
    @ColumnInfo@NonNull
    private String img;
}
