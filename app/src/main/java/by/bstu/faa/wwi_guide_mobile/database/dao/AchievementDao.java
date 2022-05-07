package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import io.reactivex.Maybe;

@Dao
public interface AchievementDao extends BaseDao<AchievementEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.ACHIEVEMENTS_TABLE)
    Maybe<List<AchievementEntity>> getAll();
}
