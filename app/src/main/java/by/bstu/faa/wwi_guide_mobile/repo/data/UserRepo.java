package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.AppMsgResponseDto;
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

    public void updateUserInfo(String token) {
        RetrofitService.getInstance()
                .getAppApi()
                .updateUserInfo(CONSTANTS.URLS.BEARER + token)
                .enqueue(new Callback<AppMsgResponseDto>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<AppMsgResponseDto> call,
                            @NonNull Response<AppMsgResponseDto> res) {
                        if(res.body() != null && res.isSuccessful()) {
                            //apiRes.postValue(res.body());
                            Log.d(TAG, "Received ACHIEVEMENT DATA");
                        }
                    }
                    @Override
                    public void onFailure(
                            @NonNull Call<AppMsgResponseDto> call,
                            @NonNull Throwable t) {
                        //apiRes.postValue(null);
                    }
                });
    }
}
