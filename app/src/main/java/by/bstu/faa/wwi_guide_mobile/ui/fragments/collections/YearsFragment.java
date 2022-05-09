package by.bstu.faa.wwi_guide_mobile.ui.fragments.collections;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.MainActivity;
import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.YearEntity;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentBottomNav;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.EventsRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.YearsRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections.YearViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class YearsFragment extends Fragment implements FragmentBottomNav {
    private final String TAG = YearsFragment.class.getSimpleName();

    private YearsRecyclerAdapter yearAdapter;
    private EventsRecyclerAdapter eventsAdapter;
    private YearViewModel yearViewModel;
    private RecyclerView recyclerView;

    public YearsFragment() {
        // Required empty public constructor
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        yearViewModel = new ViewModelProvider(this).get(YearViewModel.class);

        EventsRecyclerAdapter.OnItemClickListener eventClickListener =
                (event, position) -> Toast.makeText(requireContext().getApplicationContext(),
                        "You chose event with title " + event.getTitle(),
                        Toast.LENGTH_SHORT).show();

        eventsAdapter = new EventsRecyclerAdapter(requireContext().getApplicationContext(), eventClickListener);

        YearsRecyclerAdapter.OnItemClickListener yearClickListener =
                (year, position) -> {
                    Toast.makeText(requireContext().getApplicationContext(),
                            "You chose year with title " + year.getTitle(),
                            Toast.LENGTH_SHORT).show();

                    yearViewModel.getYearEvents(year.getId())
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
                };

        yearAdapter = new YearsRecyclerAdapter(requireContext().getApplicationContext(), yearClickListener);

        yearViewModel.getYearRepo().getEntitiesFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<List<YearEntity>>() {
                    @Override
                    public void onSuccess(List<YearEntity> entities) {
                        yearAdapter.setItems(entities);
                        Log.d(TAG, "Set items to adapter");
                    }
                    @Override
                    public void onError(Throwable e) { }
                    @Override
                    public void onComplete() { Log.d(TAG, "onComplete"); }
                });

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_years, container, false);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_years_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(yearAdapter);

        showBottomNav(MainActivity.BottomNavigationView, true);

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
}