package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyQuestionDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyQuestionEntity;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.SurveyQuestionDto;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveysQuestionsRepo extends DataRepo<SurveyQuestionDto, SurveyQuestionDao, SurveyQuestionEntity> implements DataRepoMethods {
    private final String TAG = SurveysQuestionsRepo.class.getSimpleName();
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
                            dataDao = AppInstance.getInstance().getDatabase().surveyQuestionDao();
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
