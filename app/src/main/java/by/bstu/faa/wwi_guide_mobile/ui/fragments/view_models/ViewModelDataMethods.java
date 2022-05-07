package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface ViewModelDataMethods<T> {
    void getElements();
    LiveData<List<T>> getElementsDtoResponseLiveData();
}
