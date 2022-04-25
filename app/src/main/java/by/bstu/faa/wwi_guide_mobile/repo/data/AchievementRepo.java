package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.AchievementDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AchievementRepo extends DataRepo<AchievementDto> {
    private final String ACHIEVEMENT_REPO = "ACHIEVEMENT REPO";

    @Override
    public void getElements(TokenData token) {
        RetrofitService.getInstance()
                .getAppApi()
                .getAchievements(CONSTANTS.URLS.BEARER + token.getToken())
                .enqueue(new Callback<List<AchievementDto>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<AchievementDto>> call,
                            @NonNull Response<List<AchievementDto>> response) {
                        if(response.body() != null && response.isSuccessful()) {
                            Log.d(ACHIEVEMENT_REPO, "Receiving ACHIEVEMENT DATA");
                            elementsDtoMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<AchievementDto>> call,
                            @NonNull Throwable t) {
                        elementsDtoMutableLiveData.postValue(null);
                    }
                });
    }

    @Override
    public LiveData<List<AchievementDto>> getElementsLiveData() { return elementsDtoMutableLiveData; }
}
