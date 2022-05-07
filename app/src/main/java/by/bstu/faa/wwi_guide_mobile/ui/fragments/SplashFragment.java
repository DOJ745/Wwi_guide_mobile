package by.bstu.faa.wwi_guide_mobile.ui.fragments;

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
import androidx.navigation.Navigation;

import org.modelmapper.ModelMapper;


import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.security.SecurePreferences;
import by.bstu.faa.wwi_guide_mobile.view_models.SplashViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SplashFragment extends Fragment {
    private final String SPLASH_FRAGMENT = "SPLASH FRAGMENT";

    private SecurePreferences preferences;
    private SplashViewModel splashViewModel;
    private String FIRST_LAUNCH;
    private boolean hasConnection;

    private TextView textPrompt;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public SplashFragment() {
        // Required empty public constructor
        Log.d(SPLASH_FRAGMENT, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        hasConnection = RetrofitService.hasConnection(requireContext().getApplicationContext());
        super.onCreate(savedInstanceState);
        Log.d(SPLASH_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);

        preferences = SecurePreferences.getInstance(requireContext().getApplicationContext());
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        splashViewModel.getCountries();
        splashViewModel.getAchievement();
        splashViewModel.getRanks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        splashViewModel.getCountriesRepoResponse().observe(getViewLifecycleOwner(), res -> {
            if (res != null) {
                mDisposable.add(splashViewModel.insertOrUpdateCountries(res)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // On complete
                                () -> Log.d(SPLASH_FRAGMENT, "DB: Countries has been written into database"),
                                // On error
                                err -> Log.e(SPLASH_FRAGMENT, "Unable to insert countries", err))
                );
            }
        });

        /*splashViewModel.getAchievementsRepoResponse().observe(getViewLifecycleOwner(), res -> {
            if (res != null) {
                splashViewModel.setResAchievements(res);
                mDisposable.add(splashViewModel.insertOrUpdateAchievements(res)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // On complete
                                () -> {
                                    Log.d(SPLASH_FRAGMENT, "DB: Achievements has been written into database");

                                    mDisposable.add(splashViewModel.getAchievementsFromDB()
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    // On complete
                                                    data -> {
                                                        Log.d(SPLASH_FRAGMENT, "DB: Received current achievements");
                                                        splashViewModel.setCurrentAchievements(data);
                                                    },
                                                    // On error
                                                    err -> Log.e(SPLASH_FRAGMENT, "Unable to get achievements", err))
                                    );
                                },
                                // On error
                                err -> Log.e(SPLASH_FRAGMENT, "Unable to insert achievements", err))
                );
            }
        });*/

        splashViewModel.getRanksRepoResponse().observe(getViewLifecycleOwner(), res -> {
            if (res != null) {
                mDisposable.add(splashViewModel.insertOrUpdateRanks(res)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // On complete
                                () -> Log.d(SPLASH_FRAGMENT, "DB: Ranks has been written into database"),
                                // On error
                                err -> Log.e(SPLASH_FRAGMENT, "Unable to insert ranks", err))
                );
            }
        });

        splashViewModel.getLoginRepoResponse().observe(getViewLifecycleOwner(), res -> {
            if (res != null) {
                Log.d(SPLASH_FRAGMENT, "GET LOGIN RESPONSE");
                if (!res.getMsgStatus().equals("") && res.getMsgError() == null) {
                    if (CONSTANTS.WEB_APP_SUCCESS_RESPONSES.LOGIN_SUCCESS.equals(res.getMsgStatus())) {
                        Log.d(SPLASH_FRAGMENT, "SUCCESS LOGIN");

                        ModelMapper mapper = new ModelMapper();
                        UserDto userData = mapper.map(res, UserDto.class);
                        String token = res.getToken();
                        preferences.put("token", token);
                        Log.d(SPLASH_FRAGMENT, "Token has been saved into shared pref: " + token);

                        mDisposable.add(splashViewModel.insertOrUpdateUser(userData)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        // On complete
                                        () -> {
                                            Log.d(SPLASH_FRAGMENT, "DB: User has been written into database");

                                            /*mDisposable.add(splashViewModel.deleteOldAchievements(
                                                    splashViewModel.getCurrentAchievements(),
                                                    splashViewModel.getResAchievements())
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(
                                                            // On complete
                                                            () -> Log.d(SPLASH_FRAGMENT, "DB: Deleted old achievements"),
                                                            // On error
                                                            err -> Log.e(SPLASH_FRAGMENT, "Unable to delete achievements", err))
                                            );*/
                                        },
                                        // On error
                                        err -> Log.e(SPLASH_FRAGMENT, "Unable to insert user", err))
                        );
                        Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_yearsFragment, null);
                    }
                }
                else
                    Log.e(SPLASH_FRAGMENT, "Cannot get response!");
            }
        });

        if(hasConnection) {
            Log.d(SPLASH_FRAGMENT, "We have internet connection");

            FIRST_LAUNCH = "1";
            String FIRST_LAUNCH_KEY = "FIRST_LAUNCH";
            preferences.removeValue(FIRST_LAUNCH_KEY);
            preferences.put(FIRST_LAUNCH_KEY, FIRST_LAUNCH);
            checkUserAndConnection(true, view);
        }
        else checkUserAndConnection(false, view);

        Log.d(SPLASH_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        textPrompt = view.findViewById(R.id.fragment_splash_text_prompt);
        Button retryButton = view.findViewById(R.id.fragment_splash_retry_button);
        Button exitButton = view.findViewById(R.id.fragment_splash_exit_button);

        retryButton.setOnClickListener(v -> {
            hasConnection = RetrofitService.hasConnection(requireContext().getApplicationContext());
            checkUserAndConnection(hasConnection, view);
        });
        exitButton.setOnClickListener(v -> getActivity().finish());

        Log.d(SPLASH_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_CREATED);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(SPLASH_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_STATE_RESTORED);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(SPLASH_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(SPLASH_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(SPLASH_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        //mDisposable.clear();
        splashViewModel.getAchievementRepo().getMDisposable().clear();
        Log.d(SPLASH_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_STOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(SPLASH_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY_VIEW);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(SPLASH_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(SPLASH_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DETACH);
    }

    private void loginWithSavedUser() {
        LoginData data = new LoginData();
        data.setLogin(this.preferences.getString("login"));
        data.setPassword(this.preferences.getString("password"));
        splashViewModel.loginUser(data);
    }

    private void checkUserAndConnection(boolean hasConnection, View view) {
        mDisposable.add(splashViewModel.getUserFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    if(hasConnection) {
                        if(user.size() > 0) {
                            Log.d(SPLASH_FRAGMENT, "DB ONLINE: user has been found");
                            loginWithSavedUser();
                        }
                        else Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment, null);
                    }
                    else {
                        if(user.size() > 0) {
                            Log.d(SPLASH_FRAGMENT, "DB OFFLINE: user has been found");
                            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_yearsFragment, null);
                        }
                        else {
                            if(preferences.getString("FIRST_LAUNCH").equals("1"))
                                textPrompt.setText(R.string.prompt_first_launch_no_internet_connection);
                            else
                                textPrompt.setText(R.string.prompt_no_internet_connection);
                        }
                    }

                }, throwable -> Log.e(SPLASH_FRAGMENT, "Unable to get user", throwable)));
    }
}