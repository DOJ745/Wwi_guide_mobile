package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyQuestionDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyQuestionEntity;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.SurveyQuestionDto;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveysQuestionsRepo extends DataRepo<SurveyQuestionDto, SurveyQuestionDao, SurveyQuestionEntity> implements DataRepoMethods {
    private final String TAG = SurveysQuestionsRepo.class.getSimpleName();

    public SurveysQuestionsRepo() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        dataDao = AppInstance.getInstance().getDatabase().surveyQuestionDao();
    }

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getSurveysQuestions()
                .enqueue(new Callback<List<SurveyQuestionDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<SurveyQuestionDto>> call,
                            @NonNull Response<List<SurveyQuestionDto>> res) {
                        if(res.body() != null && res.isSuccessful()) {
                            //apiRes.postValue(res.body());
                            Log.d(TAG, "Received ACHIEVEMENT DATA");
                            addDisposableEvents(TAG, res.body(), SurveyQuestionEntity.class);
                        }
                    }
                    @Override
                    public void onFailure(
                            @NonNull Call<List<SurveyQuestionDto>> call,
                            @NonNull Throwable t) {
                        apiRes.postValue(null);
                    }
                });
    }

    @Override
    Maybe<List<SurveyQuestionEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
}
