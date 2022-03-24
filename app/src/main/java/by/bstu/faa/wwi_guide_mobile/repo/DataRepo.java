package by.bstu.faa.wwi_guide_mobile.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;

public abstract class DataRepo<T> {

    protected final MutableLiveData<List<T>> elementsDtoMutableLiveData;
    public DataRepo() { elementsDtoMutableLiveData = new MutableLiveData<>(); }

    abstract void getElements(TokenData token);
    abstract LiveData<List<T>> getElementsLiveData();
}
