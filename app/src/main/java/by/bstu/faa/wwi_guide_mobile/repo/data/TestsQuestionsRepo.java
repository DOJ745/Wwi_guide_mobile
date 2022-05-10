package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.TestQuestionDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestQuestionEntity;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.TestQuestionDto;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestsQuestionsRepo extends DataRepo<TestQuestionDto, TestQuestionDao, TestQuestionEntity> implements DataRepoMethods {
    private final String TAG = TestsQuestionsRepo.class.getSimpleName();

    public TestsQuestionsRepo() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        dataDao = AppInstance.getInstance().getDatabase().testQuestionDao();
    }

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getTestsQuestions()
                .enqueue(new Callback<List<TestQuestionDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<TestQuestionDto>> call,
                            @NonNull Response<List<TestQuestionDto>> res) {
                        if(res.body() != null && res.isSuccessful()) {
                            //apiRes.postValue(res.body());
                            dataDao = AppInstance.getInstance().getDatabase().testQuestionDao();
                            Log.d(TAG, "Received tests questions");
                            addDisposableEvents(TAG, res.body(), TestQuestionEntity.class);
                        }
                    }
                    @Override
                    public void onFailure(
                            @NonNull Call<List<TestQuestionDto>> call,
                            @NonNull Throwable t) {
                        apiRes.postValue(null);
                    }
                });
    }

    @Override
    Maybe<List<TestQuestionEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
}
