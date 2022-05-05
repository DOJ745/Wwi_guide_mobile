package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.YearDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YearRepo extends DataRepo<YearDto> {
    private final String YEAR_REPO = "YEAR REPO";

    @Override
    public void callApi() {
        RetrofitService.getInstance()
                .getAppApi()
                .getYears()
                .enqueue(new Callback<List<YearDto>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<YearDto>> call,
                            @NonNull Response<List<YearDto>> response) {
                        if(response.body() != null) {
                            apiRes.postValue(response.body());
                            Log.d(YEAR_REPO, "Received YEAR DATA");
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<YearDto>> call,
                            @NonNull Throwable t) {
                        apiRes.postValue(null);
                    }
                });
    }

    @Override
    public LiveData<List<YearDto>> getApiRes() { return apiRes; }
}
