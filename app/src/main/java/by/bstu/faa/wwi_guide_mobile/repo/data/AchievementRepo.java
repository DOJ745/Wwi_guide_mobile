package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.AchievementDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AchievementRepo extends DataRepo<AchievementDto> {
    private final String ACHIEVEMENT_REPO = "ACHIEVEMENT REPO";

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getAchievements()
                .enqueue(new Callback<List<AchievementDto>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<AchievementDto>> call,
                            @NonNull Response<List<AchievementDto>> response) {
                        if(response.body() != null && response.isSuccessful()) {
                            apiRes.postValue(response.body());
                            Log.d(ACHIEVEMENT_REPO, "Received ACHIEVEMENT DATA");
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<AchievementDto>> call,
                            @NonNull Throwable t) {
                        apiRes.postValue(null);
                    }
                });
    }

    @Override
    public LiveData<List<AchievementDto>> getApiRes() { return apiRes; }
}
