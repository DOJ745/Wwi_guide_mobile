package by.bstu.faa.wwi_guide_mobile.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.data_objects.Token;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.YearDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YearRepo {

    private MutableLiveData<List<YearDto>> yearsDtoMutableLiveData;
    public  YearRepo() {
        yearsDtoMutableLiveData = new MutableLiveData<>();
    }

    public void getYears(Token token) {

        RetrofitService.getInstance()
                .getAppApi()
                .getYears("Bearer " + token.getToken())
                .enqueue(new Callback<List<YearDto>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<YearDto>> call,
                            @NonNull Response<List<YearDto>> response) {
                        if(response.body() != null) {
                            yearsDtoMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<YearDto>> call,
                            @NonNull Throwable t) {
                        yearsDtoMutableLiveData.postValue(null);
                    }
                });
    }

    public LiveData<List<YearDto>> getYearsLiveData() { return yearsDtoMutableLiveData; }
}
