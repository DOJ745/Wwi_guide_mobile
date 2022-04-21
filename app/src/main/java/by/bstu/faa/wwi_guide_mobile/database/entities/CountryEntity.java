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
@Entity(tableName = CONSTANTS.APP_DATABASE.COUNTRIES_TABLE)
public class CountryEntity {
    private String name;
    private String img;
}
