package by.bstu.faa.wwi_guide_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;

public class MainActivity extends AppCompatActivity {

    private boolean hasConnection = true;
    private NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Wwi_guide_mobile);
        super.onCreate(savedInstanceState);
        Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);

        hasConnection = RetrofitService.hasConnection(this);

        setContentView(R.layout.activity_main);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;

        FragmentContainerView fragmentContainerView = findViewById(R.id.nav_host_fragment);
        TextView textPrompt = findViewById(R.id.activity_main_text_prompt);
        ImageView img = findViewById(R.id.activity_main_img);
        Button retryButton = findViewById(R.id.activity_main_retry_button);
        Button exitButton = findViewById(R.id.activity_main_exit_button);

        retryButton.setOnClickListener(view -> {
            if(hasConnection) {
                fragmentContainerView.setVisibility(View.VISIBLE);
                makeElementsGone(textPrompt, img, retryButton, exitButton, true);
                navHostFragment.getNavController().navigate(R.id.loginFragment, null);
            }
        });
        exitButton.setOnClickListener(view -> finish());

        if(hasConnection){
            Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, "We have internet connection!");
            retryButton.setEnabled(false);
            exitButton.setEnabled(false);
        }
        else {
            Log.e(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, "No internet!");
            makeElementsGone(textPrompt, img, retryButton, exitButton, false);
            textPrompt.setText(R.string.prompt_no_internet_connection);
            fragmentContainerView.setVisibility(View.GONE);
        }
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
}