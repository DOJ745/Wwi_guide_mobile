package by.bstu.faa.wwi_guide_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Wwi_guide_mobile);
        super.onCreate(savedInstanceState);
        Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
        setContentView(R.layout.activity_main);

        if(RetrofitService.hasConnection(this)){
            Log.d(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, "We have internet connection!");
        }
        else {
            Log.e(CONSTANTS.LOG_TAGS.MAIN_ACTIVITY, "No internet!");
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
}