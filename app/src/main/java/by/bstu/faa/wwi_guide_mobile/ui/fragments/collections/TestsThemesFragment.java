package by.bstu.faa.wwi_guide_mobile.ui.fragments.collections;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.MainActivity;
import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestThemeEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentBottomNav;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentNavigation;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.TestsThemesRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections.TestsThemesViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class TestsThemesFragment extends Fragment implements FragmentBottomNav, FragmentNavigation {
    private final String TAG = TestsThemesFragment.class.getSimpleName();

    private TestsThemesViewModel testsThemesViewModel;
    private TestsThemesRecyclerAdapter testsThemesRecyclerAdapter;
    private Boolean hasConnection;

    private UserEntity user;
    private String achievementId;
    private String themeId;

    public TestsThemesFragment() {
        // Required empty public constructor
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
        hasConnection = RetrofitService.hasConnection(requireContext());
        testsThemesViewModel = new ViewModelProvider(this).get(TestsThemesViewModel.class);

        if(hasConnection) {
            testsThemesViewModel.getUserFromDB()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableMaybeObserver<UserEntity>() {
                        @Override
                        public void onSuccess(UserEntity userEntity) {
                            Log.d(TAG, "User has been set");
                            user = userEntity;
                            testsThemesViewModel.getTestsThemes()
                                    .subscribeOn(Schedulers.io())
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

        RecyclerView recyclerView = view.findViewById(R.id.fragment_tests_theme_recycler_view);
        TextView noInternet = view.findViewById(R.id.fragment_tests_no_internet);
        FloatingActionButton infoBtn = view.findViewById(R.id.fragment_tests_btn_info);

        infoBtn.setOnClickListener(v -> new AlertDialog.Builder(getActivity())
                .setTitle("Как проходятся тесты. Инструкция для рядовых")
                .setMessage(R.string.prompt_tests_rules)
                .setPositiveButton("Понял", (dialog, which) -> dialog.dismiss() )
                .setIcon(R.drawable.book_icon_128)
                .show());

        if(hasConnection) {
            TestsThemesRecyclerAdapter.OnItemClickListener testThemeClickListener = (theme, position) -> {
                themeId = theme.getId();
                achievementId = theme.getAchievementId();
                navigateToFragment(view, "testItem");
            };

            testsThemesRecyclerAdapter = new TestsThemesRecyclerAdapter(requireContext().getApplicationContext(), testThemeClickListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(testsThemesRecyclerAdapter);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            infoBtn.setVisibility(View.GONE);
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
        showBottomNav(MainActivity.BottomNavigationView, true);

        testsThemesViewModel.getTestsThemes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<List<TestThemeEntity>>() {
                    @Override
                    public void onSuccess(List<TestThemeEntity> testThemeEntities) {
                        Log.d(TAG, "DB: set themes to adapter");
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
    public void navigateToFragment(View view, String fragmentName) {
        Bundle bundle = new Bundle();

        try {
            bundle.putString("achievementId", achievementId);
            bundle.putString("themeId", themeId);
        }
        catch (IndexOutOfBoundsException e) {
            bundle.putString("achievementId", "");
            bundle.putString("themeId", "");
        }

        if(fragmentName.equals("testItem"))
            Navigation.findNavController(view).navigate(R.id.action_testsFragment_to_testItemFragment, bundle);
    }
}