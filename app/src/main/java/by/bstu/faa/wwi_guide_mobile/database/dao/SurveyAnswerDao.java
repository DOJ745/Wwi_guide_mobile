package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyAnswerEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyQuestionEntity;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface SurveyAnswerDao extends BaseDao<SurveyAnswerEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.SURVEYS_ANSWERS_TABLE)
    Maybe<List<SurveyAnswerEntity>> getAll();
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.SURVEYS_ANSWERS_TABLE + " WHERE surveyQuestionId = :surveyQuestionId")
    Single<List<SurveyAnswerEntity>> getSurveyAnswersByQuestionId(String surveyQuestionId);
}
