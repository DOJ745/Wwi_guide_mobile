package by.bstu.faa.wwi_guide_mobile.database.entities.base;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public abstract class BaseQuestionEntity extends BaseId {
    @ColumnInfo@NonNull
    protected String text;
    @ColumnInfo@Nullable
    protected String img;
}
