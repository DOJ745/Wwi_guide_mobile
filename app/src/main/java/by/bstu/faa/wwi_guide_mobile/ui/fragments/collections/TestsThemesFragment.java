package by.bstu.faa.wwi_guide_mobile.ui.fragments.collections;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestAnswerEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestQuestionEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestThemeEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.security.SecurePreferences;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentBottomNav;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentNavigation;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.TestQuestionRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.TestsThemesRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections.QuestionItem;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections.TestsThemesViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class TestsThemesFragment extends Fragment implements FragmentBottomNav, FragmentNavigation {
    private final String TAG = TestsThemesFragment.class.getSimpleName();

    private TestsThemesViewModel testsThemesViewModel;
    private RecyclerView recyclerView;
    private TestsThemesRecyclerAdapter testsThemesRecyclerAdapter;
    private TestQuestionRecyclerAdapter testQuestionRecyclerAdapter;
    private Boolean hasConnection;
    private TextView timerView;
    private FloatingActionButton infoBtn;
    private Button finishTestBtn;
    private CountDownTimer answerTimer;

    private TestsThemesRecyclerAdapter.OnItemClickListener testThemeClickListener;
    private UserEntity user;

    private SecurePreferences preferences;

    public TestsThemesFragment() {
        // Required empty public constructor
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
        hasConnection = RetrofitService.hasConnection(requireContext());
        preferences = SecurePreferences.getInstance(requireContext());

        testsThemesViewModel = new ViewModelProvider(this).get(TestsThemesViewModel.class);
        testQuestionRecyclerAdapter = new TestQuestionRecyclerAdapter(requireContext());

        if(hasConnection) {
            testsThemesViewModel.getUserFromDB().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableMaybeObserver<UserEntity>() {
                        @Override
                        public void onSuccess(UserEntity userEntity) {
                            Log.d(TAG, "User has been set");
                            user = userEntity;
                            testsThemesViewModel.getTestsThemes().subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DisposableMaybeObserver<List<TestThemeEntity>>() {
                                        @Override
                                        public void onSuccess(List<TestThemeEntity> testThemeEntities) {
                                            Log.d(TAG, "DB: set themes to adapter");
                                            testsThemesRecyclerAdapter.setItems(testThemeEntities);
                                            for(TestThemeEntity theme: testsThemesRecyclerAdapter.getItems()){
                                                for(String achievementId: user.getAchievements()){
                                                    if(theme.getAchievementId().equals(achievementId))
                                                        theme.setName(theme.getName() + " (ПРОЙДЕН)");
                                                }
                                            }
                                            testsThemesRecyclerAdapter.setItems(testsThemesRecyclerAdapter.getItems());
                                        }
                                        @Override
                                        public void onError(Throwable e) { }
                                        @Override
                                        public void onComplete() { }
                                    });
                        }
                        @Override
                        public void onError(Throwable e) { }
                        @Override
                        public void onComplete() { }
                    });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return inflater.inflate(R.layout.fragment_tests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_tests_recycler_view);
        TextView noInternet = view.findViewById(R.id.fragment_tests_no_internet);
        finishTestBtn = view.findViewById(R.id.fragment_tests_btn_finish);
        infoBtn = view.findViewById(R.id.fragment_tests_btn_info);
        timerView = view.findViewById(R.id.fragment_tests_timer);

        infoBtn.setOnClickListener(v -> new AlertDialog.Builder(getActivity())
                .setTitle("Как проходятся тесты. Инструкция для рядовых")
                .setMessage(R.string.prompt_tests_rules)
                .setPositiveButton("Понял", (dialog, which) -> dialog.dismiss() )
                .setIcon(R.drawable.book_icon_128)
                .show());

        if(hasConnection) {
            testThemeClickListener = (theme, position) -> {
                infoBtn.setVisibility(View.GONE);
                testsThemesViewModel.setThemeId(theme.getId());

                answerTimer = new CountDownTimer(90000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        timerView.setText("Осталость " + millisUntilFinished / 1000 + " сек.");
                    }
                    public void onFinish() {
                        timerView.setText("Время вышло");
                        recyclerView.setAdapter(testsThemesRecyclerAdapter);
                    }
                }.start();

                testsThemesViewModel.setAchievementId(theme.getAchievementId());
                formTest(theme.getId());
            };

            testsThemesRecyclerAdapter = new TestsThemesRecyclerAdapter(requireContext().getApplicationContext(), testThemeClickListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(testsThemesRecyclerAdapter);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            timerView.setVisibility(View.GONE);
            finishTestBtn.setVisibility(View.GONE);
            infoBtn.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
        }

        finishTestBtn.setOnClickListener(v -> {
            answerTimer.cancel();
            finishTestBtn.setVisibility(View.GONE);
            timerView.setVisibility(View.GONE);
            infoBtn.setVisibility(View.VISIBLE);

            user.setScore(user.getScore() + testQuestionRecyclerAdapter.getPointsSum());

            testsThemesViewModel.getLog().formLog(
                    CONSTANTS.LOG_STRUCT.ACTION_NAME_PASSED_TEST + testsThemesViewModel.getThemeId(),
                    CONSTANTS.LOG_STRUCT.ACTION_RESULT_TEST_RATIO +
                            testsThemesViewModel.getQuestionItems().size() + "/" +
                            testQuestionRecyclerAdapter.getCorrectAnswersAmount()
                            + "/" + testsThemesViewModel.getTestThreshold());

            testsThemesViewModel.sendLog(preferences.getString("token"), testsThemesViewModel.getLog());

            if(testQuestionRecyclerAdapter.getCorrectAnswersAmount() >= testsThemesViewModel.getTestThreshold()) {
                user.getAchievements().add(testsThemesViewModel.getAchievementId());
                testsThemesViewModel.updateUser(user);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Успех!")
                        .setMessage(R.string.prompt_tests_success)
                        .setPositiveButton("Замечательно", (dialog, which) -> {
                            recyclerView.setAdapter(testsThemesRecyclerAdapter);
                            dialog.dismiss();
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
                            recyclerView.setAdapter(testsThemesRecyclerAdapter);
                            dialog.dismiss();
                            navigateToFragment(view, "year");
                        } )
                        .setIcon(R.drawable.test_failure)
                        .show();
            }
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
        testsThemesViewModel.setTestThreshold((int)Math.round(_testThreshold / 2));

        testQuestionRecyclerAdapter.setItems(testsThemesViewModel.getQuestionItems());
        recyclerView.setAdapter(testQuestionRecyclerAdapter);
        finishTestBtn.setVisibility(View.VISIBLE);
        timerView.setVisibility(View.VISIBLE);
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