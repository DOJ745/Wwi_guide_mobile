package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.YearEntity;
import io.reactivex.Flowable;

@Dao
public interface YearDao extends BaseDao<YearEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.YEARS_TABLE)
    Flowable<List<YearEntity>> getAll();
}
