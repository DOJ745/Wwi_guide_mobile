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

    public QuestionItem(){
        Log.d(TAG, "constructor");
        answers = new ArrayList<>();
    }

    public static int getRandomAnswer() { return (int) (Math.random() * 3); }
}
