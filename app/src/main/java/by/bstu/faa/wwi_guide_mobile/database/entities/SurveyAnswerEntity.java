package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.room.Entity;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseAnswerEntity;

@Entity(tableName = CONSTANTS.APP_DATABASE.SURVEYS_ANSWERS_TABLE)
public class SurveyAnswerEntity extends BaseAnswerEntity {
}
