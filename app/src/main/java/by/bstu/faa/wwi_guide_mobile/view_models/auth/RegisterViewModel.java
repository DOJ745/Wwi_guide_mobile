package by.bstu.faa.wwi_guide_mobile.view_models.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.RegData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.AppMsgResponseDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.repo.CountryRepo;
import by.bstu.faa.wwi_guide_mobile.repo.RegRepo;

public class RegisterViewModel extends ViewModel {
    private final String REG_VIEW_MODEL = "REG VIEW MODEL";

    private CountryRepo countryRepo;
    private RegRepo regRepo;

    private LiveData<List<CountryDto>> countryDtoResponse;
    private LiveData<AppMsgResponseDto> regRepoResponse;

    public RegisterViewModel() {
        Log.d(REG_VIEW_MODEL, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    public void init() {
        Log.d(REG_VIEW_MODEL, "init");
        countryRepo = new CountryRepo();
        regRepo = new RegRepo();

        countryDtoResponse = countryRepo.getResponse();
        regRepoResponse = regRepo.getResponse();
    }

    public void getCountryResponse() {
        Log.d(REG_VIEW_MODEL, "getCountryResponse");
        countryRepo.getElements();
    }
    public void regUser(RegData regData) { regRepo.regUser(regData); }

    public LiveData<List<CountryDto>> getElementsDtoResponseLiveData() { return countryDtoResponse; }
    public LiveData<AppMsgResponseDto> getRegRepoResponse() { return regRepoResponse; }
}
