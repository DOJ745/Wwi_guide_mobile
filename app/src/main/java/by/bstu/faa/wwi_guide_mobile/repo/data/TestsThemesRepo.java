package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.TestThemeDto;
import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.TestThemeDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestThemeEntity;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestsThemesRepo extends DataRepo<TestThemeDto, TestThemeDao, TestThemeEntity> implements DataRepoMethods {
    private final String TAG = TestThemeEntity.class.getSimpleName();

    public TestsThemesRepo() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        dataDao = AppInstance.getInstance().getDatabase().testThemeDao();
    }

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getTestsThemes()
                .enqueue(new Callback<List<TestThemeDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<TestThemeDto>> call,
                            @NonNull Response<List<TestThemeDto>> res) {
                        if(res.body() != null && res.isSuccessful()) {
                            //apiRes.postValue(res.body());
                            Log.d(TAG, "Received ACHIEVEMENT DATA");
                            addDisposableEvents(TAG, res.body(), TestThemeEntity.class);
                        }
                    }
                    @Override
                    public void onFailure(
                            @NonNull Call<List<TestThemeDto>> call,
                            @NonNull Throwable t) {
                        apiRes.postValue(null);
                    }
                });
    }

    @Override
    Maybe<List<TestThemeEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
}
