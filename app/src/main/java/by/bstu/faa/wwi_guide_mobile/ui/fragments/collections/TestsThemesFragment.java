package by.bstu.faa.wwi_guide_mobile.ui.fragments.collections;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import io.reactivex.observers.DisposableSingleObserver;
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
    //private ArrayList<QuestionItem> questionItems;

    public TestsThemesFragment() {
        // Required empty public constructor
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);

        hasConnection = RetrofitService.hasConnection(requireContext());

        if(hasConnection) {
            //questionItems = new ArrayList<>();
            testsThemesViewModel = new ViewModelProvider(this).get(TestsThemesViewModel.class);
            testQuestionRecyclerAdapter = new TestQuestionRecyclerAdapter(requireContext());

            TestsThemesRecyclerAdapter.OnItemClickListener testThemeClickListener =
                    (testThemeEntity, position) -> {
                        Toast.makeText(requireContext().getApplicationContext(),
                                "You chose testThemeEntity with title " + testThemeEntity.getName(),
                                Toast.LENGTH_SHORT).show();

                        testsThemesViewModel.getTestThemeQuestions(testThemeEntity.getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableSingleObserver<List<TestQuestionEntity>>() {
                                    @Override
                                    public void onSuccess(List<TestQuestionEntity> testQuestionEntities) {
                                        ArrayList<QuestionItem> temp = new ArrayList<>();
                                        for (TestQuestionEntity question: testQuestionEntities) {
                                            QuestionItem questionItem = new QuestionItem();

                                            testsThemesViewModel.getQuestionAnswers(question.getId())
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new DisposableSingleObserver<List<TestAnswerEntity>>() {
                                                        @Override
                                                        public void onSuccess(List<TestAnswerEntity> testAnswerEntities) {
                                                            questionItem.setQuestionText(question.getText());
                                                            questionItem.setQuestionImg(question.getImg());

                                                            for (TestAnswerEntity answer: testAnswerEntities) {
                                                                questionItem.getAnswersText().add(answer.getText());
                                                                questionItem.getAnswersPoints().add(answer.getPoints());
                                                                questionItem.getAnswersIsTrue().add(answer.getIsTrue());
                                                            }
                                                            temp.add(questionItem);
                                                        }
                                                        @Override
                                                        public void onError(Throwable e) { }
                                                    });
                                        }
                                        testQuestionRecyclerAdapter.setItems(temp);
                                    }
                                    @Override
                                    public void onError(Throwable e) { }
                                });
                    };

            testsThemesRecyclerAdapter = new TestsThemesRecyclerAdapter(requireContext().getApplicationContext(), testThemeClickListener);

            testsThemesViewModel.getTestsThemes().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableMaybeObserver<List<TestThemeEntity>>() {
                        @Override
                        public void onSuccess(List<TestThemeEntity> testThemeEntities) {
                            testsThemesRecyclerAdapter.setItems(testThemeEntities);
                        }
                        @Override
                        public void onError(Throwable e) { }
                        @Override
                        public void onComplete() { }
                    });
        }
        else { hasConnection = false; }
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
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(testsThemesRecyclerAdapter);
            finishTestBtn.setVisibility(View.VISIBLE);
            timerView.setVisibility(View.VISIBLE);
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