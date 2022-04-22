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
@Entity(tableName = CONSTANTS.APP_DATABASE.RANKS_TABLE)
public class RankEntity extends BaseId {
    @ColumnInfo
    private String name;
    @ColumnInfo
    private int points;
    @ColumnInfo
    private String img;
    @ColumnInfo
    private String countryId;
}
