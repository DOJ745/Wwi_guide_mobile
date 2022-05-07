package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestQuestionEntity;
import io.reactivex.Flowable;

@Dao
public interface TestQuestionDao extends BaseDao<TestQuestionEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.TESTS_QUESTIONS_TABLE)
    Flowable<List<TestQuestionEntity>> getAll();
}
