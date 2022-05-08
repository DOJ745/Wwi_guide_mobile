package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.ArmamentDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.ArmamentEntity;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.ArmamentDto;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArmamentRepo extends DataRepo<ArmamentDto, ArmamentDao, ArmamentEntity> implements DataRepoMethods {
    private final String TAG = ArmamentRepo.class.getSimpleName();

    public ArmamentRepo() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        dataDao = AppInstance.getInstance().getDatabase().armamentDao();
    }

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getArmament()
                .enqueue(new Callback<List<ArmamentDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<ArmamentDto>> call,
                            @NonNull Response<List<ArmamentDto>> res) {
                        if(res.body() != null && res.isSuccessful()) {
                            //apiRes.postValue(res.body());
                            Log.d(TAG, "Received ACHIEVEMENT DATA");
                            addDisposableEvents(TAG, res.body(), ArmamentEntity.class);
                        }
                    }
                    @Override
                    public void onFailure(
                            @NonNull Call<List<ArmamentDto>> call,
                            @NonNull Throwable t) {
                        apiRes.postValue(null);
                    }
                });
    }

    @Override
    public Maybe<List<ArmamentEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
}
