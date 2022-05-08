package by.bstu.faa.wwi_guide_mobile;

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

        NavHostFragment navHostFragment1 = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment1 != null;

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