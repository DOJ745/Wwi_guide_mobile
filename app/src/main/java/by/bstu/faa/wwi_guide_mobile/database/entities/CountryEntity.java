package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity(tableName = CONSTANTS.APP_DATABASE.COUNTRIES_TABLE)
public class CountryEntity extends BaseId {
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String img;
}
