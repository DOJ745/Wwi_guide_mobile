package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseEntityId;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.base_dto.IdDto;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class DataRepo<T extends IdDto, B extends BaseDao<C>, C extends BaseEntityId> {
    protected final MutableLiveData<List<T>> apiRes;
    protected B dataDao;

    public DataRepo() { apiRes = new MutableLiveData<>(); }

    abstract Maybe<List<C>> getEntitiesFromDB();
    LiveData<List<T>> getApiRes() { return apiRes; }

    public Completable insertOrUpdateEntities(List<T> dataDto, Class<C> entityClass) {
        ModelMapper modelMapper = new ModelMapper();
        List<C> temp = new ArrayList<>();
        for (T dto: dataDto) {
            C entity = modelMapper.map(dto, (Type) entityClass);
            temp.add(entity);
        }
        return dataDao.insertMany(temp);
    }

    public Completable deleteOldEntities(List<C> currentEntityData, List<T> newData, Class<C> entityClass) {
        ModelMapper modelMapper = new ModelMapper();
        List<C> mappedNewData = new ArrayList<>();
        for (T dto: newData) {
            C entity = modelMapper.map(dto, (Type) entityClass);
            mappedNewData.add(entity);
        }
        if(currentEntityData == null) { return dataDao.insertMany(mappedNewData); }
        else {
            for(int i = 0; i < newData.size(); i++) { currentEntityData.remove(mappedNewData.get(i)); }
            return dataDao.deleteMany(currentEntityData);
        }
    }

    public void addDisposableEvents(String repoTag, List<T> resBody, Class<C> entityClass) {
        /*mDisposable.add(insertOrUpdateEntities(resBody, entityClass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // On complete
                        () -> {
                            Log.d(repoTag, "DB: Items has been written into database");
                            mDisposable.add(getEntitiesFromDB()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            // On complete
                                            data -> {
                                                Log.d(repoTag, "DB: Received current items");
                                                setCurrentEntities(data);

                                                mDisposable.add(deleteOldEntities(getCurrentEntities(), resBody, entityClass)
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(
                                                                // On complete
                                                                () -> Log.d(repoTag, "DB: Deleted old items"),
                                                                // On error
                                                                err -> Log.e(repoTag, "Unable to delete items", err))
                                                );
                                            },
                                            // On error
                                            err -> Log.e(repoTag, "Unable to get items", err))
                            );
                        },
                        // On error
                        err -> Log.e(repoTag, "Unable to insert items", err))
        );*/

        insertOrUpdateEntities(resBody, entityClass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(repoTag, "DB: onComplete inserting and updating items");

                        getEntitiesFromDB()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableMaybeObserver<List<C>>() {
                                    @Override
                                    public void onSuccess(List<C> data) {
                                        Log.d(repoTag, "DB: Received current items");

                                        deleteOldEntities(data, resBody, entityClass)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new DisposableCompletableObserver() {
                                                    @Override
                                                    public void onComplete() { Log.d(repoTag, "DB: Deleted old items"); }
                                                    @Override
                                                    public void onError(Throwable e) {
                                                        Log.e(repoTag, "DB: Error occurred!\n" + e.getMessage());
                                                    }
                                                });
                                    }
                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(repoTag, "DB: Error occurred!\n" + e.getMessage());
                                    }
                                    @Override
                                    public void onComplete() { Log.d(repoTag, "onComplete getting data"); }
                                });
                    }
                    @Override
                    public void onError(Throwable e) { Log.e(repoTag, "DB: Error occurred!\n" + e.getMessage()); }
                });
    }
}
