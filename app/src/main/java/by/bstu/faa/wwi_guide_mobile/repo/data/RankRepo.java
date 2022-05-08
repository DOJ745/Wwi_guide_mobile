package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.RankDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.RankEntity;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.RankDto;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankRepo extends DataRepo<RankDto, RankDao, RankEntity> implements DataRepoMethods {
    private final String TAG = RankRepo.class.getSimpleName();

    public RankRepo() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        dataDao = AppInstance.getInstance().getDatabase().rankDao();
    }

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getRanks()
                .enqueue(new Callback<List<RankDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<RankDto>> call,
                            @NonNull Response<List<RankDto>> res) {
                        if(res.body() != null && res.isSuccessful()) {
                            //apiRes.postValue(res.body());
                            Log.d(TAG, "Received RANK DATA");
                            dataDao = AppInstance.getInstance().getDatabase().rankDao();
                            addDisposableEvents(TAG, res.body(), RankEntity.class);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<RankDto>> call,
                            @NonNull Throwable t) {
                        apiRes.postValue(null);
                    }
                });
    }

    public Maybe<List<RankEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
    @Override
    public LiveData<List<RankDto>> getApiRes() { return apiRes; }
}
