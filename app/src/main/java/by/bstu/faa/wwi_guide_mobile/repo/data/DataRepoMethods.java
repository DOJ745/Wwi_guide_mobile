package by.bstu.faa.wwi_guide_mobile.repo.data;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseId;
import io.reactivex.Flowable;

public interface DataRepoMethods<T extends BaseId> {
    void callApi();
    //Flowable<List<T>> getEntitiesFromDB();
}
