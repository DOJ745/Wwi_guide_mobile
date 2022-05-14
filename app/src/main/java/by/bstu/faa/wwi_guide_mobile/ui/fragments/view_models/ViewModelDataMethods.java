package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.LogDto;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyEntity;
import by.bstu.faa.wwi_guide_mobile.repo.log.LogRepo;
import io.reactivex.Single;

public interface ViewModelDataMethods<T> extends GetUserMethod, UpdateUser {
    Single<T> getEntityDataById(String entityId);
    default Single<SurveyEntity> getSurveyById(String id, SurveyDao surveyDao) { return surveyDao.getSurveyById(id); }
    default void sendLog(String token, LogDto data, LogRepo logRepo) { logRepo.sendLog(token, data); }
}
