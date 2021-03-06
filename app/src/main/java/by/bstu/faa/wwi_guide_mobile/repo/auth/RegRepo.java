package by.bstu.faa.wwi_guide_mobile.repo.auth;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.RegData;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.AppMsgResponseDto;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegRepo extends AuthRepo {
    private final String TAG = RegRepo.class.getSimpleName();

    public void regUser(RegData regData) {

        RetrofitService.getInstance()
                .getAppApi()
                .registerUser(regData)
                .enqueue(new Callback<AppMsgResponseDto>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<AppMsgResponseDto> call,
                            @NonNull Response<AppMsgResponseDto> response) {
                        if(response.body() != null && response.isSuccessful()) {
                            Log.d(TAG, "Receiving REG DATA");
                            appResponse.postValue(response.body());
                        }
                        if (response.errorBody()!= null && response.code() == CONSTANTS.HTTP_CODES.BAD_REQUEST){
                            // Deserializing AppMsgResponseDto from errorBody
                            Gson gson = new Gson();
                            Type type = new TypeToken<AppMsgResponseDto>(){}.getType();
                            AppMsgResponseDto errorResponse = gson.fromJson(response.errorBody().charStream(), type);
                            Log.d(TAG, "errorResponse:\n" + errorResponse.toString());
                            appResponse.postValue(errorResponse);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<AppMsgResponseDto> call,
                            @NonNull Throwable t) {
                        appResponse.postValue(null);
                    }
                });
    }
}
