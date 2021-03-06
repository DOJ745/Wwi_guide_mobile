package by.bstu.faa.wwi_guide_mobile.database.dao.base_dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;

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
    Completable delete(T data);
    @Delete
    Completable deleteMany(List<T> data);
}
