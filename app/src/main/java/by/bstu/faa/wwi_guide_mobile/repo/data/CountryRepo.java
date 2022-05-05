package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRepo extends DataRepo<CountryDto> {
    private final String COUNTRY_REPO = "COUNTRY REPO";

    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getCountries()
                .enqueue(new Callback<List<CountryDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<CountryDto>> call,
                            @NonNull Response<List<CountryDto>> response) {
                        if(response.body() != null) {
                            Log.d(COUNTRY_REPO, "Received COUNTRY DATA");
                            apiRes.postValue(response.body());
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

    public LiveData<List<CountryDto>> getApiRes() { return apiRes; }
}
