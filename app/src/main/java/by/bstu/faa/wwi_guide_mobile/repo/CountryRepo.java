package by.bstu.faa.wwi_guide_mobile.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.Constants;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRepo extends BasicRepo<CountryDto>{
    @Override
    public void getElements(TokenData token) {

        RetrofitService.getInstance()
                .getAppApi()
                .getCountries(Constants.Values.BEARER + token.getToken())
                .enqueue(new Callback<List<CountryDto>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<CountryDto>> call,
                            @NonNull Response<List<CountryDto>> response) {
                        if(response.body() != null) {
                            elementsDtoMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<CountryDto>> call,
                            @NonNull Throwable t) {
                        elementsDtoMutableLiveData.postValue(null);
                    }
                });
    }

    @Override
    public LiveData<List<CountryDto>> getElementsLiveData() { return  elementsDtoMutableLiveData; }
}
