package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestAnswerEntity;
import io.reactivex.Maybe;

@Dao
public interface TestAnswerDao extends BaseDao<TestAnswerEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.TESTS_ANSWERS_TABLE)
    Maybe<List<TestAnswerEntity>> getAll();
}
