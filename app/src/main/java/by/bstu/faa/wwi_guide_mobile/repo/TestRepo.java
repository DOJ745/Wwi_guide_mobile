package by.bstu.faa.wwi_guide_mobile.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import by.bstu.faa.wwi_guide_mobile.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.data_objects.Token;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestRepo {

    private MutableLiveData<Token> tokenLiveData;

    public  TestRepo() {
        tokenLiveData = new MutableLiveData<>();
    }

    public void getToken(LoginData data){

        RetrofitService.getInstance()
                .getAppApi()
                .getToken(data)
                .enqueue(new Callback<Token>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<Token> call,
                            @NonNull Response<Token> response) {
                        if(response.body() != null) {
                            tokenLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<Token> call,
                            @NonNull Throwable t) {
                        tokenLiveData.postValue(null);
                    }
                });
    }

    public LiveData<Token> getTokenLiveData() { return tokenLiveData; }
}
