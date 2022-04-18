package by.bstu.faa.wwi_guide_mobile.database.entities.base;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class BaseQuestionEntity {
    @ColumnInfo
    private String text;
    @ColumnInfo@Nullable
    private String img;
}
