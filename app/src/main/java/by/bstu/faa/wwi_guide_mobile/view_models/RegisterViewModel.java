package by.bstu.faa.wwi_guide_mobile.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.RankDto;
import by.bstu.faa.wwi_guide_mobile.repo.CountryRepo;
import by.bstu.faa.wwi_guide_mobile.repo.RankRepo;

public class RegisterViewModel extends AndroidViewModel implements ViewModelMethods<CountryDto> {

    private CountryRepo countryRepo;
    private LiveData<List<CountryDto>> countryDtoResponseLiveData;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        countryRepo = new CountryRepo();
        countryDtoResponseLiveData = countryRepo.getElementsLiveData();
    }

    public void getElements(TokenData token){ countryRepo.getElements(token); }

    public LiveData<List<CountryDto>> getElementsDtoResponseLiveData() { return countryDtoResponseLiveData; }
}
