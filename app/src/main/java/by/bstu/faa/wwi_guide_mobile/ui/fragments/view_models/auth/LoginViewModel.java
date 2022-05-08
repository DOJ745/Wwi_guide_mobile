package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.auth.LoginRepo;
import io.reactivex.Completable;

public class LoginViewModel extends ViewModel implements LoginUserMethods {
    private final String TAG = LoginViewModel.class.getSimpleName();

    private final UserDao userDao;
    private LoginRepo loginRepo;
    private LiveData<UserDto> loginRepoResponse;

    public LoginViewModel() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        userDao = AppInstance.getInstance().getDatabase().userDao();

        loginRepo = new LoginRepo();
        loginRepoResponse = loginRepo.getUserResponse();
    }

    public void loginUser(LoginData loginData) {
        Log.d(TAG, "Login user...");
        loginRepo.loginUser(loginData);
    }
    public LiveData<UserDto> getLoginRepoResponse() { return loginRepoResponse; }

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
