package by.bstu.faa.wwi_guide_mobile.view_models;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import io.reactivex.Flowable;

public class MainViewModel extends ViewModel {
    private final UserDao userDao;
    public MainViewModel() {
        Log.d(CONSTANTS.LOG_TAGS.MAIN_VIEW_MODEL, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        userDao = AppInstance.getInstance().getDatabase().userDao();
    }

    public Flowable<UserEntity> getUser() { return userDao.getUser(); }
}