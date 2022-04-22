package by.bstu.faa.wwi_guide_mobile.database.entities;

import androidx.room.Entity;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseQuestionEntity;

@Entity(tableName = CONSTANTS.APP_DATABASE.SURVEYS_QUESTIONS_TABLE)
public class SurveyQuestionEntity extends BaseQuestionEntity {
}
