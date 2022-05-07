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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AchievementRepo extends DataRepo<AchievementDto> {
    private final String ACHIEVEMENT_REPO = "ACHIEVEMENT REPO";
    private final AchievementDao achievementDao = AppInstance.getInstance().getDatabase().achievementDao();
    @Getter
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    @Getter@Setter
    private List<AchievementEntity> currentAchievements;

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
                            Log.d(ACHIEVEMENT_REPO, "Received ACHIEVEMENT DATA");

                            mDisposable.add(insertOrUpdateAchievements(res.body())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            // On complete
                                            () -> {
                                                Log.d(ACHIEVEMENT_REPO, "DB: Achievements has been written into database");
                                                mDisposable.add(getAchievementsFromDB()
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(
                                                                // On complete
                                                                data -> {
                                                                    Log.d(ACHIEVEMENT_REPO, "DB: Received current achievements");
                                                                    setCurrentAchievements(data);

                                                                    mDisposable.add(deleteOldAchievements(
                                                                            getCurrentAchievements(),
                                                                            res.body())
                                                                            .subscribeOn(Schedulers.io())
                                                                            .observeOn(AndroidSchedulers.mainThread())
                                                                            .subscribe(
                                                                                    // On complete
                                                                                    () -> Log.d(ACHIEVEMENT_REPO, "DB: Deleted old achievements"),
                                                                                    // On error
                                                                                    err -> Log.e(ACHIEVEMENT_REPO, "Unable to delete achievements", err))
                                                                    );
                                                                },
                                                                // On error
                                                                err -> Log.e(ACHIEVEMENT_REPO, "Unable to get achievements", err))
                                                );
                                            },
                                            // On error
                                            err -> Log.e(ACHIEVEMENT_REPO, "Unable to insert achievements", err))
                            );
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
    public Flowable<List<AchievementEntity>> getAchievementsFromDB() { return achievementDao.getAll(); }

    public Completable insertOrUpdateAchievements(List<AchievementDto> data) {
        ModelMapper modelMapper = new ModelMapper();
        List<AchievementEntity> temp = new ArrayList<>();
        for (AchievementDto dto: data) {
            AchievementEntity entity = modelMapper.map(dto, AchievementEntity.class);
            temp.add(entity);
        }
        return achievementDao.insertMany(temp);
    }

    public Completable deleteOldAchievements(List<AchievementEntity> currentData, List<AchievementDto> newData) {
        ModelMapper modelMapper = new ModelMapper();
        List<AchievementEntity> mappedNewData = new ArrayList<>();
        for (AchievementDto dto: newData) {
            AchievementEntity entity = modelMapper.map(dto, AchievementEntity.class);
            mappedNewData.add(entity);
        }
        if(currentData == null) { return achievementDao.insertMany(mappedNewData); }
        if(mappedNewData.size() < currentData.size()) {
            for(int i = 0; i < newData.size(); i++) {
                currentData.remove(mappedNewData.get(i));
            }
            return achievementDao.deleteMany(currentData);
        }
        else {
            return achievementDao.insertMany(currentData);
        }
    }
}
