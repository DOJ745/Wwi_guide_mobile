package by.bstu.faa.wwi_guide_mobile.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;

public abstract class BasicRepo<T> {

    protected MutableLiveData<List<T>> elementsDtoMutableLiveData;
    public BasicRepo() { elementsDtoMutableLiveData = new MutableLiveData<>(); }

    abstract void getElements(TokenData token);
    abstract LiveData<List<T>> getElementsLiveData();
}
