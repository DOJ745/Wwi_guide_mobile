package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import android.util.Log;

import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public interface DataMethods<T> extends GetUserMethods {
    default void updateUser(UserEntity user, UserDao userDao, String TAG) {
        userDao.insert(user).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() { Log.d(TAG, "User has been updated"); }
                    @Override
                    public void onError(Throwable e) { Log.d(TAG, "Error occurred\n" + e.getMessage()); }
                });
    }
    Single<T> getEntityDataById(String entityId);
}
