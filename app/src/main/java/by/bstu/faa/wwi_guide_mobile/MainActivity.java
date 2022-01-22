package by.bstu.faa.wwi_guide_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import by.bstu.faa.wwi_guide_mobile.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}