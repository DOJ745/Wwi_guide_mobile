package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.RankDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.RankEntity;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.RankDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankRepo extends DataRepo<RankDto, RankDao, RankEntity> implements DataRepoMethods<RankEntity> {
    private final String RANK_REPO = "RANK REPO";

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getRanks()
                .enqueue(new Callback<List<RankDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<RankDto>> call,
                            @NonNull Response<List<RankDto>> response) {
                        if(response.body() != null && response.isSuccessful()) {
                            Log.d(RANK_REPO, "Received RANK DATA");
                            //apiRes.postValue(response.body());
                            dataDao = AppInstance.getInstance().getDatabase().rankDao();
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

    @Override
    public Flowable<List<RankEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
    @Override
    public LiveData<List<RankDto>> getApiRes() { return apiRes; }
}
