package by.bstu.faa.wwi_guide_mobile.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import by.bstu.faa.wwi_guide_mobile.data_objects.dto.YearDto;
import by.bstu.faa.wwi_guide_mobile.repo.YearRepo;

public class YearViewModel extends AndroidViewModel {

    private YearRepo yearRepo;
    private LiveData<YearDto> yearDtoResponseLiveData;

    public YearViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        yearRepo = new YearRepo();
        yearDtoResponseLiveData = yearRepo.getYearLiveData();
    }

    public LiveData<YearDto> getYearDtoResponseLiveData() { return yearDtoResponseLiveData; }
}
