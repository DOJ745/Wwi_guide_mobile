package by.bstu.faa.wwi_guide_mobile.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import by.bstu.faa.wwi_guide_mobile.data_objects.dto.AppMsgResponseDto;

public abstract class AuthRepo {
    protected final MutableLiveData<AppMsgResponseDto> appResponse;
    public AuthRepo() { appResponse = new MutableLiveData<>(); }

    public LiveData<AppMsgResponseDto> getResponse() { return appResponse; }
}
