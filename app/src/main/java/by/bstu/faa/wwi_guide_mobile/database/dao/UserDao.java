package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import io.reactivex.Flowable;

@Dao
public interface UserDao extends BaseDao<UserEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.USER_TABLE)
    Flowable<List<UserEntity>> getUser();

    @Query("SELECT score FROM " + CONSTANTS.APP_DATABASE.USER_TABLE)
    int getUserScore();
}
