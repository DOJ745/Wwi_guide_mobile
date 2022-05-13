package by.bstu.faa.wwi_guide_mobile;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import org.modelmapper.ModelMapper;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.data.UserRepo;

import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.GetUserMethod;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel implements GetUserMethod {
    private final String TAG = MainViewModel.class.getSimpleName();

    private UserDao userDao;
    private UserRepo userRepo;
    private UserDto userDto;

    public MainViewModel() {
        userDao = AppInstance.getInstance().getDatabase().userDao();
        userRepo = new UserRepo();

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

    @Override
    public Maybe<UserEntity> getUserFromDB() { return userDao.getUser(); }
}
