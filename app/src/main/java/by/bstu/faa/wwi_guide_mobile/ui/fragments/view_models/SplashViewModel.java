package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.AchievementDto;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.repo.auth.LoginRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.AchievementRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.CountryRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.RankRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.YearRepo;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.auth.LoginUserMethods;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

public class SplashViewModel extends ViewModel implements LoginUserMethods {
    private final String SPLASH_VIEW_MODEL = "SPLASH VIEW MODEL";

    private final UserDao userDao;
    @Getter
    private final RankRepo rankRepo;
    @Getter
    private final AchievementRepo achievementRepo;
    @Getter
    private final CountryRepo countryRepo;
    @Getter
    private final YearRepo yearRepo;

    private final LoginRepo loginRepo;
    @Getter
    private final LiveData<UserDto> loginRepoResponse;
    @Getter@Setter
    private List<AchievementDto> resAchievements;

    public SplashViewModel() {
        userDao = AppInstance.getInstance().getDatabase().userDao();

        achievementRepo = new AchievementRepo();
        loginRepo = new LoginRepo();
        rankRepo = new RankRepo();
        countryRepo = new CountryRepo();
        yearRepo = new YearRepo();

        loginRepoResponse = loginRepo.getUserResponse();
    }

    public void getRanks() { rankRepo.callApi(); }
    public void getAchievement(){ achievementRepo.callApi(); }
    public void getCountries() { countryRepo.callApi(); }
    public void getYears() { yearRepo.callApi(); }

    public Flowable<List<UserEntity>> getUserFromDB() { return userDao.getUser(); }

    public void loginUser(LoginData loginData) { loginRepo.loginUser(loginData); }

    @Override
    public Completable insertOrUpdateUser(UserDto data) {
        UserEntity loggedUser = new UserEntity();

        StringBuilder tempRoles = new StringBuilder();
        StringBuilder tempAchievements = new StringBuilder();

        loggedUser.setLogin(data.getLogin());
        loggedUser.setPassword(data.getPassword());
        loggedUser.setCountryId(data.getCountryId());
        loggedUser.setRankId(data.getRankId());
        loggedUser.setScore(data.getScore());

        for (String item : data.getRoles()) {
            tempRoles.append(CONSTANTS.APP_DATABASE.ELEMENT_DIVIDER).append(item);
        }
        loggedUser.setRoles(tempRoles.toString());

        if(data.getAchievements() != null && !data.getAchievements().isEmpty()) {
            for (String item: data.getAchievements()) {
                tempAchievements.append(CONSTANTS.APP_DATABASE.ELEMENT_DIVIDER).append(item);
            }
            loggedUser.setAchievements(tempAchievements.toString());
        }
        else loggedUser.setAchievements("none");

        return userDao.insert(loggedUser);
    }
}