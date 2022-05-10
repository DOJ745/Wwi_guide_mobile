package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections;

import java.util.ArrayList;

import by.bstu.faa.wwi_guide_mobile.database.entities.TestAnswerEntity;
import lombok.Data;

@Data
public class QuestionItem {
    private String questionText;
    private String questionImg;
    private ArrayList<TestAnswerEntity> answers;

    public QuestionItem(){ answers = new ArrayList<>(); }
}
