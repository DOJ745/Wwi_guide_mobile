package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRepo {
    private final String COUNTRY_REPO = "COUNTRY REPO";

    protected MutableLiveData<List<CountryDto>> elementsDtoResponse;
    public CountryRepo() { elementsDtoResponse = new MutableLiveData<>(); }

    public void getElements() {

        RetrofitService.getInstance()
                .getAppApi()
                .getCountries()
                .enqueue(new Callback<List<CountryDto>>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<List<CountryDto>> call,
                            @NonNull Response<List<CountryDto>> response) {
                        if(response.body() != null) {
                            Log.d(COUNTRY_REPO, "Receiving COUNTRY DATA");
                            elementsDtoResponse.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<CountryDto>> call,
                            @NonNull Throwable t) {
                        elementsDtoResponse.postValue(null);
                    }
                });
    }

    public LiveData<List<CountryDto>> getResponse() { return elementsDtoResponse; }
}
