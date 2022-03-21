package by.bstu.faa.wwi_guide_mobile.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.RankDto;
import by.bstu.faa.wwi_guide_mobile.repo.RankRepo;

public class RegisterViewModel extends AndroidViewModel {

    private RankRepo rankRepo;
    private LiveData<List<RankDto>> ranksDtoResponseLiveData;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        rankRepo = new RankRepo();
        ranksDtoResponseLiveData = rankRepo.getElementsLiveData();
    }

    public void getRanks(TokenData token){ rankRepo.getElements(token); }

    public LiveData<List<RankDto>> getRanksDtoResponseLiveData() { return ranksDtoResponseLiveData; }
}
