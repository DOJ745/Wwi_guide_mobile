package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.auth;

import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.UserDto;
import io.reactivex.Completable;

public interface LoginUserMethods {
    void loginUser(LoginData loginData);
    Completable insertOrUpdateUser(UserDto data);
}
