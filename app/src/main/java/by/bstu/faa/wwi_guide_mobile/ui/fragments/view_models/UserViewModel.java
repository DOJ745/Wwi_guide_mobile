package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.RankDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.data.UserRepo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import lombok.Getter;
import lombok.Setter;

public class UserViewModel extends ViewModel {
    private final String TAG = UserViewModel.class.getSimpleName();

    @Getter
    private final RankDao rankDao;
    @Getter
    private final UserRepo userRepo;
    @Getter@Setter
    private UserEntity userEntity;
    @Getter@Setter
    private String userRankImg;

    public UserViewModel() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        userEntity = new UserEntity();
        userRepo = new UserRepo();
        rankDao = AppInstance.getInstance().getDatabase().rankDao();
        //getUserDataFromDB();
    }

    public String getUserImgRankFromDB() {

        /*rankDao.getRankImg(getUserEntity().getRankId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        setUserRankImg(s);
                    }
                    @Override
                    public void onError(Throwable e) { Log.d(TAG, "onError\n" + e.getMessage()); }
                });*/

        return rankDao.getRankImg(this.userEntity.getRankId());
    }

    public void getUserDataFromDB() {
        /*CompositeDisposable mDisposable = new CompositeDisposable();
        mDisposable.add(userRepo.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            this.userEntity = data.get(0);
                            Log.d(TAG, "DB: user data - " + this.userEntity.getRankId());
                }, throwable -> Log.e(TAG, "Unable to get user", throwable))
        );*/

        //mDisposable.clear();
        /*DisposableSubscriber<List<UserEntity>> disposableSubscriber =
                userRepo.getUser().subscribeWith(new DisposableSubscriber<List<UserEntity>>() {
            @Override
            public void onStart() { Log.d(TAG, "Start getting user data"); }
            @Override
            public void onNext(List<UserEntity> data) {
                Log.d(TAG, "onNext");
                userEntity = data.get(0);
            }
            @Override
            public void onError(Throwable t) { t.printStackTrace(); }
            @Override
            public void onComplete() { Log.d(TAG, "done getting user"); }
        });*/
    }
}
