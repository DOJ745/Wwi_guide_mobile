package by.bstu.faa.wwi_guide_mobile.view_models;

import android.util.Log;
import androidx.lifecycle.ViewModel;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import io.reactivex.Flowable;

public class MainViewModel extends ViewModel {
    private final UserDao userDao;
    public MainViewModel() {
        userDao = AppInstance.getInstance().getDatabase().userDao();
    }

    public Flowable<List<UserEntity>> getUser() { return userDao.getUser(); }
}