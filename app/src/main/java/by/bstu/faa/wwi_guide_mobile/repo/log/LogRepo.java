package by.bstu.faa.wwi_guide_mobile.repo.log;

import android.util.Log;

import androidx.annotation.NonNull;

import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.LogDto;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogRepo {
    private final String TAG = LogRepo.class.getSimpleName();

    public LogRepo() { Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR); }

    public void sendLog(String token, LogDto data) {
        RetrofitService.getInstance()
                .getAppApi()
                .sendLog(CONSTANTS.URLS.BEARER + token, data)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<Void> call,
                            @NonNull Response<Void> res) {
                        if(res.isSuccessful())
                            Log.d(TAG, "Log info has been sent on server DB");
                    }
                    @Override
                    public void onFailure(
                            @NonNull Call<Void> call,
                            @NonNull Throwable t) {
                    }
                });
    }
}
