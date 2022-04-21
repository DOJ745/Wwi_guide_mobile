package by.bstu.faa.wwi_guide_mobile.ui.fragments.collections;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import by.bstu.faa.wwi_guide_mobile.MainActivity;
import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.EventDto;
import by.bstu.faa.wwi_guide_mobile.ui.adapters.EventsRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.adapters.YearsRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentBottomNav;
import by.bstu.faa.wwi_guide_mobile.view_models.YearViewModel;

public class YearsFragment extends Fragment implements FragmentBottomNav {
    private final String YEARS_FRAGMENT = "YEARS FRAGMENT";

    private YearViewModel yearViewModel;
    private YearsRecyclerAdapter yearAdapter;
    private EventsRecyclerAdapter eventsAdapter;

    private TokenData token;

    public YearsFragment() {
        // Required empty public constructor
        Log.d(YEARS_FRAGMENT, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        yearAdapter = new YearsRecyclerAdapter();
        EventsRecyclerAdapter.OnEventClickListener eventClickListener =
                (event, position) -> Toast.makeText(requireContext().getApplicationContext(),
                        "You chose event with title " + event.getTitle(),
                        Toast.LENGTH_SHORT).show();
        // создаем адаптер
        eventsAdapter = new EventsRecyclerAdapter(requireContext().getApplicationContext(), eventClickListener);
        yearViewModel = new ViewModelProvider(this).get(YearViewModel.class);
        yearViewModel.init();

        yearViewModel.getElementsDtoResponseLiveData().observe(this, yearsResponse -> {
            if (yearsResponse != null) {
                yearAdapter.setItems(yearsResponse);
            }
        });

        token = new TokenData();
        Log.d(YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        yearViewModel.getElements(token);
        View view = inflater.inflate(R.layout.fragment_years, container, false);

        Log.d(YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.years_fragment_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(yearAdapter);

        Button getYearsButton = view.findViewById(R.id.years_fragment_button);
        showBottomNav(MainActivity.BottomNavigationView, true);

        token.setToken("");
        getYearsButton.setOnClickListener(v -> {

            getYearsButton.setVisibility(View.VISIBLE);
            // устанавливаем для списка адаптер
            recyclerView.setAdapter(eventsAdapter);

        });

        Log.d(YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_CREATED);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_STATE_RESTORED);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_STOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY_VIEW);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(YEARS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DETACH);
    }
}