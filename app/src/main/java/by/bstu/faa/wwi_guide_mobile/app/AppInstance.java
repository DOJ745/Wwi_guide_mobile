package by.bstu.faa.wwi_guide_mobile.app;

import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.AppDatabase;

public class AppInstance extends Application {

    public static AppInstance INSTANCE;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        database = Room.databaseBuilder(this, AppDatabase.class, CONSTANTS.APP_DATABASE.DATABASE_NAME).build();
    }

    public static AppInstance getInstance() { return INSTANCE; }
    public AppDatabase getDatabase() { return database; }
}
