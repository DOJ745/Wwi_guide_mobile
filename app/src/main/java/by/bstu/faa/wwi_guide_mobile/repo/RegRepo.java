package by.bstu.faa.wwi_guide_mobile.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import by.bstu.faa.wwi_guide_mobile.data_objects.RegData;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegRepo {

    MutableLiveData<String> regResponse;

    public void regUser(RegData regData) {

        RetrofitService.getInstance()
                .getAppApi()
                .registerUser(regData)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<String> call,
                            @NonNull Response<String> response) {
                        if(response.body() != null) {
                            regResponse.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<String> call,
                            @NonNull Throwable t) {
                        regResponse.postValue(null);
                    }
                });
    }
    public LiveData<String> getResponse() { return regResponse; }
}
