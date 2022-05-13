package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.auth.LoginRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.AchievementRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.ArmamentRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.CountryRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.EventsRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.RankRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.SurveyRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.TestsAnswersRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.TestsQuestionsRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.TestsThemesRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.YearRepo;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.auth.LoginUserMethods;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import lombok.Getter;

public class SplashViewModel extends ViewModel implements LoginUserMethods {
    private final String TAG = SplashViewModel.class.getSimpleName();

    private UserDao userDao;
    @Getter
    private RankRepo rankRepo;
    @Getter
    private AchievementRepo achievementRepo;
    @Getter
    private ArmamentRepo armamentRepo;
    @Getter
    private CountryRepo countryRepo;
    @Getter
    private EventsRepo eventsRepo;
    @Getter
    private YearRepo yearRepo;
    @Getter
    private TestsThemesRepo testsThemesRepo;
    @Getter
    private TestsQuestionsRepo testsQuestionsRepo;
    @Getter
    private TestsAnswersRepo testsAnswersRepo;
    @Getter
    private SurveyRepo surveyRepo;

    private LoginRepo loginRepo;
    @Getter
    private final LiveData<UserDto> loginRepoResponse;

    public SplashViewModel() {
        initRepos();
        this.loginRepoResponse = loginRepo.getUserResponse();

        userDao = AppInstance.getInstance().getDatabase().userDao();
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    public void getAllData() {
        surveyRepo.callApi();
        achievementRepo.callApi();
        testsThemesRepo.callApi();
        countryRepo.callApi();
        yearRepo.callApi();
        rankRepo.callApi();
        testsAnswersRepo.callApi();
        testsQuestionsRepo.callApi();
        armamentRepo.callApi();
        eventsRepo.callApi();
    }

    public Maybe<UserEntity> getUserFromDB() { return userDao.getUser(); }

    public void loginUser(LoginData loginData) { loginRepo.loginUser(loginData); }

    private void initRepos() {
        this.surveyRepo = new SurveyRepo();
        this.testsThemesRepo = new TestsThemesRepo();
        this.testsQuestionsRepo = new TestsQuestionsRepo();
        this.testsAnswersRepo = new TestsAnswersRepo();
        this.countryRepo = new CountryRepo();
        this.yearRepo = new YearRepo();
        this.achievementRepo = new AchievementRepo();
        this.loginRepo = new LoginRepo();
        this.rankRepo = new RankRepo();
        this.armamentRepo = new ArmamentRepo();
        this.eventsRepo = new EventsRepo();
    }

    @Override
    public Completable insertOrUpdateUser(UserDto data) {
        UserEntity loggedUser = new UserEntity();

        loggedUser.setLogin(data.getLogin());
        loggedUser.setPassword(data.getPassword());
        loggedUser.setCountryId(data.getCountryId());
        loggedUser.setScore(data.getScore());
        loggedUser.setAchievements(data.getAchievements());
        loggedUser.setRoles(data.getRoles());

        return userDao.insert(loggedUser);
    }
}