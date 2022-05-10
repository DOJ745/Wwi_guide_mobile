package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestQuestionEntity;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface TestQuestionDao extends BaseDao<TestQuestionEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.TESTS_QUESTIONS_TABLE)
    Maybe<List<TestQuestionEntity>> getAll();

    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.TESTS_QUESTIONS_TABLE + " WHERE testThemeId =:testThemeId")
    Single<List<TestQuestionEntity>> getTestsQuestionsByThemeId(String testThemeId);
}
