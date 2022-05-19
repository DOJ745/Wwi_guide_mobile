package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseQuestionEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity(tableName = CONSTANTS.APP_DATABASE.TESTS_QUESTIONS_TABLE/*,
        foreignKeys = {
        @ForeignKey(entity = TestThemeEntity.class, parentColumns = "id", childColumns = "testThemeId")
}*/)
public class TestQuestionEntity extends BaseQuestionEntity {
    @ColumnInfo@Nullable
    private String testThemeId;
}
