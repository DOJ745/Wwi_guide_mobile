package by.bstu.faa.wwi_guide_mobile.repo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.YearDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YearRepo extends DataRepo<YearDto> {
    private final String YEAR_REPO = "YEAR REPO";

    @Override
    public void getElements(TokenData token) {

        RetrofitService.getInstance()
                .getAppApi()
                .getYears(CONSTANTS.URLS.BEARER + token.getToken())
                .enqueue(new Callback<List<YearDto>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<YearDto>> call,
                            @NonNull Response<List<YearDto>> response) {
                        if(response.body() != null) {
                            Log.d(YEAR_REPO, "Receiving YEAR DATA");
                            elementsDtoMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<YearDto>> call,
                            @NonNull Throwable t) {
                        elementsDtoMutableLiveData.postValue(null);
                    }
                });
    }

    @Override
    public LiveData<List<YearDto>> getElementsLiveData() { return elementsDtoMutableLiveData; }
}
