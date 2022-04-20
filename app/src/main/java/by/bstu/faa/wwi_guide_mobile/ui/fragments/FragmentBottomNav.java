package by.bstu.faa.wwi_guide_mobile.ui.fragments;

import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public interface FragmentBottomNav {
    default void showBottomNav(BottomNavigationView bottomNavigationView, boolean flag){
        if(flag)
            bottomNavigationView.setVisibility(View.VISIBLE);
        else bottomNavigationView.setVisibility(View.GONE);
    };
}
