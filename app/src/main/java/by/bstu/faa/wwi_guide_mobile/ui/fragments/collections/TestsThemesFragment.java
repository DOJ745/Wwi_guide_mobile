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
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
                            //themeEntities.addAll(testThemeEntities);
                        }
                        @Override
                        public void onError(Throwable e) { }
                        @Override
                        public void onComplete() { }
                    });

            /*testsThemesViewModel.getQuestions().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableMaybeObserver<List<TestQuestionEntity>>() {
                        @Override
                        public void onSuccess(List<TestQuestionEntity> questions) {
                            Log.d(TAG, "DB: set questions to collection");
                            testQuestionEntities.addAll(questions);
                        }
                        @Override
                        public void onError(Throwable e) { }
                        @Override
                        public void onComplete() { }
                    });

            testsThemesViewModel.getAnswers().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableMaybeObserver<List<TestAnswerEntity>>() {
                        @Override
                        public void onSuccess(List<TestAnswerEntity> answers) {
                            Log.d(TAG, "DB: set answers to collection");
                            testAnswersEntities.addAll(answers);
                        }
                        @Override
                        public void onError(Throwable e) { }
                        @Override
                        public void onComplete() { }
                    });*/

            /*testThemeClickListener = (testThemeEntity, position) -> {
                Log.d(TAG, "themeId: " + testThemeEntity.getId());
                testsThemesViewModel.getTestThemeQuestions(testThemeEntity.getId()).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<TestQuestionEntity>>() {
                                       @Override
                                       public void onSubscribe(Disposable d) { }
                                       @Override
                                       public void onSuccess(List<TestQuestionEntity> entities) {
                                           for(TestQuestionEntity question: entities) {
                                               QuestionItem temp = new QuestionItem();
                                               temp.setQuestionId(question.getId());
                                               temp.setQuestionText(question.getText());
                                               temp.setQuestionImg(question.getImg());
                                               questionItems.add(temp);
                                           }

                                           for(QuestionItem item: questionItems) {
                                               testsThemesViewModel.getQuestionAnswers(item.getQuestionId()).subscribeOn(Schedulers.io())
                                                       .observeOn(AndroidSchedulers.mainThread())
                                                       .subscribe(new SingleObserver<List<TestAnswerEntity>>() {
                                                           @Override
                                                           public void onSubscribe(Disposable d) { }
                                                           @Override
                                                           public void onSuccess(List<TestAnswerEntity> entities) {
                                                               for(int i = 0; i < entities.size(); i++){
                                                                   item.getAnswersText().add(entities.get(i).getText());
                                                                   item.getAnswersPoints().add(entities.get(i).getPoints());
                                                                   item.getAnswersIsTrue().add(entities.get(i).getIsTrue());
                                                               }
                                                           }
                                                           @Override
                                                           public void onError(Throwable e) { }
                                                       });
                                           }
                                       }
                                       @Override
                                       public void onError(Throwable e) { }
                                   }
                        );

                Log.d(TAG, "First question: " + questionItems.get(0));
                testQuestionRecyclerAdapter.setItems(questionItems);
                recyclerView.setAdapter(testQuestionRecyclerAdapter);
            };*/
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

                /*for(int i = 0; i < testQuestionEntities.size(); i++){
                    QuestionItem item = new QuestionItem();
                    if(testQuestionEntities.get(i).getTestThemeId().equals(testThemeEntity.getId())){
                        item.setQuestionText(testQuestionEntities.get(i).getText());
                        item.setQuestionImg(testQuestionEntities.get(i).getImg());
                        item.setQuestionId(testQuestionEntities.get(i).getId());
                        questionItems.add(item);
                    }
                }*/
                /*for(int i = 0; i < testAnswersEntities.size(); i++){
                    if(questionItems.get(i).getQuestionId().equals(testAnswersEntities.get(i).getTestQuestionId())){
                        questionItems.get(i).getAnswersText().add(currentAnswers.get(i).getText());
                        questionItems.get(i).getAnswersPoints().add(currentAnswers.get(i).getPoints());
                        questionItems.get(i).getAnswersIsTrue().add(currentAnswers.get(i).getIsTrue());
                    }
                }*/

                /*for(int i = 0; i < testAnswersEntities.size(); i++){
                    if(testQuestionEntities.get(i).getId().equals(testAnswersEntities.get(i).getTestQuestionId())){
                        currentAnswers.add(testAnswersEntities.get(i));
                    }
                }*/

                /*for(int i = 0; i < currentQuestions.size(); i++){
                    QuestionItem item = new QuestionItem();
                    if(currentQuestions.get(i).getId().equals(currentAnswers.get(i).getTestQuestionId())){
                        item.setQuestionText(currentQuestions.get(i).getText());
                        item.setQuestionImg(currentQuestions.get(i).getImg());
                        item.setQuestionId(currentQuestions.get(i).getId());
                        questionItems.add(item);
                    }
                }*/

                /*for(int i = 0; i < currentAnswers.size(); i++){
                    if(questionItems.get(i).getQuestionId().equals(currentAnswers.get(i).getTestQuestionId())){
                        questionItems.get(i).getAnswersText().add(currentAnswers.get(i).getText());
                        questionItems.get(i).getAnswersPoints().add(currentAnswers.get(i).getPoints());
                        questionItems.get(i).getAnswersIsTrue().add(currentAnswers.get(i).getIsTrue());
                    }
                }*/

                /*for (TestQuestionEntity entity: currentQuestions) {
                    QuestionItem item = new QuestionItem();
                    item.setQuestionImg(entity.getImg());
                    item.setQuestionText(entity.getText());
                    item.setQuestionId(entity.getId());
                    questionItems.add(item);
                }
                for(int i = 0; i < questionItems.size(); i++){
                    if(questionItems.get(i).getQuestionId().equals(currentAnswers.get(i).getTestQuestionId())){
                        questionItems.get(i).getAnswersText().add(currentAnswers.get(i).getText());
                        questionItems.get(i).getAnswersPoints().add(currentAnswers.get(i).getPoints());
                        questionItems.get(i).getAnswersIsTrue().add(currentAnswers.get(i).getIsTrue());
                    }
                }*/

            testsThemesRecyclerAdapter = new TestsThemesRecyclerAdapter(requireContext().getApplicationContext(), testThemeClickListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(testsThemesRecyclerAdapter);
            //finishTestBtn.setVisibility(View.VISIBLE);
            //timerView.setVisibility(View.VISIBLE);
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