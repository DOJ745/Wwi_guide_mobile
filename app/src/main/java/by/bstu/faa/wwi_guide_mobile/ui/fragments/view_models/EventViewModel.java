package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import androidx.lifecycle.ViewModel;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.EventDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.Getter;
import lombok.Setter;

public class EventViewModel extends ViewModel implements DataMethods<EventEntity> {
    private EventDao eventDao;
    @Getter
    private UserDao userDao;

    public EventViewModel() {
        userDao = AppInstance.getInstance().getDatabase().userDao();
        eventDao = AppInstance.getInstance().getDatabase().eventDao();
    }
    public Single<EventEntity> getEntityDataById(String id) { return eventDao.getEventById(id); }

    @Override
    public Maybe<UserEntity> getUserFromDB() { return userDao.getUser(); }
}
