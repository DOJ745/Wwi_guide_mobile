package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.SurveyDto;
import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyEntity;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyRepo extends DataRepo<SurveyDto, SurveyDao, SurveyEntity> implements DataRepoMethods {
    private final String TAG = SurveyRepo.class.getSimpleName();

    private EventsRepo eventsRepo;
    private ArmamentRepo armamentRepo;

    public SurveyRepo() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        dataDao = AppInstance.getInstance().getDatabase().surveyDao();
        armamentRepo = new ArmamentRepo();
        eventsRepo = new EventsRepo();
    }

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getSurveys()
                .enqueue(new Callback<List<SurveyDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<SurveyDto>> call,
                            @NonNull Response<List<SurveyDto>> res) {
                        if(res.body() != null && res.isSuccessful()) {
                            //apiRes.postValue(res.body());
                            Log.d(TAG, "Received SURVEY DATA");
                            addDisposableEvents(TAG, res.body(), SurveyEntity.class);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<SurveyDto>> call,
                            @NonNull Throwable t) {
                        apiRes.postValue(null);
                    }
                });
    }
    public Maybe<List<SurveyEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
}
