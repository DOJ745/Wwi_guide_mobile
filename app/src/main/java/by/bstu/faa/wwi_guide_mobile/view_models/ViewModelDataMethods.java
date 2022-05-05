package by.bstu.faa.wwi_guide_mobile.view_models;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface ViewModelDataMethods<T> {
    void init();
    void getElements();
    LiveData<List<T>> getElementsDtoResponseLiveData();
}
