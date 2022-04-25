package by.bstu.faa.wwi_guide_mobile.view_models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.AchievementDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.YearDto;
import by.bstu.faa.wwi_guide_mobile.database.dao.AchievementDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.AchievementRepo;
import by.bstu.faa.wwi_guide_mobile.repo.YearRepo;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class MainViewModel extends ViewModel {
    private final String MAIN_VIEW_MODEL = "MAIN VIEW MODEL";
    private final UserDao userDao;
    private final AchievementDao achievementDao;

    private AchievementRepo achievementRepo;
    private LiveData<List<AchievementDto>> achievementDtoResponseLiveData;

    public MainViewModel() {
        userDao = AppInstance.getInstance().getDatabase().userDao();
        achievementDao = AppInstance.getInstance().getDatabase().achievementDao();
    }

    public void init() {
        achievementRepo = new AchievementRepo();
        achievementDtoResponseLiveData = achievementRepo.getElementsLiveData();
    }

    public void getAchievement(TokenData token){ achievementRepo.getElements(token); }
    public LiveData<List<AchievementDto>> getAchievementDtoResponseLiveData() { return achievementDtoResponseLiveData; }

    public Flowable<List<UserEntity>> getUser() { return userDao.getUser(); }
    public Flowable<List<AchievementEntity>> getAchievements() { return achievementDao.getAll(); }

    public void insertOrUpdateAchievements(List<AchievementDto> data) {
        ModelMapper modelMapper = new ModelMapper();
        List<AchievementEntity> temp = new ArrayList<>();
        for (AchievementDto achievementDto: data) {
            AchievementEntity achievementEntity = modelMapper.map(achievementDto, AchievementEntity.class);
            Log.d(MAIN_VIEW_MODEL, achievementEntity.getDescription() + "\n" + achievementEntity.getName());
        }

        //return achievementDao.insertMany(temp);
    }
}