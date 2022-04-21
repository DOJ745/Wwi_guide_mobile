package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.room.Entity;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(tableName = CONSTANTS.APP_DATABASE.RANKS_TABLE)
public class RankEntity {
    private String name;
    private int points;
    private String img;
    private String countryId;
}
