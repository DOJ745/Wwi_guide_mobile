package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.YearDto;
import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.EventDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.data.YearRepo;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.GetUserMethod;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.Getter;

public class YearViewModel extends ViewModel implements GetUserMethod {
    private final String TAG = YearViewModel.class.getSimpleName();
    private final EventDao eventDao;
    @Getter
    private final UserDao userDao;
    @Getter
    private YearRepo yearRepo;
    private LiveData<List<YearDto>> yearsDtoResponseLiveData;

    public YearViewModel() {
        eventDao = AppInstance.getInstance().getDatabase().eventDao();
        userDao = AppInstance.getInstance().getDatabase().userDao();
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);

        yearRepo = new YearRepo();
        yearsDtoResponseLiveData = yearRepo.getApiRes();
    }

    public void getElements(){ yearRepo.callApi(); }

    public LiveData<List<YearDto>> getElementsDtoResponseLiveData() { return yearsDtoResponseLiveData; }
    public Single<List<EventEntity>> getYearEvents(String yearId) { return eventDao.getYearEvents(yearId); }

    @Override
    public Maybe<UserEntity> getUserFromDB() { return userDao.getUser(); }
}
