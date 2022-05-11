package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

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
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.DataMethods;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import lombok.Setter;

public class TestsThemesViewModel extends ViewModel implements DataMethods {
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
    @Getter
    private final ArrayList<QuestionItem> questionItems = new ArrayList<>();
    @Getter@Setter
    private String achievementId;

    public TestsThemesViewModel() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        testThemeDao = AppInstance.getInstance().getDatabase().testThemeDao();
        testQuestionDao = AppInstance.getInstance().getDatabase().testQuestionDao();
        testAnswerDao = AppInstance.getInstance().getDatabase().testAnswerDao();
        userDao = AppInstance.getInstance().getDatabase().userDao();
    }

    public Maybe<List<TestThemeEntity>> getTestsThemes() { return testThemeDao.getAll(); }
    public List<TestQuestionEntity> getTestThemeQuestions(String themeId) {
        return testQuestionDao.getTestsQuestionsByThemeId(themeId);
    }
    public List<TestAnswerEntity> getQuestionAnswers(String questionId) {
        return testAnswerDao.getQuestionAnswersByQuestionId(questionId);
    }
    public Maybe<UserEntity> getUserFromDB(){ return userDao.getUser(); }

    public void updateUser(UserEntity user){
        userDao.insert(user).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new DisposableCompletableObserver() {
                @Override
                public void onComplete() { Log.d(TAG, "User has been updated"); }
                @Override
                public void onError(Throwable e) { Log.d(TAG, "Error occurred\n" + e.getMessage()); }
            });
    }
}
