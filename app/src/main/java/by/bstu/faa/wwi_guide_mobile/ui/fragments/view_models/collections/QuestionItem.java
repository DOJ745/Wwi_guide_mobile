package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections;

import java.util.ArrayList;

import lombok.Data;

@Data
public class QuestionItem {
    private String questionText;
    private String questionImg;
    private ArrayList<String> answersText;
    private ArrayList<Integer> answersPoints;
    private ArrayList<String> answersIsTrue;

    public QuestionItem(){
        answersText = new ArrayList<>();
        answersPoints = new ArrayList<>();
        answersIsTrue = new ArrayList<>();
    }
}
