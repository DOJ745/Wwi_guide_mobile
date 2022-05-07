package by.bstu.faa.wwi_guide_mobile.repo.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseId;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.base_dto.IdDto;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import lombok.Setter;

public abstract class DataRepo<T extends IdDto, B extends BaseDao<C>, C extends BaseId> {
    protected final MutableLiveData<List<T>> apiRes;
    protected B dataDao;
    @Getter
    protected final CompositeDisposable mDisposable = new CompositeDisposable();
    @Getter@Setter
    private List<C> currentEntities;

    public DataRepo() { apiRes = new MutableLiveData<>(); }

    abstract Flowable<List<C>> getEntitiesFromDB();
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
        if(mappedNewData.size() < currentEntityData.size()) {
            for(int i = 0; i < newData.size(); i++) { currentEntityData.remove(mappedNewData.get(i)); }
            return dataDao.deleteMany(currentEntityData);
        }
        else return dataDao.insertMany(currentEntityData);
    }

    public void addDisposableEvents(String repoTag, List<T> resBody, Class<C> entityClass) {
        mDisposable.add(insertOrUpdateEntities(resBody, entityClass)
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
        );
    }
}
