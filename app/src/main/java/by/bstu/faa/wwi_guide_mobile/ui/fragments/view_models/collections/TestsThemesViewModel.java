package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.LogDto;
import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.TestAnswerDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.TestQuestionDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.TestThemeDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestAnswerEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestQuestionEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestThemeEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.repo.log.LogRepo;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.GetUserMethod;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.UpdateUser;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import lombok.Setter;

public class TestsThemesViewModel extends ViewModel implements GetUserMethod, UpdateUser {
    private final String TAG = TestsThemesViewModel.class.getSimpleName();

    @Getter
    private final TestThemeDao testThemeDao;
    @Getter
    private final TestQuestionDao testQuestionDao;
    @Getter
    private final TestAnswerDao testAnswerDao;
    @Getter
    private final UserDao userDao;
    @Getter@Setter
    private int testThreshold;
    @Getter@Setter
    private String themeId;
    @Getter
    private final ArrayList<QuestionItem> questionItems = new ArrayList<>();
    @Getter@Setter
    private String achievementId;
    @Getter
    private LogRepo logRepo;
    @Getter@Setter
    private LogDto log;

    public TestsThemesViewModel() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        testThemeDao = AppInstance.getInstance().getDatabase().testThemeDao();
        testQuestionDao = AppInstance.getInstance().getDatabase().testQuestionDao();
        testAnswerDao = AppInstance.getInstance().getDatabase().testAnswerDao();
        userDao = AppInstance.getInstance().getDatabase().userDao();
        logRepo = new LogRepo();
        log = new LogDto();
    }

    public void sendLog(String token, LogDto data) { logRepo.sendLog(token, data); }
    public Maybe<List<TestThemeEntity>> getTestsThemes() { return testThemeDao.getAll(); }
    public List<TestQuestionEntity> getTestThemeQuestions(String themeId) {
        return testQuestionDao.getTestsQuestionsByThemeId(themeId);
    }
    public List<TestAnswerEntity> getQuestionAnswers(String questionId) {
        return testAnswerDao.getQuestionAnswersByQuestionId(questionId);
    }
    public Maybe<UserEntity> getUserFromDB(){ return userDao.getUser(); }
}
