package by.bstu.faa.wwi_guide_mobile.repo.auth;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.AppMsgResponseDto;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {
    private final String TAG = LoginRepo.class.getSimpleName();

    @Getter
    protected final MutableLiveData<UserDto> userResponse;

    public LoginRepo() { userResponse = new MutableLiveData<>(); }

    public void loginUser(LoginData loginData) {
        RetrofitService.getInstance()
                .getAppApi()
                .loginUser(loginData)
                .enqueue(new Callback<UserDto>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<UserDto> call,
                            @NonNull Response<UserDto> response) {
                        if(response.body() != null && response.isSuccessful()) {
                            userResponse.postValue(response.body());
                            Log.d(TAG, "Received USER DATA");
                        }
                        if (response.errorBody()!= null && response.code() == CONSTANTS.HTTP_CODES.BAD_REQUEST){
                            // Deserializing response from errorBody
                            Gson gson = new Gson();
                            Type type = new TypeToken<UserDto>(){}.getType();
                            UserDto errorResponse = gson.fromJson(response.errorBody().charStream(), type);
                            Log.d(TAG, "errorResponse msg status: " + errorResponse.getMsgStatus());
                            userResponse.postValue(errorResponse);
                        }
                    }
                    @Override
                    public void onFailure(
                            @NonNull Call<UserDto> call,
                            @NonNull Throwable t) {
                        userResponse.postValue(null);
                    }
                });
    }
}
