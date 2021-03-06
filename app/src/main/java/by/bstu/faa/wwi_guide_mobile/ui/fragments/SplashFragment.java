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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.modelmapper.ModelMapper;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.api_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.security.SecurePreferences;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.SplashViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;
import lombok.SneakyThrows;

public class SplashFragment extends Fragment {
    private final String TAG = SplashFragment.class.getSimpleName();

    private SecurePreferences preferences;
    private SplashViewModel splashViewModel;
    private boolean hasConnection;

    private TextView textPrompt;
    private Button retryButton;
    private Button exitButton;

    private UserEntity user;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public SplashFragment() {
        // Required empty public constructor
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        hasConnection = RetrofitService.hasConnection(requireContext().getApplicationContext());
        ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);

        preferences = SecurePreferences.getInstance(requireContext().getApplicationContext());
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        splashViewModel.getAllData();

        splashViewModel.getUserFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<UserEntity>() {
                    @Override
                    public void onSuccess(UserEntity userEntity) {
                        Log.d(TAG, "DB: user has been found");
                        user = userEntity;
                    }
                    @Override
                    public void onError(Throwable e) { }
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "DB: NO user has been found");
                        user = null;
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        splashViewModel.getLoginRepoResponse().observe(getViewLifecycleOwner(), res -> {
            if (res != null) {
                Log.d(TAG, "GET LOGIN RESPONSE");
                if (!res.getMsgStatus().equals("") && res.getMsgError() == null) {
                    if (CONSTANTS.WEB_APP_SUCCESS_RESPONSES.LOGIN_SUCCESS.equals(res.getMsgStatus())) {
                        Log.d(TAG, "SUCCESS LOGIN");

                        ModelMapper mapper = new ModelMapper();
                        UserDto userData = mapper.map(res, UserDto.class);
                        UserEntity userDataEntity = mapper.map(userData, UserEntity.class);
                        String token = res.getToken();
                        preferences.put("token", token);
                        Log.d(TAG, "Token has been saved into shared pref: " + token);

                        if(!userDataEntity.equals(user))
                            userData = mapper.map(user, UserDto.class);

                        mDisposable.add(splashViewModel.insertOrUpdateUser(userData)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        // On complete
                                        () -> Log.d(TAG, "DB: User has been written into database"),
                                        // On error
                                        err -> Log.e(TAG, "Unable to insert user", err))
                        );
                        Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_yearsFragment, null);
                    }
                }
                else
                    Log.e(TAG, "Cannot get response!");
            }
        });

        if(hasConnection) {
            Log.d(TAG, "We have internet connection");
            String FIRST_LAUNCH = "1";
            String FIRST_LAUNCH_KEY = "FIRST_LAUNCH";
            preferences.removeValue(FIRST_LAUNCH_KEY);
            preferences.put(FIRST_LAUNCH_KEY, FIRST_LAUNCH);
            checkUserAndConnection(true, view);
        }
        else checkUserAndConnection(false, view);

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        textPrompt = view.findViewById(R.id.fragment_splash_text_prompt);
        retryButton = view.findViewById(R.id.fragment_splash_retry_button);
        exitButton = view.findViewById(R.id.fragment_splash_exit_button);

        retryButton.setOnClickListener(v -> {
            hasConnection = RetrofitService.hasConnection(requireContext().getApplicationContext());
            checkUserAndConnection(hasConnection, view);
        });
        exitButton.setOnClickListener(v -> getActivity().finish());

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_CREATED);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_STATE_RESTORED);
    }

    @SneakyThrows
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
        mDisposable.clear();
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

    private void loginWithSavedUser() {
        LoginData data = new LoginData();
        data.setLogin(this.preferences.getString("login"));
        data.setPassword(this.preferences.getString("password"));
        splashViewModel.loginUser(data);
    }

    private void checkUserAndConnection(boolean hasConnection, View view) {
        splashViewModel.getUserFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<UserEntity>() {
                    @Override
                    public void onSuccess(UserEntity userEntity) {
                        if(hasConnection) {
                            Log.d(TAG, "DB ONLINE: user has been found");
                            retryButton.setVisibility(View.GONE);
                            exitButton.setVisibility(View.GONE);
                            loginWithSavedUser();
                        }
                        else {
                            Log.d(TAG, "DB OFFLINE: user has been found");
                            retryButton.setVisibility(View.GONE);
                            exitButton.setVisibility(View.GONE);
                            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_yearsFragment, null);
                        }
                    }
                    @Override
                    public void onError(Throwable e) { Log.e(TAG, "Unable to get user", e); }
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "No user has been found");
                        if(hasConnection) {
                            retryButton.setVisibility(View.GONE);
                            exitButton.setVisibility(View.GONE);
                            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment, null);
                        }
                        else {
                            retryButton.setVisibility(View.VISIBLE);
                            exitButton.setVisibility(View.VISIBLE);
                            if(preferences.getString("FIRST_LAUNCH").equals("1"))
                                textPrompt.setText(R.string.prompt_first_launch_no_internet_connection);
                            else
                                textPrompt.setText(R.string.prompt_no_internet_connection);
                        }
                    }
                });
    }
}