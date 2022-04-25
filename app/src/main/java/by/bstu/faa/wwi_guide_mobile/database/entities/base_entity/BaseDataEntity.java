package by.bstu.faa.wwi_guide_mobile.database.entities.base_entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public abstract class BaseDataEntity extends BaseId {
    @ColumnInfo
    @NonNull
    protected String title;
    @ColumnInfo
    @NonNull
    protected String text;
    @ColumnInfo
    protected String images;
}
