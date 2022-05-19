package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections;

import androidx.lifecycle.ViewModel;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.ArmamentDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.ArmamentEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.GetUserMethod;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.Getter;

public class ArmamentViewModel extends ViewModel implements GetUserMethod {

    private ArmamentDao armamentDao;
    @Getter
    private final UserDao userDao;

    public ArmamentViewModel() {
        userDao = AppInstance.getInstance().getDatabase().userDao();
        armamentDao = AppInstance.getInstance().getDatabase().armamentDao();
    }

    @Override
    public Maybe<UserEntity> getUserFromDB() { return userDao.getUser(); }
    public Single<ArmamentEntity> getArmamentById(String id) { return armamentDao.getArmamentById(id); }
    public Single<List<ArmamentEntity>> getWeapons() { return armamentDao.getArmamentsByCategory("weapon"); }
    public Single<List<ArmamentEntity>> getTechniqueBySub(String subcategoryConstant) { return armamentDao.getArmamentsBySubcategory(subcategoryConstant); }
}
