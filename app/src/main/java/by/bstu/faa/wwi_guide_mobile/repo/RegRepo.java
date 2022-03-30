package by.bstu.faa.wwi_guide_mobile.repo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
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
                        if(response.body() != null && response.isSuccessful()) {
                            regResponse.postValue(response.body());
                        }
                        if (response.errorBody()!= null && response.code() == CONSTANTS.HTTP_CODES.BAD_REQUEST){
                            // Deserializing RegDto from errorBody
                            Gson gson = new Gson();
                            Type type = new TypeToken<RegDto>(){}.getType();
                            RegDto errorResponse = gson.fromJson(response.errorBody().charStream(), type);

                            Log.d(CONSTANTS.LOG_TAGS.REG_REPO, "errorResponse:\n" + errorResponse.toString());
                            regResponse.postValue(errorResponse);
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
