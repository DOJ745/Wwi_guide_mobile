package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.ArmamentEntity;
import io.reactivex.Flowable;

@Dao
public interface ArmamentDao extends BaseDao<ArmamentEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.ARMAMENT_TABLE)
    Flowable<List<ArmamentEntity>> getAll();
}
