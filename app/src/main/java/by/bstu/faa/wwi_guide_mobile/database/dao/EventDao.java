package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface EventDao extends BaseDao<EventEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.EVENTS_TABLE + " WHERE yearId = :yearId")
    Single<List<EventEntity>> getYearEvents(String yearId);
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.EVENTS_TABLE)
    Maybe<List<EventEntity>> getAll();
}
