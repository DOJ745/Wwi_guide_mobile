package by.bstu.faa.wwi_guide_mobile.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.RankDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankRepo extends DataRepo<RankDto> {

    @Override
    public void getElements(TokenData token) {

        RetrofitService.getInstance()
                .getAppApi()
                .getRanks(CONSTANTS.URLS.BEARER + token.getToken())
                .enqueue(new Callback<List<RankDto>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<RankDto>> call,
                            @NonNull Response<List<RankDto>> response) {
                        if(response.body() != null) {
                            elementsDtoMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<RankDto>> call,
                            @NonNull Throwable t) {
                        elementsDtoMutableLiveData.postValue(null);
                    }
                });
    }

    @Override
    public LiveData<List<RankDto>> getElementsLiveData() { return  elementsDtoMutableLiveData; }
}
