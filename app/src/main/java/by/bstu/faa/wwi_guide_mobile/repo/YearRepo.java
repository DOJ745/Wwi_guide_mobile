package by.bstu.faa.wwi_guide_mobile.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import by.bstu.faa.wwi_guide_mobile.data_objects.Token;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.YearDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YearRepo {

    private MutableLiveData<YearDto> yearDtoMutableLiveData;
    public  YearRepo() {
        yearDtoMutableLiveData = new MutableLiveData<>();
    }

    public void getYears(Token token) {

        RetrofitService.getInstance()
                .getAppApi()
                .getYears("Bearer " + token.getToken())
                .enqueue(new Callback<YearDto>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<YearDto> call,
                            @NonNull Response<YearDto> response) {
                        if(response.body() != null) {
                            yearDtoMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<YearDto> call,
                            @NonNull Throwable t) {
                        yearDtoMutableLiveData.postValue(null);
                    }
                });
    }

    public LiveData<YearDto> getYearLiveData() { return yearDtoMutableLiveData; }
}
