package by.bstu.faa.wwi_guide_mobile.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import by.bstu.faa.wwi_guide_mobile.data_objects.Token;
import by.bstu.faa.wwi_guide_mobile.repo.TestRepo;

public class TokenViewModel extends AndroidViewModel {

    private TestRepo tokenRepo;
    private LiveData<Token> tokenResponseLiveData;

    public TokenViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        tokenRepo = new TestRepo();
        tokenResponseLiveData = tokenRepo.getTokenLiveData();
    }

    public LiveData<Token> getTokenResponseLiveData() { return tokenResponseLiveData; }
}
