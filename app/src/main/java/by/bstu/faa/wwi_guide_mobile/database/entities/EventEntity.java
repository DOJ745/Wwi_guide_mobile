package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseDataEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
@Entity(tableName = CONSTANTS.APP_DATABASE.EVENTS_TABLE, foreignKeys = {
        @ForeignKey(entity = YearEntity.class, parentColumns = "_id", childColumns = "yearId")
})
public class EventEntity extends BaseDataEntity {
    @Getter@Setter
    private String yearId;
}
