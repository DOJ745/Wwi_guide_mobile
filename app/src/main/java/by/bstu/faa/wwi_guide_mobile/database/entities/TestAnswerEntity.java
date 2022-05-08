package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseAnswerEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity(tableName = CONSTANTS.APP_DATABASE.TESTS_ANSWERS_TABLE,
        foreignKeys = {
        @ForeignKey(entity = TestQuestionEntity.class, parentColumns = "id", childColumns = "testQuestionId")
})
public class TestAnswerEntity extends BaseAnswerEntity {
    @ColumnInfo@NonNull
    private String testQuestionId;
}
