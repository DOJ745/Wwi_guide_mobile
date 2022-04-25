package by.bstu.faa.wwi_guide_mobile.view_models.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.LoginRepo;
import io.reactivex.Completable;

public class LoginViewModel extends ViewModel {
    private final String LOGIN_VIEW_MODEL = "LOGIN VIEW_MODEL";

    private final UserDao userDao;
    private LoginRepo loginRepo;
    private LiveData<UserDto> loginRepoResponse;

    public LoginViewModel() {
        Log.d(LOGIN_VIEW_MODEL, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        userDao = AppInstance.getInstance().getDatabase().userDao();
    }

    public void init() {
        Log.d(LOGIN_VIEW_MODEL, "init");
        loginRepo = new LoginRepo();
        loginRepoResponse = loginRepo.getUserResponse();
    }

    public void loginUser(LoginData loginData) {
        Log.d(LOGIN_VIEW_MODEL, "loginUser");
        loginRepo.loginUser(loginData);
    }
    public LiveData<UserDto> getLoginRepoResponse() { return loginRepoResponse; }

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
