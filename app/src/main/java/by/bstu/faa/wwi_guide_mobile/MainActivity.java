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
import androidx.navigation.fragment.NavHostFragment;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.security.SecurePreferences;
import by.bstu.faa.wwi_guide_mobile.view_models.MainViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

// TODO: set timer for automatic token refresh if we have internet connection
// TODO: check for "Remember me" to navigate user to main fragment

public class MainActivity extends AppCompatActivity {

    private String FIRST_LAUNCH;
    private boolean hasConnection = false;
    private MainViewModel mainViewModel;

    private NavHostFragment navHostFragment;
    private TextView textPrompt;
    private ImageView img;
    private Button retryButton;
    private Button exitButton;
    private FragmentContainerView fragmentContainerView;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hasConnection = RetrofitService.hasConnection(this);

        setTheme(R.style.Theme_Wwi_guide_mobile);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SecurePreferences preferences = SecurePreferences.getInstance(this);
        String FIRST_LAUNCH_KEY = "FIRST_LAUNCH";
        if(preferences.getString(FIRST_LAUNCH_KEY) == null )
            FIRST_LAUNCH = "0";
        else if (preferences.getString(FIRST_LAUNCH_KEY) != null)
            FIRST_LAUNCH = preferences.getString(FIRST_LAUNCH_KEY);
        else preferences.put(FIRST_LAUNCH_KEY, "1");

        Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, "First launch check: " + FIRST_LAUNCH);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;

        fragmentContainerView = findViewById(R.id.nav_host_fragment);
        textPrompt = findViewById(R.id.activity_main_text_prompt);
        img = findViewById(R.id.activity_main_img);
        retryButton = findViewById(R.id.activity_main_retry_button);
        exitButton = findViewById(R.id.activity_main_exit_button);

        retryButton.setOnClickListener(view -> checkUserAndConnection());
        exitButton.setOnClickListener(view -> finish());

        if(hasConnection) {
            // Going to login, because it's home fragment
            Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, "We have internet connection!");

            FIRST_LAUNCH = "1";
            preferences.removeValue(FIRST_LAUNCH_KEY);
            preferences.put(FIRST_LAUNCH_KEY, FIRST_LAUNCH);

            retryButton.setEnabled(false);
            exitButton.setEnabled(false);
        }
        else {
            Log.e(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, "No internet connection!");
            if(FIRST_LAUNCH.equals("0")) {
                makeElementsGone(textPrompt, img, retryButton, exitButton, false);
                textPrompt.setText(R.string.prompt_first_launch_no_internet_connection);
                fragmentContainerView.setVisibility(View.GONE);
            }
            else {
                makeElementsGone(textPrompt, img, retryButton, exitButton, false);
                textPrompt.setText(R.string.prompt_no_internet_connection);
                fragmentContainerView.setVisibility(View.GONE);
            }
        }
        Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_START);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_RESTART);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_RESUME);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_PAUSE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_STOP);
        mDisposable.clear();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY);
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

    private void checkUserAndConnection() {
        mDisposable.add(mainViewModel.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, "checkUserAndConnection");
                    hasConnection = RetrofitService.hasConnection(this);
                    if(hasConnection && user.size() > 0) {
                        fragmentContainerView.setVisibility(View.VISIBLE);
                        makeElementsGone(textPrompt, img, retryButton, exitButton, true);
                        navHostFragment.getNavController().navigate(R.id.loginFragment, null);
                    }
                    else if (!hasConnection && user.size() == 0) {
                        if(FIRST_LAUNCH.equals("1"))
                            textPrompt.setText(R.string.prompt_first_launch_no_internet_connection);
                        else
                            textPrompt.setText(R.string.prompt_no_internet_connection);
                    }
                    else {
                        fragmentContainerView.setVisibility(View.VISIBLE);
                        makeElementsGone(textPrompt, img, retryButton, exitButton, true);
                        navHostFragment.getNavController().navigate(R.id.registerFragment, null);
                    }
                }, throwable -> Log.e(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, "Unable to get user", throwable)));
    }
}