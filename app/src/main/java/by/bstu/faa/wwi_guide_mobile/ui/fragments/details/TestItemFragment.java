package by.bstu.faa.wwi_guide_mobile.ui.fragments.details;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.MainActivity;
import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestAnswerEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestQuestionEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.security.SecurePreferences;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentBottomNav;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentNavigation;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.TestQuestionRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections.QuestionItem;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections.TestsThemesViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TestItemFragment extends Fragment implements FragmentBottomNav, FragmentNavigation {
    private final String TAG = TestItemFragment.class.getSimpleName();

    private static final String ARG_THEME_ID = "themeId";
    private static final String ARG_ACHIEVEMENT_ID = "achievementId";

    private String themeId;
    private String achievementId;
    private int achievementPoints;

    private TestsThemesViewModel testsThemesViewModel;
    private RecyclerView recyclerView;
    private TestQuestionRecyclerAdapter testQuestionRecyclerAdapter;
    private TextView timerView;
    private CountDownTimer answerTimer;

    private UserEntity user;
    private SecurePreferences preferences;

    public TestItemFragment() {
        // Required empty public constructor
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            themeId = getArguments().getString(ARG_THEME_ID);
            achievementId = getArguments().getString(ARG_ACHIEVEMENT_ID);
        }

        preferences = SecurePreferences.getInstance(requireContext());
        testsThemesViewModel = new ViewModelProvider(this).get(TestsThemesViewModel.class);
        testQuestionRecyclerAdapter = new TestQuestionRecyclerAdapter(requireContext());

        showBottomNav(MainActivity.BottomNavigationView, false);

        testsThemesViewModel.getUserFromDB()
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

        testsThemesViewModel.getAchievementById(achievementId)
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

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_item, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Button finishTestBtn = view.findViewById(R.id.fragment_test_item_btn_finish);
        timerView = view.findViewById(R.id.fragment_test_item_timer);
        recyclerView = view.findViewById(R.id.fragment_test_item_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        formTest(themeId);

        answerTimer = new CountDownTimer(90000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerView.setText("Осталость " + millisUntilFinished / 1000 + " сек.");
            }
            public void onFinish() {
                timerView.setText("Время вышло");
                navigateToFragment(view, "tests");
            }
        }.start();

        finishTestBtn.setOnClickListener(v -> {
            answerTimer.cancel();
            if(testQuestionRecyclerAdapter.getCorrectAnswersAmount() >= testsThemesViewModel.getTestThreshold()) {
                user.setScore(user.getScore() + testQuestionRecyclerAdapter.getPointsSum() + achievementPoints);
                user.getAchievements().add(achievementId);

                new AlertDialog.Builder(getActivity())
                        .setTitle("Успех!")
                        .setMessage(R.string.prompt_tests_success)
                        .setPositiveButton("Замечательно", (dialog, which) -> {
                            dialog.dismiss();
                            testsThemesViewModel.updateUser(user, testsThemesViewModel.getUserDao(), TAG);
                            navigateToFragment(view, "user");
                        } )
                        .setIcon(R.drawable.test_success)
                        .show();
            }
            else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Провал!")
                        .setMessage(R.string.prompt_tests_failure)
                        .setPositiveButton("Вас понял", (dialog, which) -> {
                            dialog.dismiss();
                            navigateToFragment(view, "year");
                        } )
                        .setIcon(R.drawable.test_failure)
                        .show();
            }

            testsThemesViewModel.getLog().formLog(
                    CONSTANTS.LOG_STRUCT.ACTION_NAME_PASSED_TEST_ID + themeId,
                    CONSTANTS.LOG_STRUCT.ACTION_RESULT_TEST_RATIO +
                            testsThemesViewModel.getQuestionItems().size() + "/" +
                            testQuestionRecyclerAdapter.getCorrectAnswersAmount()
                            + "/" + testsThemesViewModel.getTestThreshold());

            testsThemesViewModel.sendLog(preferences.getString("token"), testsThemesViewModel.getLog());
        });

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

    private void formTest(String themeId) {
        float _testThreshold = 0.0f;
        List<TestQuestionEntity> currentQuestions = new ArrayList<>(testsThemesViewModel.getTestThemeQuestions(themeId));
        List<List<TestAnswerEntity>> currentAnswers = new ArrayList<>();

        for(TestQuestionEntity question: currentQuestions) {
            QuestionItem temp = new QuestionItem();
            temp.setQuestionText(question.getText());
            temp.setQuestionImg(question.getImg());
            temp.setAnswers((ArrayList<TestAnswerEntity>) testsThemesViewModel.getQuestionAnswers(question.getId()));
            currentAnswers.add(testsThemesViewModel.getQuestionAnswers(question.getId()));
            testsThemesViewModel.getQuestionItems().add(temp);
        }

        for(List<TestAnswerEntity> answerList: currentAnswers) {
            for(int i = 0; i < answerList.size(); i++) {
                if (answerList.get(i).getIsTrue().equals("true")){
                    _testThreshold += 1;
                }
            }
        }
        testsThemesViewModel.setTestThreshold(Math.round(_testThreshold / 2));
        testQuestionRecyclerAdapter.setItems(testsThemesViewModel.getQuestionItems());
        recyclerView.setAdapter(testQuestionRecyclerAdapter);
    }

    @Override
    public void navigateToFragment(View view, String fragmentName) {
        switch (fragmentName){
            case "year":
                Navigation.findNavController(view).navigate(R.id.yearsFragment, null);
                break;
            case "user":
                Navigation.findNavController(view).navigate(R.id.userFragment, null);
                break;
        }
    }
}