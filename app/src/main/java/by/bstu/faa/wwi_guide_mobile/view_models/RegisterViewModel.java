package by.bstu.faa.wwi_guide_mobile.view_models;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.Constants;
import by.bstu.faa.wwi_guide_mobile.data_objects.RegData;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.RegDto;
import by.bstu.faa.wwi_guide_mobile.repo.CountryRepo;
import by.bstu.faa.wwi_guide_mobile.repo.RegRepo;

public class RegisterViewModel extends AndroidViewModel {

    private CountryRepo countryRepo;
    private RegRepo regRepo;

    private LiveData<List<CountryDto>> countryDtoResponse;
    private LiveData<RegDto> regRepoResponse;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        Log.d(Constants.Values.LOG_TAG_REG_VIEW_MODEL, "init");
        countryRepo = new CountryRepo();
        regRepo = new RegRepo();

        countryDtoResponse = countryRepo.getResponse();
        regRepoResponse = regRepo.getResponse();
    }

    public void getCountryResponse() {
        Log.d(Constants.Values.LOG_TAG_REG_VIEW_MODEL, "getCountryResponse");
        countryRepo.getElements();
    }
    public void regUser(RegData regData) { regRepo.regUser(regData); }

    public LiveData<List<CountryDto>> getElementsDtoResponseLiveData() { return countryDtoResponse; }
    public LiveData<RegDto> getRegRepoResponse() { return regRepoResponse; }
}
