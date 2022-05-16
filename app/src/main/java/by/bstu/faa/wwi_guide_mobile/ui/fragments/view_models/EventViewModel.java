package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import androidx.lifecycle.ViewModel;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.LogDto;
import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.AchievementDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.EventDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.log.LogRepo;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.Getter;
import lombok.Setter;

public class EventViewModel extends ViewModel implements ViewModelDataMethods<EventEntity>, GetUserMethod {
    private final EventDao eventDao;
    @Getter
    private final UserDao userDao;
    @Getter
    private final SurveyDao surveyDao;
    @Getter
    private final AchievementDao achievementDao;
    @Getter
    private final LogRepo logRepo;
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

    public Single<AchievementEntity> getAchievementById(String id) { return achievementDao.getAchievementById(id); }
    public Single<EventEntity> getEntityDataById(String id) { return eventDao.getEventById(id); }
    @Override
    public Maybe<UserEntity> getUserFromDB() { return userDao.getUser(); }
}
