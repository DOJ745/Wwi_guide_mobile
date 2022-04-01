package by.bstu.faa.wwi_guide_mobile.database;

import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {
        UserEntity.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
