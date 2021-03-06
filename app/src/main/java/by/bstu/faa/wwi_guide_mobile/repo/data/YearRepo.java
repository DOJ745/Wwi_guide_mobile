package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.YearDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.YearEntity;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.YearDto;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YearRepo extends DataRepo<YearDto, YearDao, YearEntity> implements DataRepoMethods {
    private final String TAG = YearRepo.class.getSimpleName();

    public YearRepo() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        dataDao = AppInstance.getInstance().getDatabase().yearDao();
    }

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getYears()
                .enqueue(new Callback<List<YearDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<YearDto>> call,
                            @NonNull Response<List<YearDto>> res) {
                        if(res.body() != null) {
                            //apiRes.postValue(res.body());
                            addDisposableEvents(TAG, res.body(), YearEntity.class);
                            Log.d(TAG, "Received YEAR DATA");
                        }
                    }
                    @Override
                    public void onFailure(
                            @NonNull Call<List<YearDto>> call,
                            @NonNull Throwable t) {
                        apiRes.postValue(null);
                    }
                });
    }

    public Maybe<List<YearEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
    @Override
    public LiveData<List<YearDto>> getApiRes() { return apiRes; }
}
