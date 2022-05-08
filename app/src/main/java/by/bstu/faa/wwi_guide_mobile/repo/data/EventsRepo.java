package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.EventDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.EventDto;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsRepo extends DataRepo<EventDto, EventDao, EventEntity> implements DataRepoMethods {
    private final String TAG = EventsRepo.class.getSimpleName();

    public EventsRepo() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        dataDao = AppInstance.getInstance().getDatabase().eventDao();
    }

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getEvents()
                .enqueue(new Callback<List<EventDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<EventDto>> call,
                            @NonNull Response<List<EventDto>> res) {
                        if(res.body() != null && res.isSuccessful()) {
                            //apiRes.postValue(res.body());
                            Log.d(TAG, "Received ACHIEVEMENT DATA");
                            addDisposableEvents(TAG, res.body(), EventEntity.class);
                        }
                    }
                    @Override
                    public void onFailure(
                            @NonNull Call<List<EventDto>> call,
                            @NonNull Throwable t) {
                        apiRes.postValue(null);
                    }
                });
    }

    @Override
    Maybe<List<EventEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
}
