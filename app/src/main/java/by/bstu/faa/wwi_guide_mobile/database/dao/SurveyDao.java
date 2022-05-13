package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.CountryEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyEntity;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface SurveyDao extends BaseDao<SurveyEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.SURVEYS_TABLE)
    Maybe<List<SurveyEntity>> getAll();
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.SURVEYS_TABLE + " WHERE id = :surveyId")
    Single<SurveyEntity> getSurveyById(String surveyId);
}
