package by.bstu.faa.wwi_guide_mobile.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.AchievementDto;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.RankDto;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.database.dao.AchievementDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.CountryDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.RankDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.CountryEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.RankEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.auth.LoginRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.AchievementRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.CountryRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.RankRepo;
import by.bstu.faa.wwi_guide_mobile.view_models.auth.LoginUserMethods;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import lombok.Getter;
import lombok.Setter;

public class SplashViewModel extends ViewModel implements LoginUserMethods {
    private final String SPLASH_VIEW_MODEL = "SPLASH VIEW MODEL";

    private final UserDao userDao;
    private final AchievementDao achievementDao;
    private final RankDao rankDao;
    private final CountryDao countryDao;

    private final RankRepo rankRepo;
    private final AchievementRepo achievementRepo;
    private final LoginRepo loginRepo;
    private final CountryRepo countryRepo;
    private final LiveData<List<AchievementDto>> achievementRepoResponse;
    private final LiveData<List<CountryDto>> countriesRepoResponse;
    private final LiveData<List<RankDto>> rankRepoResponse;
    private final LiveData<UserDto> loginRepoResponse;

    @Getter@Setter
    private List<AchievementDto> resAchievements;
    @Getter@Setter
    private List<AchievementEntity> currentAchievements;

    public SplashViewModel() {
        userDao = AppInstance.getInstance().getDatabase().userDao();
        achievementDao = AppInstance.getInstance().getDatabase().achievementDao();
        rankDao = AppInstance.getInstance().getDatabase().rankDao();
        countryDao = AppInstance.getInstance().getDatabase().countryDao();

        achievementRepo = new AchievementRepo();
        loginRepo = new LoginRepo();
        rankRepo = new RankRepo();
        countryRepo = new CountryRepo();

        achievementRepoResponse = achievementRepo.getApiRes();
        loginRepoResponse = loginRepo.getUserResponse();
        rankRepoResponse = rankRepo.getApiRes();
        countriesRepoResponse = countryRepo.getApiRes();
    }

    public void getRanks() { rankRepo.callApi(); }
    public void getAchievement(){ achievementRepo.callApi(); }
    public void getCountries() { countryRepo.callApi(); }

    public LiveData<List<CountryDto>> getCountriesRepoResponse() { return countriesRepoResponse; }
    public LiveData<List<AchievementDto>> getAchievementsRepoResponse() { return achievementRepoResponse; }
    public LiveData<List<RankDto>> getRanksRepoResponse() { return rankRepoResponse; }
    public LiveData<UserDto> getLoginRepoResponse() { return loginRepoResponse; }

    public Flowable<List<UserEntity>> getUserFromDB() { return userDao.getUser(); }
    public Flowable<List<AchievementEntity>> getAchievementsFromDB() { return achievementDao.getAll(); }

    public void loginUser(LoginData loginData) { loginRepo.loginUser(loginData); }

    @Override
    public Completable insertOrUpdateUser(UserDto data) {
        UserEntity loggedUser = new UserEntity();

        StringBuilder tempRoles = new StringBuilder();
        StringBuilder tempAchievements = new StringBuilder();

        loggedUser.setLogin(data.getLogin());
        loggedUser.setPassword(data.getPassword());
        loggedUser.setCountryId(data.getCountryId());
        loggedUser.setRankId(data.getRankId());
        loggedUser.setScore(data.getScore());

        for (String item : data.getRoles()) {
            tempRoles.append(CONSTANTS.APP_DATABASE.ELEMENT_DIVIDER).append(item);
        }
        loggedUser.setRoles(tempRoles.toString());

        if(data.getAchievements() != null && !data.getAchievements().isEmpty()) {
            for (String item: data.getAchievements()) {
                tempAchievements.append(CONSTANTS.APP_DATABASE.ELEMENT_DIVIDER).append(item);
            }
            loggedUser.setAchievements(tempAchievements.toString());
        }
        else loggedUser.setAchievements("none");

        return userDao.insert(loggedUser);
    }

    public Completable insertOrUpdateAchievements(List<AchievementDto> data) {
        ModelMapper modelMapper = new ModelMapper();
        List<AchievementEntity> temp = new ArrayList<>();
        for (AchievementDto dto: data) {
            AchievementEntity entity = modelMapper.map(dto, AchievementEntity.class);
            temp.add(entity);
        }
        return achievementDao.insertMany(temp);
    }

    public Completable deleteOldAchievements(List<AchievementEntity> currentData, List<AchievementDto> newData) {
        ModelMapper modelMapper = new ModelMapper();
        List<AchievementEntity> mappedNewData = new ArrayList<>();
        for (AchievementDto dto: newData) {
            AchievementEntity entity = modelMapper.map(dto, AchievementEntity.class);
            mappedNewData.add(entity);
        }
        if(currentData == null) {
            return achievementDao.insertMany(mappedNewData);
        }
        if(mappedNewData.size() < currentData.size()) {
            for(int i = 0; i < newData.size(); i++) {
                currentData.remove(mappedNewData.get(i));
            }
        }
        return achievementDao.deleteMany(currentData);
    }

    public Completable insertOrUpdateRanks(List<RankDto> data) {
        ModelMapper modelMapper = new ModelMapper();
        List<RankEntity> temp = new ArrayList<>();
        for (RankDto dto: data) {
            RankEntity entity = modelMapper.map(dto, RankEntity.class);
            temp.add(entity);
        }
        return rankDao.insertMany(temp);
    }

    public Completable insertOrUpdateCountries(List<CountryDto> data) {
        ModelMapper modelMapper = new ModelMapper();
        List<CountryEntity> temp = new ArrayList<>();
        for (CountryDto dto: data) {
            CountryEntity entity = modelMapper.map(dto, CountryEntity.class);
            temp.add(entity);
        }
        return countryDao.insertMany(temp);
    }
}