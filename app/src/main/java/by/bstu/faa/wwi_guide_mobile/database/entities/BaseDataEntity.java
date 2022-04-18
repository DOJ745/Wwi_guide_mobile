package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.room.ColumnInfo;

import java.util.ArrayList;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class BaseDataEntity {
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String text;
    @ColumnInfo
    private ArrayList<String> images;
}
