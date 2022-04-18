package by.bstu.faa.wwi_guide_mobile.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.ui.adapters.YearRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.view_models.YearViewModel;

public class YearsFragment extends Fragment implements FragmentMethods{

    private YearViewModel yearViewModel;
    private YearRecyclerAdapter yearAdapter;
    private BottomNavigationView bottomNavigationView;

    private TokenData token;

    public YearsFragment() {
        // Required empty public constructor
        Log.d(CONSTANTS.LOG_TAGS.YEARS_FRAGMENT, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        yearAdapter = new YearRecyclerAdapter();
        yearViewModel = new ViewModelProvider(this).get(YearViewModel.class);
        yearViewModel.init();

        yearViewModel.getElementsDtoResponseLiveData().observe(this, yearsResponse -> {
            if (yearsResponse != null) {
                yearAdapter.setItems(yearsResponse);
            }
        });

        token = new TokenData();
        Log.d(CONSTANTS.LOG_TAGS.YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_years, container, false);

        Log.d(CONSTANTS.LOG_TAGS.YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        RecyclerView yearRecyclerView = view.findViewById(R.id.years_fragment_year_list);
        yearRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        yearRecyclerView.setAdapter(yearAdapter);
        Button getYearsButton = view.findViewById(R.id.years_fragment_button);
        bottomNavigationView = view.findViewById(R.id.years_fragment_bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch(id) {
                case R.id.nav_action_tests:
                    navigateToFragment(view, "tests");
                    return true;
                case R.id.nav_action_achievements:
                    navigateToFragment(view, "achievements");
                    return true;
                case R.id.nav_action_user_info:
                    navigateToFragment(view, "user");
                    return true;
                case R.id.nav_action_years:
                    navigateToFragment(view, "years");
                    return true;
                case R.id.nav_action_weapons_and_technique:
                    navigateToFragment(view, "wt");
                    return true;
            }
            return false;
        });

        token.setToken("");
        getYearsButton.setOnClickListener(v -> {
            yearViewModel.getElements(token);
            getYearsButton.setVisibility(View.GONE);
        });

        Log.d(CONSTANTS.LOG_TAGS.YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_CREATED);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(CONSTANTS.LOG_TAGS.YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_STATE_RESTORED);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(CONSTANTS.LOG_TAGS.YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(CONSTANTS.LOG_TAGS.YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(CONSTANTS.LOG_TAGS.YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(CONSTANTS.LOG_TAGS.YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_STOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(CONSTANTS.LOG_TAGS.YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY_VIEW);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(CONSTANTS.LOG_TAGS.YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(CONSTANTS.LOG_TAGS.YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DETACH);
    }

    @Override
    public void navigateToFragment(View view, String fragmentName) {
        switch (fragmentName){
            case "years":
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment, null);
                break;
            case "user":
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment, null);
                break;
            case "tests":
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment, null);
                break;
            case "wt":
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment, null);
                break;
            case "achievement":
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment, null);
                break;
        }
    }
}