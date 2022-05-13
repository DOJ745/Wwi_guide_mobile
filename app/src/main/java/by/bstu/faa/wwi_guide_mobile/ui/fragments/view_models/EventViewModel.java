package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import androidx.lifecycle.ViewModel;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.AchievementDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.EventDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.Getter;

public class EventViewModel extends ViewModel implements DataImplMethods<EventEntity>, GetUserMethod {
    private EventDao eventDao;
    @Getter
    private UserDao userDao;
    @Getter
    private SurveyDao surveyDao;
    @Getter
    private AchievementDao achievementDao;

    public EventViewModel() {
        userDao = AppInstance.getInstance().getDatabase().userDao();
        eventDao = AppInstance.getInstance().getDatabase().eventDao();
        surveyDao = AppInstance.getInstance().getDatabase().surveyDao();
        achievementDao = AppInstance.getInstance().getDatabase().achievementDao();
    }

    public Single<EventEntity> getEntityDataById(String id) { return eventDao.getEventById(id); }

    @Override
    public Maybe<UserEntity> getUserFromDB() { return userDao.getUser(); }
}
