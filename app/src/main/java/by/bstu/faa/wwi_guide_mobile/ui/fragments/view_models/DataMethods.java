package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import io.reactivex.Maybe;

public interface DataMethods {
    Maybe<UserEntity> getUserFromDB();
    //void getElements();
    //LiveData<List<T>> getElementsDtoResponseLiveData();
}
