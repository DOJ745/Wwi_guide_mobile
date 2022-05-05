package by.bstu.faa.wwi_guide_mobile.repo.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public abstract class DataRepo<T> {

    protected final MutableLiveData<List<T>> apiRes;
    public DataRepo() { apiRes = new MutableLiveData<>(); }

    abstract void callApi();
    abstract LiveData<List<T>> getApiRes();
}
