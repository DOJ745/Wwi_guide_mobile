package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.AchievementDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.AchievementDto;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AchievementRepo extends DataRepo<AchievementDto, AchievementDao, AchievementEntity> implements DataRepoMethods {
    private final String TAG = ArmamentRepo.class.getSimpleName();

    public AchievementRepo(){
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        dataDao = AppInstance.getInstance().getDatabase().achievementDao();
    }

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getAchievements()
                .enqueue(new Callback<List<AchievementDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<AchievementDto>> call,
                            @NonNull Response<List<AchievementDto>> res) {
                        if(res.body() != null && res.isSuccessful()) {
                            //apiRes.postValue(res.body());
                            Log.d(TAG, "Received ACHIEVEMENT DATA");
                            addDisposableEvents(TAG, res.body(), AchievementEntity.class);
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
    public Maybe<List<AchievementEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
}
