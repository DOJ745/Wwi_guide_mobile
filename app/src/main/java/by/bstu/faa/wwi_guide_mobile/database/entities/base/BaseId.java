package by.bstu.faa.wwi_guide_mobile.database.entities.base;

import androidx.room.PrimaryKey;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public abstract class BaseId {
    @PrimaryKey@NonNull
    protected String _id;
}
