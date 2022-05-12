package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import androidx.lifecycle.ViewModel;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.database.dao.EventDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyAnswerDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyQuestionDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyQuestionEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.Getter;
import lombok.Setter;

public class EventViewModel extends ViewModel implements DataMethods<EventEntity> {
    private EventDao eventDao;
    @Getter
    private UserDao userDao;
    @Getter
    private SurveyQuestionDao surveyQuestionDao;
    @Getter
    private SurveyAnswerDao surveyAnswerDao;

    public EventViewModel() {
        userDao = AppInstance.getInstance().getDatabase().userDao();
        eventDao = AppInstance.getInstance().getDatabase().eventDao();
        surveyQuestionDao = AppInstance.getInstance().getDatabase().surveyQuestionDao();
        surveyAnswerDao = AppInstance.getInstance().getDatabase().surveyAnswerDao();
    }
    public Single<EventEntity> getEntityDataById(String id) { return eventDao.getEventById(id); }
    public Single<SurveyQuestionEntity> getSurveyQuestionById(String id) { return surveyQuestionDao.getSurveyQuestionByEventId(id); }

    @Override
    public Maybe<UserEntity> getUserFromDB() { return userDao.getUser(); }
}
