package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections;

import androidx.lifecycle.ViewModel;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.data.AchievementRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.YearRepo;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.GetUserMethod;
import io.reactivex.Maybe;
import lombok.Getter;

public class AchievementViewModel extends ViewModel implements GetUserMethod {
    @Getter
    private final AchievementRepo achievementRepo;
    @Getter
    private final UserDao userDao;

    public AchievementViewModel() {
        this.achievementRepo = new AchievementRepo();
        userDao = AppInstance.getInstance().getDatabase().userDao();
    }

    @Override
    public Maybe<UserEntity> getUserFromDB() { return userDao.getUser(); }
}
