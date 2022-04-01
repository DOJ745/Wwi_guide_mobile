package by.bstu.faa.wwi_guide_mobile.view_models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.AppMsgResponseDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.database.AppDatabase;
import by.bstu.faa.wwi_guide_mobile.repo.LoginRepo;

public class LoginViewModel extends ViewModel {

    private AppDatabase database;
    private LoginRepo loginRepo;
    private LiveData<UserDto> loginRepoResponse;

    public void init(){
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_VIEW_MODEL, "init");
        database = AppInstance.getInstance().getDatabase();
        loginRepo = new LoginRepo();
        loginRepoResponse = loginRepo.getUserResponse();
    }

    public void loginUser(LoginData loginData) {
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_VIEW_MODEL, "loginUser");
        loginRepo.loginUser(loginData);
    }
    public LiveData<UserDto> getLoginRepoResponse() { return loginRepoResponse; }
}
