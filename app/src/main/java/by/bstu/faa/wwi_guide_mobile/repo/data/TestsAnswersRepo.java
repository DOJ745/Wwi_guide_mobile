package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.TestAnswerDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestAnswerEntity;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.TestAnswerDto;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestsAnswersRepo extends DataRepo<TestAnswerDto, TestAnswerDao, TestAnswerEntity> implements DataRepoMethods {
    private final String TAG = TestsAnswersRepo.class.getSimpleName();

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getTestsAnswers()
                .enqueue(new Callback<List<TestAnswerDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<TestAnswerDto>> call,
                            @NonNull Response<List<TestAnswerDto>> res) {
                        if(res.body() != null && res.isSuccessful()) {
                            //apiRes.postValue(res.body());
                            dataDao = AppInstance.getInstance().getDatabase().testAnswerDao();
                            Log.d(TAG, "Received ACHIEVEMENT DATA");
                            addDisposableEvents(TAG, res.body(), TestAnswerEntity.class);
                        }
                    }
                    @Override
                    public void onFailure(
                            @NonNull Call<List<TestAnswerDto>> call,
                            @NonNull Throwable t) {
                        apiRes.postValue(null);
                    }
                });
    }

    @Override
    Maybe<List<TestAnswerEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
}
