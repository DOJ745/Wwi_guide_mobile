package by.bstu.faa.wwi_guide_mobile.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.data_objects.RegData;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.YearDto;
import by.bstu.faa.wwi_guide_mobile.repo.YearRepo;

public class YearViewModel extends AndroidViewModel {

    private YearRepo yearRepo;
    private LiveData<List<YearDto>> yearsDtoResponseLiveData;

    public YearViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        yearRepo = new YearRepo();
        yearsDtoResponseLiveData = yearRepo.getYearsLiveData();
    }

    public void getYears(TokenData token){ yearRepo.getYears(token); }

    public LiveData<List<YearDto>> getYearsDtoResponseLiveData() { return yearsDtoResponseLiveData; }
}