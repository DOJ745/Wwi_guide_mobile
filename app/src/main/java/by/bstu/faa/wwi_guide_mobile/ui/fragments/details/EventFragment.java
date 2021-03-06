package by.bstu.faa.wwi_guide_mobile.ui.fragments.details;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import by.bstu.faa.wwi_guide_mobile.MainActivity;
import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.security.SecurePreferences;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentBottomNav;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.EventViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class EventFragment extends Fragment implements FragmentDataMethods, FragmentBottomNav {
    private final String TAG = EventFragment.class.getSimpleName();

    private EventViewModel eventViewModel;
    private static final String ARG_ID = "id";
    private TextView titleView;
    private TextView surveyQuestionView;
    private ConstraintLayout constraintLayout;
    private RadioGroup radioGroup;
    private ImageView surveyImg;

    private String eventId;
    private EventEntity entity;
    private SecurePreferences preferences;
    private UserEntity user;
    private int achievementPoints;

    public EventFragment() {
        // Required empty public constructor
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = SecurePreferences.getInstance(requireContext());
        entity = new EventEntity();
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        showBottomNav(MainActivity.BottomNavigationView, false);

        if (getArguments() != null) {
            eventId = getArguments().getString(ARG_ID);
            Log.d(TAG, getArguments().getString(ARG_ID));
        }
        eventViewModel.getUserFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<UserEntity>() {
                    @Override
                    public void onSuccess(UserEntity entity) { user = entity; }
                    @Override
                    public void onError(Throwable e) { }
                    @Override
                    public void onComplete() { }
                });

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        constraintLayout = view.findViewById(R.id.fragment_data_constraint_layout);
        titleView = view.findViewById(R.id.fragment_data_title);
        radioGroup = view.findViewById(R.id.fragment_data_survey);
        surveyQuestionView = view.findViewById(R.id.fragment_data_survey_question_text);
        surveyImg = view.findViewById(R.id.fragment_data_survey_img);

        formArticle(view);

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_CREATED);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_STATE_RESTORED);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_STOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY_VIEW);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_DETACH);
    }

    @Override
    public void formArticle(View view) {
        eventViewModel.getEntityDataById(eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<EventEntity>() {
                    @Override
                    public void onSuccess(EventEntity eventEntity) {
                        entity = eventEntity;
                        eventViewModel.setAchievementId(eventEntity.getAchievementId());
                        titleView.setText(entity.getTitle());

                        eventViewModel.getAchievementById(entity.getAchievementId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableSingleObserver<AchievementEntity>() {
                                    @Override
                                    public void onSuccess(AchievementEntity achievementEntity) {
                                        achievementPoints = achievementEntity.getPoints();
                                    }
                                    @Override
                                    public void onError(Throwable e) { }
                                });

                        if(RetrofitService.hasConnection(requireContext()))
                        {
                            eventViewModel.getSurveyById(entity.getSurveyId(), eventViewModel.getSurveyDao())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DisposableSingleObserver<SurveyEntity>() {
                                        @SuppressLint("ResourceType")
                                        @Override
                                        public void onSuccess(SurveyEntity surveyEntity) {
                                            surveyQuestionView.setText(surveyEntity.getQuestion_text());
                                            eventViewModel.setPoints(surveyEntity.getPoints());

                                            if(surveyEntity.getImg() == null)
                                            {
                                                Glide
                                                        .with(view)
                                                        .asBitmap()
                                                        .load(surveyEntity.getImg())
                                                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                                        .placeholder(R.drawable.placeholder)
                                                        .error(R.drawable.error)
                                                        .into(surveyImg);
                                            }
                                            else surveyImg.setVisibility(View.GONE);
                                            for(int i = 0; i < surveyEntity.getAnswer_variants().size(); i++) {
                                                RadioButton radioButton = new RadioButton(requireContext());
                                                radioButton.setId(2030000 + i);
                                                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                                                        (ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                                                radioButton.setLayoutParams(layoutParams);
                                                radioButton.setText(surveyEntity.getAnswer_variants().get(i));
                                                radioButton.setTextColor(Color.BLACK);
                                                radioGroup.addView(radioButton);
                                            }
                                            if(user.getAchievements().contains(entity.getAchievementId())) {
                                                surveyQuestionView.setText(R.string.prompt_passed_survey);
                                                radioGroup.setVisibility(View.GONE);
                                            }
                                            else surveyLogic(view, surveyEntity.getId(), entity.getId());
                                        }
                                        @Override
                                        public void onError(Throwable e) { }
                                    });
                        }
                        else {
                            surveyQuestionView.setText(R.string.prompt_no_surveys);
                            surveyImg.setVisibility(View.GONE);
                        }
                        createUiElements(view,
                                entity,
                                constraintLayout,
                                surveyQuestionView,
                                requireContext(),
                                titleView.getId());
                    }
                    @Override
                    public void onError(Throwable e) { }
                });
    }

    private void surveyLogic(View view, String surveyId, String eventId) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = view.findViewById(checkedId);

            user.getAchievements().add(eventViewModel.getAchievementId());
            user.setScore(user.getScore() + eventViewModel.getPoints() + achievementPoints);
            eventViewModel.updateUser(user, eventViewModel.getUserDao(), TAG);

            eventViewModel.getLog().formLog(
                    CONSTANTS.LOG_STRUCT.ACTION_NAME_PASSED_SURVEY_ID + surveyId + "\n"
                            + CONSTANTS.LOG_STRUCT.LOG_EVENT_ID + eventId,
                    CONSTANTS.LOG_STRUCT.ACTION_RESULT_SURVEY + radioButton.getText());

            eventViewModel.sendLog(preferences.getString("token"), eventViewModel.getLog(), eventViewModel.getLogRepo());

            new AlertDialog.Builder(getActivity())
                    .setTitle("??????????????!")
                    .setMessage(R.string.prompt_answered_survey)
                    .setPositiveButton("????????????", (dialog, which) -> dialog.dismiss())
                    .setIcon(R.drawable.passed_survey)
                    .show();

            disableRadioButtons(radioGroup);
        });
    }
}