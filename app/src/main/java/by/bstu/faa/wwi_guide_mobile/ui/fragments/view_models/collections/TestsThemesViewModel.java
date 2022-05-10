package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.app.AppInstance;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.TestAnswerDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.TestQuestionDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.TestThemeDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestAnswerEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestQuestionEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestThemeEntity;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.Getter;

public class TestsThemesViewModel extends ViewModel {
    @Getter
    private final TestThemeDao testThemeDao;
    @Getter
    private final TestQuestionDao testQuestionDao;
    @Getter
    private final TestAnswerDao testAnswerDao;
    private final String TAG = TestsThemesViewModel.class.getSimpleName();

    public TestsThemesViewModel() {
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
        testThemeDao = AppInstance.getInstance().getDatabase().testThemeDao();
        testQuestionDao = AppInstance.getInstance().getDatabase().testQuestionDao();
        testAnswerDao = AppInstance.getInstance().getDatabase().testAnswerDao();
    }

    public Maybe<List<TestThemeEntity>> getTestsThemes() { return testThemeDao.getAll(); }
    public Maybe<List<TestQuestionEntity>> getQuestions() { return testQuestionDao.getAll(); }
    public Maybe<List<TestAnswerEntity>> getAnswers() { return testAnswerDao.getAll(); }

    public List<TestQuestionEntity> getTestThemeQuestions(String themeId) {
        return  testQuestionDao.getTestsQuestionsByThemeId(themeId);
    }
    public List<TestAnswerEntity> getQuestionAnswers(String questionId) {
        return testAnswerDao.getQuestionAnswersByQuestionId(questionId);
    }

    /*public Single<List<TestQuestionEntity>> getTestThemeQuestions(String themeId) {
        return  testQuestionDao.getTestsQuestionsByThemeId(themeId);
    }
    public Single<List<TestAnswerEntity>> getQuestionAnswers(String questionId) {
        return testAnswerDao.getQuestionAnswersByQuestionId(questionId);
    }*/
}
