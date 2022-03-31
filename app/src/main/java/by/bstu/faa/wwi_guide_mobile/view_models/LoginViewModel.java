package by.bstu.faa.wwi_guide_mobile.view_models;

import androidx.lifecycle.LiveData;

import by.bstu.faa.wwi_guide_mobile.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.AppMsgResponseDto;
import by.bstu.faa.wwi_guide_mobile.repo.LoginRepo;

public class LoginViewModel {

    private LoginRepo loginRepo;
    private LiveData<AppMsgResponseDto> loginRepoResponse;

    public void init(){
        loginRepo = new LoginRepo();
        loginRepoResponse = loginRepo.getResponse();
    }

    public void loginUser(LoginData loginData) { loginRepo.loginUser(loginData); }
    public LiveData<AppMsgResponseDto> getLoginRepoResponse() { return loginRepoResponse; }
}
