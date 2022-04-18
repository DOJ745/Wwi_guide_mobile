package by.bstu.faa.wwi_guide_mobile.view_models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.YearDto;
import by.bstu.faa.wwi_guide_mobile.repo.YearRepo;

public class YearViewModel extends ViewModel implements ViewModelDataMethods<YearDto> {

    private YearRepo yearRepo;
    private LiveData<List<YearDto>> yearsDtoResponseLiveData;

    public YearViewModel() {
        Log.d("YearViewModel", CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    public void init() {
        yearRepo = new YearRepo();
        yearsDtoResponseLiveData = yearRepo.getElementsLiveData();
    }

    public void getElements(TokenData token){ yearRepo.getElements(token); }
    public LiveData<List<YearDto>> getElementsDtoResponseLiveData() { return yearsDtoResponseLiveData; }
}
