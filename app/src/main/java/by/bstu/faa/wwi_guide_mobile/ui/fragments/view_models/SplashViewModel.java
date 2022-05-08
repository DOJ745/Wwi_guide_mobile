package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.repo.auth.LoginRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.AchievementRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.ArmamentRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.CountryRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.EventsRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.RankRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.SurveysAnswersRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.SurveysQuestionsRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.TestsAnswersRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.TestsQuestionsRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.TestsThemesRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.YearRepo;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.auth.LoginUserMethods;
import io.reactivex.Completable;
import io.reactivex.Flowable;
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
    private SurveysQuestionsRepo surveysQuestionsRepo;
    @Getter
    private SurveysAnswersRepo surveysAnswersRepo;

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
        achievementRepo.callApi();
        armamentRepo.callApi();
        yearRepo.callApi();
        countryRepo.callApi();
        rankRepo.callApi();
        eventsRepo.callApi();
        testsThemesRepo.callApi();
        testsAnswersRepo.callApi();
        testsQuestionsRepo.callApi();
        surveysAnswersRepo.callApi();
        surveysQuestionsRepo.callApi();
    }

    public Flowable<List<UserEntity>> getUserFromDB() { return userDao.getUser(); }

    public void loginUser(LoginData loginData) { loginRepo.loginUser(loginData); }

    private void initRepos(){
        this.testsThemesRepo = new TestsThemesRepo();
        this.testsQuestionsRepo = new TestsQuestionsRepo();
        this.testsAnswersRepo = new TestsAnswersRepo();
        this.surveysQuestionsRepo = new SurveysQuestionsRepo();
        this.surveysAnswersRepo = new SurveysAnswersRepo();
        this.armamentRepo = new ArmamentRepo();
        this.achievementRepo = new AchievementRepo();
        this.loginRepo = new LoginRepo();
        this.rankRepo = new RankRepo();
        this.countryRepo = new CountryRepo();
        this.yearRepo = new YearRepo();
        this.eventsRepo = new EventsRepo();
    }

    @Override
    public Completable insertOrUpdateUser(UserDto data) {
        UserEntity loggedUser = new UserEntity();

        loggedUser.setLogin(data.getLogin());
        loggedUser.setPassword(data.getPassword());
        loggedUser.setCountryId(data.getCountryId());
        loggedUser.setRankId(data.getRankId());
        loggedUser.setScore(data.getScore());
        loggedUser.setAchievements(data.getAchievements());
        loggedUser.setRoles(data.getRoles());

        return userDao.insert(loggedUser);
    }
}