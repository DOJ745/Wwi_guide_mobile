package by.bstu.faa.wwi_guide_mobile.database.dao.base_dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(T data);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMany(List<T> dataList);

    @Update
    void update(T data);
    @Update
    void updateMany(List<T> dataList);

    @Delete
    void delete(T data);
    @Delete
    Completable deleteMany(List<T> data);
}
