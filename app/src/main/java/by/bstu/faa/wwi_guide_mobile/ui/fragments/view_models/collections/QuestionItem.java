package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections;

import android.util.Log;

import java.util.ArrayList;

import by.bstu.faa.wwi_guide_mobile.database.entities.TestAnswerEntity;
import lombok.Data;

@Data
public class QuestionItem {
    private final String TAG = QuestionItem.class.getSimpleName();
    private String questionText;
    private String questionImg;
    private ArrayList<TestAnswerEntity> answers;
    private int randOne;
    private int randTwo;
    private int randThree;

    public QuestionItem() {
        Log.d(TAG, "constructor");
        answers = new ArrayList<>();
        generateRandomAnswers();
        Log.d(TAG, "Random answers: " + randOne + "-" + randTwo + "-" + randThree);
    }

    public void generateRandomAnswers() {
        int _randomAnswerOne = getRandomAnswer();
        int _randomAnswerTwo = getRandomAnswer();
        int _randomAnswerThree = getRandomAnswer();
        while (_randomAnswerOne == _randomAnswerTwo ||
                _randomAnswerOne == _randomAnswerThree ||
                _randomAnswerTwo == _randomAnswerThree) {
            _randomAnswerOne = getRandomAnswer();
            _randomAnswerTwo = getRandomAnswer();
            _randomAnswerThree = getRandomAnswer();
        }
        randOne = _randomAnswerOne;
        randTwo = _randomAnswerTwo;
        randThree = _randomAnswerThree;
    }

    public static int getRandomAnswer() { return (int) (Math.random() * 3); }
}
