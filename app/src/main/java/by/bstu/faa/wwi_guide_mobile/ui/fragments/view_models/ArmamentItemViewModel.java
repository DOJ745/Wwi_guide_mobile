package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import androidx.lifecycle.ViewModel;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.LogDto;
import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.AchievementDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.ArmamentDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.ArmamentEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.log.LogRepo;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.Getter;
import lombok.Setter;

public class ArmamentItemViewModel extends ViewModel implements ViewModelDataMethods<ArmamentEntity>, GetUserMethod{
    private final ArmamentDao armamentDao;
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

    public ArmamentItemViewModel() {
        userDao = AppInstance.getInstance().getDatabase().userDao();
        armamentDao = AppInstance.getInstance().getDatabase().armamentDao();
        surveyDao = AppInstance.getInstance().getDatabase().surveyDao();
        achievementDao = AppInstance.getInstance().getDatabase().achievementDao();
        logRepo = new LogRepo();
        log = new LogDto();
    }

    @Override
    public Single<ArmamentEntity> getEntityDataById(String entityId) { return armamentDao.getArmamentById(entityId); }
    @Override
    public Maybe<UserEntity> getUserFromDB() { return userDao.getUser(); }
}
