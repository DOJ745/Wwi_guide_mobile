package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.AppMsgResponseDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepo {
    private final String TAG = UserRepo.class.getSimpleName();

    public UserRepo() { Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR); }

    public void updateUserInfo(String token, UserDto data) {
        RetrofitService.getInstance()
                .getAppApi()
                .updateUserInfo(CONSTANTS.URLS.BEARER + token, data)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<Void> call,
                            @NonNull Response<Void> res) {
                        if(res.isSuccessful())
                            Log.d(TAG, "User info has been updated on server DB");
                    }
                    @Override
                    public void onFailure(
                            @NonNull Call<Void> call,
                            @NonNull Throwable t) {
                    }
                });
    }
}
