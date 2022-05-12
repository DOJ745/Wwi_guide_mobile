package by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections.QuestionItem;
import lombok.Getter;

public class TestQuestionRecyclerAdapter extends RecyclerView.Adapter<TestQuestionRecyclerAdapter.TestResultHolder>
        implements AdapterSetItems<QuestionItem> {

    private List<QuestionItem> items = new ArrayList<>();
    private final LayoutInflater inflater;
    @Getter
    private int pointsSum = 0;
    @Getter
    private int correctAnswersAmount = 0;

    public TestQuestionRecyclerAdapter(Context context) { this.inflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public TestResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.collection_item_question, parent, false);
        return new TestQuestionRecyclerAdapter.TestResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TestResultHolder holder, int position) {
        Typeface typeface = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "font/old_type_nr_regular.ttf");
        holder.textView.setTypeface(typeface);

        QuestionItem item = items.get(position);

        holder.textView.setText(item.getQuestionText());
        if(item.getQuestionImg() != null){
            Glide
                    .with(holder.itemView)
                    .asBitmap()
                    .load(item.getQuestionImg())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(holder.imgView);
        }
        else holder.imgView.setVisibility(View.GONE);

        holder.answerOne.setText(item.getAnswers().get(item.getRandOne()).getText());
        holder.answerTwo.setText(item.getAnswers().get(item.getRandTwo()).getText());
        holder.answerThree.setText(item.getAnswers().get(item.getRandThree()).getText());

        holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.item_question_answer1:
                    pointsSum += item.getAnswers().get(item.getRandOne()).getPoints();
                    checkAnswer(item.getAnswers().get(item.getRandOne()).getIsTrue());
                    disableRadioButtons(holder);
                    break;
                case R.id.item_question_answer2:
                    pointsSum += item.getAnswers().get(item.getRandTwo()).getPoints();
                    checkAnswer(item.getAnswers().get(item.getRandTwo()).getIsTrue());
                    disableRadioButtons(holder);
                    break;
                case R.id.item_question_answer3:
                    pointsSum += item.getAnswers().get(item.getRandThree()).getPoints();
                    checkAnswer(item.getAnswers().get(item.getRandThree()).getIsTrue());
                    disableRadioButtons(holder);
                    break;
            }
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    @Override
    public void setItems(List<QuestionItem> items) {
        this.items = items;
        //this.notifyDataSetChanged();
    }

    static class TestResultHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imgView;
        private final RadioGroup radioGroup;
        private final RadioButton answerOne;
        private final RadioButton answerTwo;
        private final RadioButton answerThree;

        public TestResultHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_question_text);
            this.imgView = itemView.findViewById(R.id.item_question_img);
            this.radioGroup = itemView.findViewById(R.id.item_question_radio_group);
            this.answerOne = itemView.findViewById(R.id.item_question_answer1);
            this.answerTwo = itemView.findViewById(R.id.item_question_answer2);
            this.answerThree = itemView.findViewById(R.id.item_question_answer3);
        }
    }

    private void checkAnswer(String isTrueValue){
        if(isTrueValue.equals("true"))
            correctAnswersAmount += 1;
    }
    private void disableRadioButtons(TestResultHolder holder){
        for(int i = 0; i < holder.radioGroup.getChildCount(); i++){
            ((RadioButton)holder.radioGroup.getChildAt(i)).setClickable(false);
        }
    }
}
