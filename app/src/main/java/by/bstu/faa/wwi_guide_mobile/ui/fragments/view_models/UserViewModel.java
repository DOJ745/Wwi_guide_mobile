package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import org.modelmapper.ModelMapper;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.AchievementDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.RankDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.RankEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.data.UserRepo;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import lombok.Setter;

public class UserViewModel extends ViewModel implements GetUserMethod {
    private final String TAG = UserViewModel.class.getSimpleName();

    @Getter
    private final RankDao rankDao;
    @Getter
    private final UserDao userDao;
    @Getter
    private final AchievementDao achievementDao;
    @Getter
    private final UserRepo userRepo;
    @Getter@Setter
    private UserEntity userEntity;
    private UserDto userDto;

    public UserViewModel() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        userRepo = new UserRepo();
        rankDao = AppInstance.getInstance().getDatabase().rankDao();
        userDao = AppInstance.getInstance().getDatabase().userDao();
        achievementDao = AppInstance.getInstance().getDatabase().achievementDao();

        getUserFromDB().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<UserEntity>() {
                    @Override
                    public void onSuccess(UserEntity userEntity) {
                        ModelMapper mapper = new ModelMapper();
                        userDto = mapper.map(userEntity, UserDto.class);
                        Log.d(TAG, "Mapped user dto: " + userDto.toString());
                    }
                    @Override
                    public void onError(Throwable e) { }
                    @Override
                    public void onComplete() { }
                });
    }

    public void updateUserReq(String token){ userRepo.updateUserInfo(token, this.userDto); }
    public Single<List<RankEntity>> getRanksByCountryId(String id) { return rankDao.getRanksByCountryId(id); }
    public Maybe<UserEntity> getUserFromDB() { return userDao.getUser(); }
    public Completable logoutUser(UserEntity userEntity) { return userDao.delete(userEntity); }
}
