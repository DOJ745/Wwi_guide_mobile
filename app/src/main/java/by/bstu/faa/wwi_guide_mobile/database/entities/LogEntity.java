package by.bstu.faa.wwi_guide_mobile.database.entities;

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
@Entity(tableName = CONSTANTS.APP_DATABASE.LOGS_TABLE)
public class LogEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
}
