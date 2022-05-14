package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections;

import androidx.lifecycle.ViewModel;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.ArmamentDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.ArmamentEntity;
import io.reactivex.Single;

public class ArmamentViewModel extends ViewModel {

    private ArmamentDao armamentDao;

    public ArmamentViewModel() {
        armamentDao = AppInstance.getInstance().getDatabase().armamentDao();
    }

    public Single<ArmamentEntity> getArmamentById(String id) { return armamentDao.getArmamentById(id); }
    public Single<List<ArmamentEntity>> getWeapons() { return armamentDao.getArmamentsByCategory("weapon"); }
    public Single<List<ArmamentEntity>> getTechnique() { return armamentDao.getArmamentsByCategory("technique"); }
}
