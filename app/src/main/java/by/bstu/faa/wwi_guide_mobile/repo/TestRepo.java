package by.bstu.faa.wwi_guide_mobile.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import by.bstu.faa.wwi_guide_mobile.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestRepo {

    private MutableLiveData<TokenData> tokenLiveData;

    public  TestRepo() {
        tokenLiveData = new MutableLiveData<>();
    }

    public void getToken(LoginData data){

        RetrofitService.getInstance()
                .getAppApi()
                .getToken(data)
                .enqueue(new Callback<TokenData>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<TokenData> call,
                            @NonNull Response<TokenData> response) {
                        if(response.body() != null) {
                            tokenLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<TokenData> call,
                            @NonNull Throwable t) {
                        tokenLiveData.postValue(null);
                    }
                });
    }

    public LiveData<TokenData> getTokenLiveData() { return tokenLiveData; }
}
