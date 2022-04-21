package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import io.reactivex.Flowable;

public interface EventDao extends BaseDao<EventEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.EVENTS_TABLE + " WHERE yearId = :yearId")
    Flowable<List<EventEntity>> getYearEvents(String yearId);
}
