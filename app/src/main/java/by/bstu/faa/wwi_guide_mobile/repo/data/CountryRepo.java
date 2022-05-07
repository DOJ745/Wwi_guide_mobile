package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.CountryDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.CountryEntity;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRepo extends DataRepo<CountryDto, CountryDao, CountryEntity> implements DataRepoMethods {
    private final String TAG = "COUNTRY REPO";

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getCountries()
                .enqueue(new Callback<List<CountryDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<CountryDto>> call,
                            @NonNull Response<List<CountryDto>> res) {
                        if(res.body() != null) {
                            //apiRes.postValue(response.body());
                            Log.d(TAG, "Received COUNTRY DATA");
                            dataDao = AppInstance.getInstance().getDatabase().countryDao();
                            addDisposableEvents(TAG, res.body(), CountryEntity.class);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<CountryDto>> call,
                            @NonNull Throwable t) {
                        apiRes.postValue(null);
                    }
                });
    }

    public Maybe<List<CountryEntity>> getEntitiesFromDB() { return dataDao.getAll(); }
    @Override
    public LiveData<List<CountryDto>> getApiRes() { return apiRes; }
}
