package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyQuestionEntity;
import io.reactivex.Maybe;

@Dao
public interface SurveyQuestionDao extends BaseDao<SurveyQuestionEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.SURVEYS_QUESTIONS_TABLE)
    Maybe<List<SurveyQuestionEntity>> getAll();
}
