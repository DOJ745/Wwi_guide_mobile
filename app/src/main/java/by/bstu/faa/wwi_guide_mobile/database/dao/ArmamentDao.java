package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.ArmamentEntity;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ArmamentDao extends BaseDao<ArmamentEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.ARMAMENT_TABLE)
    Maybe<List<ArmamentEntity>> getAll();
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.ARMAMENT_TABLE + " WHERE id = :id")
    Single<ArmamentEntity> getArmamentById(String id);
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.ARMAMENT_TABLE + " WHERE category = :categoryName")
    Single<List<ArmamentEntity>> getArmamentsByCategory(String categoryName);
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.ARMAMENT_TABLE + " WHERE subcategory = :subcategoryName")
    Single<List<ArmamentEntity>> getArmamentsBySubcategory(String subcategoryName);
}
