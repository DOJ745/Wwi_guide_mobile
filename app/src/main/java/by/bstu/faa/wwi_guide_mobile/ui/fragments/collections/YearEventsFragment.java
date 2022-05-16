package by.bstu.faa.wwi_guide_mobile.ui.fragments.collections;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.MainActivity;
import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentBottomNav;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentNavigation;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.EventRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections.YearViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class YearEventsFragment extends Fragment implements FragmentBottomNav, FragmentNavigation {
    private final String TAG = YearEventsFragment.class.getSimpleName();

    private static final String ARG_YEAR_ID = "yearId";

    private YearViewModel yearViewModel;
    private RecyclerView recyclerView;
    private EventRecyclerAdapter eventsAdapter;

    private String yearId;
    private String eventId;

    public YearEventsFragment() {
        // Required empty public constructor
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) { yearId = getArguments().getString(ARG_YEAR_ID); }
        yearViewModel = new ViewModelProvider(this).get(YearViewModel.class);
        showBottomNav(MainActivity.BottomNavigationView, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_year_events, container, false);
        EventRecyclerAdapter.OnItemClickListener eventClickListener =
                (event, position) -> {
                    eventId = event.getId();
                    navigateToFragment(view, "event");
                };
        eventsAdapter = new EventRecyclerAdapter(requireContext().getApplicationContext(), eventClickListener);

        yearViewModel.getYearEvents(yearId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<EventEntity>>() {
                    @Override
                    public void onSuccess(List<EventEntity> entities) {
                        eventsAdapter.setItems(entities);
                        recyclerView.setAdapter(eventsAdapter);
                    }
                    @Override
                    public void onError(Throwable e) { Log.e(TAG, e.getMessage()); }
                });

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_year_events_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(eventsAdapter);

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_CREATED);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_STATE_RESTORED);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_RESUME);
        showBottomNav(MainActivity.BottomNavigationView, true);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_STOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY_VIEW);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_DETACH);
    }

    @Override
    public void navigateToFragment(View view, String fragmentName) {
        Bundle bundle = new Bundle();

        try { bundle.putString("id", eventId); }
        catch (IndexOutOfBoundsException e) { bundle.putString("id", ""); }

        if(fragmentName.equals("event"))
            Navigation.findNavController(view).navigate(R.id.action_yearEventsFragment_to_eventFragment, bundle);
    }
}