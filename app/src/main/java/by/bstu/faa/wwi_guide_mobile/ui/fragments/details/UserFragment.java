package by.bstu.faa.wwi_guide_mobile.ui.fragments.details;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.MainActivity;
import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.RankEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.security.SecurePreferences;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentBottomNav;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentNavigation;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.AchievementsRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.UserViewModel;
import io.reactivex.MaybeObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;


public class UserFragment extends Fragment implements FragmentBottomNav, FragmentNavigation {
    private final String TAG = UserFragment.class.getSimpleName();

    private UserViewModel userViewModel;
    private TextView rankName;
    private TextView loginTextView;
    private AchievementsRecyclerAdapter achievementsRecyclerAdapter;
    private RecyclerView achievementRecyclerView;
    private ImageView rankImg;
    private ProgressBar rankProgress;
    private TextView rankProgressPoints;
    private ProgressBar achievementProgress;
    private TextView achievementProgressEarned;

    private SecurePreferences preferences;

    public UserFragment() {
        // Required empty public constructor
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);

        preferences = SecurePreferences.getInstance(requireContext().getApplicationContext());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        achievementsRecyclerAdapter = new AchievementsRecyclerAdapter(requireContext().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button logOutBtn = view.findViewById(R.id.fragment_user_logout_btn);
        rankImg = view.findViewById(R.id.fragment_user_rank_img);
        rankName = view.findViewById(R.id.fragment_user_rank_name);
        rankProgressPoints = view.findViewById(R.id.fragment_user_rank_points);
        loginTextView = view.findViewById(R.id.fragment_user_login);
        rankProgress = view.findViewById(R.id.fragment_user_rank_progress);
        achievementProgress = view.findViewById(R.id.fragment_user_achievements_progress);
        achievementProgressEarned = view.findViewById(R.id.fragment_user_achievements_earned);
        achievementRecyclerView = view.findViewById(R.id.fragment_user_achievement);
        achievementRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userViewModel.getUserDataFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<UserEntity>() {
                    @Override
                    public void onSuccess(UserEntity userEntity) {
                        loginTextView.setText(userEntity.getLogin());

                        userViewModel.setUserEntity(userEntity);
                        userViewModel.getAchievementDao().getAll().subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new MaybeObserver<List<AchievementEntity>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        Log.d(TAG, "subscribed; is disposed: " + d.isDisposed());
                                    }
                                    @Override
                                    public void onSuccess(List<AchievementEntity> entities) {
                                        achievementProgress.setMax(entities.size());
                                        achievementProgress.setProgress(userEntity.getAchievements().size());

                                        ArrayList<AchievementEntity> userAchievements = new ArrayList<>();
                                        for (AchievementEntity entity : entities) {
                                            if (userEntity.getAchievements().contains(entity.getId()))
                                                userAchievements.add(entity);
                                        }

                                        achievementProgressEarned.setText(userAchievements.size() + "/" + entities.size());
                                        achievementsRecyclerAdapter.setItems(userAchievements);
                                        achievementRecyclerView.setAdapter(achievementsRecyclerAdapter);
                                    }

                                    @Override
                                    public void onError(Throwable e) { }
                                    @Override
                                    public void onComplete() { }
                                });

                        userViewModel.getUserRankByIdFromDB(userEntity.getRankId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<RankEntity>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        Log.d(TAG, "subscribed; is disposed: " + d.isDisposed());
                                    }
                                    @Override
                                    public void onSuccess(RankEntity rankEntity) {
                                        Glide
                                                .with(view)
                                                .asBitmap()
                                                .load(rankEntity.getImg())
                                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                                .placeholder(R.drawable.placeholder)
                                                .error(R.drawable.error)
                                                .into(rankImg);

                                        rankName.setText(rankEntity.getName());
                                        rankProgressPoints.setText(userEntity.getScore() + "/" + rankEntity.getPoints() + "xp");
                                        rankProgress.setMax(rankEntity.getPoints());
                                        rankProgress.setProgress(userEntity.getScore());
                                    }
                                    @Override
                                    public void onError(Throwable e) { Log.e(TAG, e.getMessage()); }
                                });
                    }

                    @Override
                    public void onError(Throwable e) { Log.e(TAG, e.getMessage()); }
                    @Override
                    public void onComplete() { }
                });

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_CREATED);
        logOutBtn.setOnClickListener(v -> new AlertDialog.Builder(getActivity())
                .setTitle("Выход их приложения")
                .setMessage("При выходе из приложения вам придется заново вводить все данные." +
                        " Вы уверены что хотите это сделать?")
                .setPositiveButton("Да", (dialog, which) -> userViewModel.logoutUser(userViewModel.getUserEntity())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                showBottomNav(MainActivity.BottomNavigationView, false);
                                preferences.clear();
                                navigateToFragment(view, "login");
                            }
                            @Override
                            public void onError(Throwable e) { Log.e(TAG, e.getMessage()); }
                        }))
                .setNegativeButton("Нет", null)
                .setIcon(R.drawable.warning)
                .show());
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
    public void navigateToFragment(View view, String fragmentName) {
        if ("login".equals(fragmentName))
            Navigation.findNavController(view).navigate(R.id.action_userFragment_to_loginFragment, null);
    }
}