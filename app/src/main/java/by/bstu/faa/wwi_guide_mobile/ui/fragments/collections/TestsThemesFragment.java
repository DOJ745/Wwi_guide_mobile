package by.bstu.faa.wwi_guide_mobile.ui.fragments.collections;

import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestAnswerEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestQuestionEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestThemeEntity;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentBottomNav;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.TestQuestionRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.TestsThemesRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections.QuestionItem;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections.TestsThemesViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class TestsThemesFragment extends Fragment implements FragmentBottomNav {
    private final String TAG = TestsThemesFragment.class.getSimpleName();

    private TestsThemesViewModel testsThemesViewModel;
    private RecyclerView recyclerView;
    private TestsThemesRecyclerAdapter testsThemesRecyclerAdapter;
    private TestQuestionRecyclerAdapter testQuestionRecyclerAdapter;
    private Boolean hasConnection;
    private TextView timerView;
    private Button finishTestBtn;

    private TestsThemesRecyclerAdapter.OnItemClickListener testThemeClickListener;
    private ArrayList<QuestionItem> questionItems;

    public TestsThemesFragment() {
        // Required empty public constructor
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);

        hasConnection = RetrofitService.hasConnection(requireContext());
        questionItems = new ArrayList<>();

        testsThemesViewModel = new ViewModelProvider(this).get(TestsThemesViewModel.class);
        testQuestionRecyclerAdapter = new TestQuestionRecyclerAdapter(requireContext());

        if(hasConnection) {
            testsThemesViewModel.getTestsThemes().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableMaybeObserver<List<TestThemeEntity>>() {
                        @Override
                        public void onSuccess(List<TestThemeEntity> testThemeEntities) {
                            Log.d(TAG, "DB: set themes to collection");
                            testsThemesRecyclerAdapter.setItems(testThemeEntities);
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
        timerView = view.findViewById(R.id.fragment_tests_timer);

        if(hasConnection) {
            testThemeClickListener = (theme, position) -> {
                List<TestQuestionEntity> currentQuestions = new ArrayList<>(testsThemesViewModel.getTestThemeQuestions(theme.getId()));

                for(TestQuestionEntity question: currentQuestions){
                    QuestionItem temp = new QuestionItem();
                    temp.setQuestionText(question.getText());
                    temp.setQuestionImg(question.getImg());
                    temp.setAnswers((ArrayList<TestAnswerEntity>) testsThemesViewModel.getQuestionAnswers(question.getId()));
                    questionItems.add(temp);
                }
                testQuestionRecyclerAdapter.setItems(questionItems);
                recyclerView.setAdapter(testQuestionRecyclerAdapter);
                finishTestBtn.setVisibility(View.VISIBLE);
                timerView.setVisibility(View.VISIBLE);
            };

            testsThemesRecyclerAdapter = new TestsThemesRecyclerAdapter(requireContext().getApplicationContext(), testThemeClickListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(testsThemesRecyclerAdapter);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            timerView.setVisibility(View.GONE);
            finishTestBtn.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
        }

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
}