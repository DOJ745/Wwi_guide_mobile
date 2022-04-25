package by.bstu.faa.wwi_guide_mobile.view_models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.AchievementDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.database.dao.AchievementDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.auth.LoginRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.AchievementRepo;
import by.bstu.faa.wwi_guide_mobile.view_models.auth.LoginUserMethods;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class MainViewModel extends ViewModel implements LoginUserMethods {
    private final String MAIN_VIEW_MODEL = "MAIN VIEW MODEL";
    private final UserDao userDao;
    private final AchievementDao achievementDao;

    private AchievementRepo achievementRepo;
    private LoginRepo loginRepo;
    private LiveData<List<AchievementDto>> achievementDtoResponseLiveData;
    private LiveData<UserDto> loginRepoResponse;

    public MainViewModel() {
        userDao = AppInstance.getInstance().getDatabase().userDao();
        achievementDao = AppInstance.getInstance().getDatabase().achievementDao();
    }

    public void init() {
        achievementRepo = new AchievementRepo();
        loginRepo = new LoginRepo();
        achievementDtoResponseLiveData = achievementRepo.getElementsLiveData();
        loginRepoResponse = loginRepo.getUserResponse();
    }

    public void loginUser(LoginData loginData) {
        Log.d(MAIN_VIEW_MODEL, "Login user...");
        loginRepo.loginUser(loginData);
    }

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

    public void getAchievement(TokenData token){ achievementRepo.getElements(token); }
    public LiveData<List<AchievementDto>> getAchievementDtoResponseLiveData() { return achievementDtoResponseLiveData; }
    public LiveData<UserDto> getLoginRepoResponse() { return loginRepoResponse; }

    public Flowable<List<UserEntity>> getUserFromDB() { return userDao.getUser(); }
    public Flowable<List<AchievementEntity>> getAchievementsFromDB() { return achievementDao.getAll(); }

    public void insertOrUpdateAchievements(List<AchievementDto> data) {
        ModelMapper modelMapper = new ModelMapper();
        List<AchievementEntity> temp = new ArrayList<>();
        for (AchievementDto achievementDto: data) {
            AchievementEntity achievementEntity = modelMapper.map(achievementDto, AchievementEntity.class);
            Log.d(MAIN_VIEW_MODEL, achievementEntity.getDescription() + "\n" + achievementEntity.getName());
        }

        //return achievementDao.insertMany(temp);
    }
}