package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.AchievementDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.AchievementDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AchievementRepo extends DataRepo<AchievementDto, AchievementDao, AchievementEntity> implements DataRepoMethods {
    private final String TAG = ArmamentRepo.class.getSimpleName();

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
                            dataDao = AppInstance.getInstance().getDatabase().achievementDao();
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
