package by.bstu.faa.wwi_guide_mobile.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import by.bstu.faa.wwi_guide_mobile.data_objects.RegData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.RegDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegRepo {

    final MutableLiveData<RegDto> regResponse = new MutableLiveData<>();

    public void regUser(RegData regData) {

        RetrofitService.getInstance()
                .getAppApi()
                .registerUser(regData)
                .enqueue(new Callback<RegDto>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<RegDto> call,
                            @NonNull Response<RegDto> response) {
                        if(response.body() != null) {
                            regResponse.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<RegDto> call,
                            @NonNull Throwable t) {
                        regResponse.postValue(null);
                    }
                });
    }
    public LiveData<RegDto> getResponse() { return regResponse; }
}
