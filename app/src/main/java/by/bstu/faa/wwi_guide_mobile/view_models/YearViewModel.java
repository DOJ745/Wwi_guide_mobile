package by.bstu.faa.wwi_guide_mobile.view_models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.YearDto;
import by.bstu.faa.wwi_guide_mobile.database.dao.EventDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.YearRepo;
import io.reactivex.Flowable;

public class YearViewModel extends ViewModel implements ViewModelDataMethods<YearDto> {
    private final String YEAR_VIEW_MODEL = "YEAR VIEW MODEL";
    private final EventDao eventDao;
    private YearRepo yearRepo;
    private LiveData<List<YearDto>> yearsDtoResponseLiveData;

    public YearViewModel() {
        eventDao = AppInstance.getInstance().getDatabase().eventDao();
        Log.d(YEAR_VIEW_MODEL, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    public void init() {
        yearRepo = new YearRepo();
        yearsDtoResponseLiveData = yearRepo.getElementsLiveData();
    }

    public void getElements(TokenData token){ yearRepo.getElements(token); }

    public LiveData<List<YearDto>> getElementsDtoResponseLiveData() { return yearsDtoResponseLiveData; }
    public Flowable<List<EventEntity>> getYearEvents(String yearId) { return eventDao.getYearEvents(yearId); }
}
