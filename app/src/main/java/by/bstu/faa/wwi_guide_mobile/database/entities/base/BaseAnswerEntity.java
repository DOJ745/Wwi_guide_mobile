package by.bstu.faa.wwi_guide_mobile.database.entities.base;

import androidx.room.ColumnInfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class BaseAnswerEntity {
    @ColumnInfo
    private String text;
    @ColumnInfo
    private int points;
}
