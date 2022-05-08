package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections;

import androidx.lifecycle.ViewModel;

import by.bstu.faa.wwi_guide_mobile.repo.data.AchievementRepo;
import by.bstu.faa.wwi_guide_mobile.repo.data.YearRepo;
import lombok.Getter;

public class AchievementViewModel extends ViewModel {
    @Getter
    private AchievementRepo achievementRepo;

    public AchievementViewModel(){
        this.achievementRepo = new AchievementRepo();
    }
}
