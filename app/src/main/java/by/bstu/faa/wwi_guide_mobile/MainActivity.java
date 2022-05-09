package by.bstu.faa.wwi_guide_mobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.security.SecurePreferences;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    public static BottomNavigationView BottomNavigationView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Wwi_guide_mobile);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SecurePreferences preferences = SecurePreferences.getInstance(this);
        String FIRST_LAUNCH_KEY = "FIRST_LAUNCH";

        String FIRST_LAUNCH;
        if(preferences.getString(FIRST_LAUNCH_KEY) == null ){
            FIRST_LAUNCH = "0";
            preferences.put(FIRST_LAUNCH_KEY, FIRST_LAUNCH);
        }
        else preferences.put(FIRST_LAUNCH_KEY, "1");

        BottomNavigationView = findViewById(R.id.main_bottom_nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.achievementsFragment,
                R.id.testsFragment,
                R.id.yearsFragment,
                R.id.armamentFragment,
                R.id.userFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(BottomNavigationView, navController);

        BottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.achievementsFragment:
                    navController.navigate(R.id.achievementsFragment);
                    return true;
                case R.id.yearsFragment:
                    navController.navigate(R.id.yearsFragment);
                    return true;
                case R.id.testsFragment:
                    navController.navigate(R.id.testsFragment);
                    return true;
                case R.id.armamentFragment:
                    navController.navigate(R.id.armamentFragment);
                    return true;
                case R.id.userFragment:
                    navController.navigate(R.id.userFragment);
                    return true;
                default:
                    return false;
            }
        });

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_START);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_RESTART);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_RESUME);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_PAUSE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_STOP);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY);
    }
}