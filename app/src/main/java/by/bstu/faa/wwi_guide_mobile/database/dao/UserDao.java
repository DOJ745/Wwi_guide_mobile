package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface UserDao extends BaseDao<UserEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.USER_TABLE)
    Maybe<UserEntity> getUser();

    @Query("SELECT score FROM " + CONSTANTS.APP_DATABASE.USER_TABLE)
    Single<Integer> getUserScore();
}
