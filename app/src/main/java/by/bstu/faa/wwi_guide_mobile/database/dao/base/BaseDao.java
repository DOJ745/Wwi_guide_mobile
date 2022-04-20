package by.bstu.faa.wwi_guide_mobile.database.dao.base;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import io.reactivex.Completable;

@Dao
public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(T data);

    @Update
    void update(T data);

    @Delete
    void delete(T data);
}
