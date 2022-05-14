package by.bstu.faa.wwi_guide_mobile.ui.fragments.details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.database.entities.base_entity.BaseDataEntity;

public interface FragmentDataMethods {
    void formArticle(View view);
    default void disableRadioButtons(RadioGroup radioGroup) {
        for(int i = 0; i < radioGroup.getChildCount(); i++) {
            ((RadioButton) radioGroup.getChildAt(i)).setClickable(false);
        }
    }

    @SuppressLint("ResourceType")
    default void createUiElements(View view,
                                  BaseDataEntity entity,
                                  ConstraintLayout constraintLayout,
                                  TextView surveyQuestionView,
                                  Context context,
                                  int titleViewId) {


        int lastParagraphViewId = 0;
        for(int i = 0; i < entity.getImages().size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setId(200000 + i);
            TextView imageTitleView = new TextView(context);
            imageTitleView.setId(2010000 + i);
            TextView paragraphView = new TextView(context);
            paragraphView.setId(2020000 + i);

            Glide
                    .with(view)
                    .asBitmap()
                    .load(entity.getImages().get(i))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(imageView);
            imageTitleView.setText(entity.getImages_titles().get(i));

            paragraphView.setText(Html.fromHtml(entity.getText_paragraphs().get(i)));

            ConstraintLayout.LayoutParams imageViewLayoutParams = new ConstraintLayout.LayoutParams
                    (ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            imageViewLayoutParams.startToStart = constraintLayout.getId();
            imageViewLayoutParams.endToEnd = constraintLayout.getId();
            imageViewLayoutParams.leftMargin = 16;
            imageViewLayoutParams.rightMargin = 16;
            if(i == 0) { imageViewLayoutParams.topToBottom = titleViewId; }
            else { imageViewLayoutParams.topToBottom = paragraphView.getId() - 1; }
            imageView.setLayoutParams(imageViewLayoutParams);

            ConstraintLayout.LayoutParams imageTitleViewLayoutParams = new ConstraintLayout.LayoutParams
                    (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            imageTitleViewLayoutParams.startToStart = constraintLayout.getId();
            imageTitleViewLayoutParams.endToEnd = constraintLayout.getId();
            imageTitleViewLayoutParams.topMargin = 8;
            imageTitleViewLayoutParams.topToBottom = imageView.getId();
            imageTitleView.setLayoutParams(imageTitleViewLayoutParams);

            ConstraintLayout.LayoutParams paragraphLayoutParams = new ConstraintLayout.LayoutParams
                    (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            paragraphLayoutParams.startToStart = constraintLayout.getId();
            paragraphLayoutParams.endToEnd = constraintLayout.getId();
            paragraphLayoutParams.topToBottom = imageTitleView.getId();
            paragraphLayoutParams.leftMargin = 8;
            paragraphLayoutParams.rightMargin = 8;
            paragraphView.setLayoutParams(paragraphLayoutParams);

            constraintLayout.addView(imageView);
            constraintLayout.addView(imageTitleView);
            constraintLayout.addView(paragraphView);
            lastParagraphViewId = paragraphView.getId();
        }
        ConstraintLayout.LayoutParams surveyQuestionLayoutParams = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        surveyQuestionLayoutParams.startToStart = constraintLayout.getId();
        surveyQuestionLayoutParams.endToEnd = constraintLayout.getId();
        surveyQuestionLayoutParams.topToBottom = lastParagraphViewId;
        surveyQuestionView.setLayoutParams(surveyQuestionLayoutParams);
    }
}
