package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyQuestionEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestAnswerEntity;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface SurveyQuestionDao extends BaseDao<SurveyQuestionEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.SURVEYS_QUESTIONS_TABLE)
    Maybe<List<SurveyQuestionEntity>> getAll();
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.SURVEYS_QUESTIONS_TABLE + " WHERE eventId = :eventId")
    Single<SurveyQuestionEntity> getSurveyQuestionByEventId(String eventId);
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.SURVEYS_QUESTIONS_TABLE + " WHERE armamentId = :armamentId")
    Single<SurveyQuestionEntity> getSurveyQuestionByArmamentId(String armamentId);
}
