package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import androidx.lifecycle.ViewModel;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.LogDto;
import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.AchievementDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.EventDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.log.LogRepo;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.Getter;
import lombok.Setter;

public class EventViewModel extends ViewModel implements DataImplMethods<EventEntity>, GetUserMethod {
    private EventDao eventDao;
    @Getter
    private UserDao userDao;
    @Getter
    private SurveyDao surveyDao;
    @Getter
    private AchievementDao achievementDao;
    @Getter
    private LogRepo logRepo;
    @Getter@Setter
    private LogDto log;
    @Getter@Setter
    private String achievementId;
    @Getter@Setter
    private int points = 0;
    @Getter@Setter
    private String chosenVariant;

    public EventViewModel() {
        userDao = AppInstance.getInstance().getDatabase().userDao();
        eventDao = AppInstance.getInstance().getDatabase().eventDao();
        surveyDao = AppInstance.getInstance().getDatabase().surveyDao();
        achievementDao = AppInstance.getInstance().getDatabase().achievementDao();
        logRepo = new LogRepo();
        log = new LogDto();
    }

    public Single<EventEntity> getEntityDataById(String id) { return eventDao.getEventById(id); }
    public Single<SurveyEntity> getSurveyById(String id) { return surveyDao.getSurveyById(id); }
    public void sendLog(String token, LogDto data) { logRepo.sendLog(token, data); }

    @Override
    public Maybe<UserEntity> getUserFromDB() { return userDao.getUser(); }
}
