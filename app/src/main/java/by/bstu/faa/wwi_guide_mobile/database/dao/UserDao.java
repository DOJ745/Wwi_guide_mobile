package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;

@Dao
public interface UserDao extends BaseDao<UserEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.USER_TABLE + " LIMIT 1")
    UserEntity getUser();

    @Query("SELECT score FROM " + CONSTANTS.APP_DATABASE.USER_TABLE)
    int getUserScore();

    @Query("DELETE FROM " + CONSTANTS.APP_DATABASE.USER_TABLE)
    void deleteTempData();
}
