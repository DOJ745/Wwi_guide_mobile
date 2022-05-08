package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyAnswerDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyAnswerEntity;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.SurveyAnswerDto;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveysAnswersRepo extends DataRepo<SurveyAnswerDto, SurveyAnswerDao, SurveyAnswerEntity> implements DataRepoMethods {
    private final String TAG = SurveysAnswersRepo.class.getSimpleName();

    public SurveysAnswersRepo() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        dataDao = AppInstance.getInstance().getDatabase().surveyAnswerDao();
    }

    @Override
    public void callApi() { RetrofitService.getInstance()
            .getAppApi()
            .getSurveysAnswers()
            .enqueue(new Callback<List<SurveyAnswerDto>>() {
                @Override
                public void onResponse(
                        @NonNull Call<List<SurveyAnswerDto>> call,
                        @NonNull Response<List<SurveyAnswerDto>> res) {
                    if(res.body() != null && res.isSuccessful()) {
                        //apiRes.postValue(res.body());
                        Log.d(TAG, "Received ACHIEVEMENT DATA");
                        addDisposableEvents(TAG, res.body(), SurveyAnswerEntity.class);
                    }
                }
                @Override
                public void onFailure(
                        @NonNull Call<List<SurveyAnswerDto>> call,
                        @NonNull Throwable t) {
                    apiRes.postValue(null);
                }
            });
    }

    @Override
    Maybe<List<SurveyAnswerEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
}
