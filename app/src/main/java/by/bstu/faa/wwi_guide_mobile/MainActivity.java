package by.bstu.faa.wwi_guide_mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.modelmapper.ModelMapper;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.security.SecurePreferences;
import by.bstu.faa.wwi_guide_mobile.view_models.auth.LoginViewModel;
import by.bstu.faa.wwi_guide_mobile.view_models.MainViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private final String MAIN_ACTIVITY = "MAIN ACTIVITY";

    public static BottomNavigationView BottomNavigationView;

    private SecurePreferences preferences;
    private String FIRST_LAUNCH;
    private MainViewModel mainViewModel;
    //private LoginViewModel loginViewModel;

    private NavHostFragment navHostFragment;
    private TextView textPrompt;
    private ImageView img;
    private Button retryButton;
    private Button exitButton;
    private FragmentContainerView fragmentContainerView;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean hasConnection = RetrofitService.hasConnection(this);

        setTheme(R.style.Theme_Wwi_guide_mobile);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = SecurePreferences.getInstance(this);
        String FIRST_LAUNCH_KEY = "FIRST_LAUNCH";

        if(preferences.getString(FIRST_LAUNCH_KEY) == null )
            FIRST_LAUNCH = "0";
        else if (preferences.getString(FIRST_LAUNCH_KEY) != null)
            FIRST_LAUNCH = preferences.getString(FIRST_LAUNCH_KEY);
        else preferences.put(FIRST_LAUNCH_KEY, "1");

        Log.d(MAIN_ACTIVITY, "First launch check: " + FIRST_LAUNCH);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.init();
        //loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //loginViewModel.init();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;

        fragmentContainerView = findViewById(R.id.nav_host_fragment);
        textPrompt = findViewById(R.id.activity_main_text_prompt);
        img = findViewById(R.id.activity_main_img);
        retryButton = findViewById(R.id.activity_main_retry_button);
        exitButton = findViewById(R.id.activity_main_exit_button);
        BottomNavigationView = findViewById(R.id.main_bottom_nav_view);

        // Passing each menu ID as a set of IDs because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.achievementsFragment,
                R.id.testsFragment,
                R.id.yearsFragment,
                R.id.armamentFragment,
                R.id.userFragment).build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment);

        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(BottomNavigationView, navController);

        /*mainViewModel.getLoginRepoResponse().observe(this, loginResponse -> {

            if (loginResponse != null) {
                if (!loginResponse.getMsgStatus().equals("") && loginResponse.getMsgError() == null) {
                    if (CONSTANTS.WEB_APP_SUCCESS_RESPONSES.LOGIN_SUCCESS.equals(loginResponse.getMsgStatus())) {
                        //UserDto userData = new UserDto();
                        //setUserData(userData, loginResponse);
                        ModelMapper mapper = new ModelMapper();
                        UserDto userData = mapper.map(loginResponse, UserDto.class);
                        String token = loginResponse.getToken();
                        preferences.put("token", token);
                        Log.d(MAIN_ACTIVITY, "Token has been saved: " + token);

                        mDisposable.add(mainViewModel.insertOrUpdateUser(userData)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        () -> {
                                            navHostFragment.getNavController().navigate(R.id.yearsFragment);
                                            Log.d(MAIN_ACTIVITY, "DB: User has been written into database");
                                        },
                                        throwable -> Log.e(MAIN_ACTIVITY, "Unable to get username", throwable))
                        );
                    }
                }
                else
                    Log.e(MAIN_ACTIVITY, "Error!");
            }
        });*/

        retryButton.setOnClickListener(view -> checkUserAndConnection(false));
        exitButton.setOnClickListener(view -> finish());

        if(hasConnection) {
            Log.d(MAIN_ACTIVITY, "We have internet connection!");

            FIRST_LAUNCH = "1";
            preferences.removeValue(FIRST_LAUNCH_KEY);
            preferences.put(FIRST_LAUNCH_KEY, FIRST_LAUNCH);

            retryButton.setEnabled(false);
            exitButton.setEnabled(false);

            checkUserAndConnection(true);
        }
        else checkUserAndConnection(false);

        Log.d(MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_START);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_RESTART);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_RESUME);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_PAUSE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_STOP);
        mDisposable.clear();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY);
    }

    private void makeElementsGone(TextView textView, ImageView imageView,
                                     Button buttonOne, Button buttonTwo, boolean flag) {
        if (!flag) {
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            buttonOne.setVisibility(View.VISIBLE);
            buttonTwo.setVisibility(View.VISIBLE);
        }
        else {
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            buttonOne.setVisibility(View.GONE);
            buttonTwo.setVisibility(View.GONE);
        }
    }

    private void loginWithSavedUser() {
        LoginData data = new LoginData();
        data.setLogin(this.preferences.getString("login"));
        data.setPassword(this.preferences.getString("password"));
        this.mainViewModel.loginUser(data);
        navHostFragment.getNavController().navigate(R.id.yearsFragment, null);
    }

    private void checkUserAndConnection(boolean hasConnection) {
        mDisposable.add(mainViewModel.getUserFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    Log.d(MAIN_ACTIVITY, "DB: checkUserAndConnection");
                    if(hasConnection) {
                        fragmentContainerView.setVisibility(View.VISIBLE);
                        makeElementsGone(textPrompt, img, retryButton, exitButton, true);

                        if(user.size() > 0) {
                            Log.d(MAIN_ACTIVITY, "DB: checkUserAndConnection");
                            loginWithSavedUser();
                        }
                        else navHostFragment.getNavController().navigate(R.id.loginFragment, null);
                    }
                    else {
                        if(user.size() > 0) {
                            fragmentContainerView.setVisibility(View.VISIBLE);
                            makeElementsGone(textPrompt, img, retryButton, exitButton, true);
                            navHostFragment.getNavController().navigate(R.id.yearsFragment, null);
                        }
                        else {
                            if(FIRST_LAUNCH.equals("1"))
                                textPrompt.setText(R.string.prompt_first_launch_no_internet_connection);
                            else
                                textPrompt.setText(R.string.prompt_no_internet_connection);
                        }
                    }

                }, throwable -> Log.e(MAIN_ACTIVITY, "Unable to get user", throwable)));
    }

    private void loadAchievements(){}
}