package by.bstu.faa.wwi_guide_mobile.view_models;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;

public interface ViewModelDataMethods<T> {
    void init();
    void getElements(TokenData token);
    LiveData<List<T>> getElementsDtoResponseLiveData();
}
