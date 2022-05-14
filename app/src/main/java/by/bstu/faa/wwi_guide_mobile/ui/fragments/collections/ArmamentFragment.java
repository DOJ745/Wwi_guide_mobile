package by.bstu.faa.wwi_guide_mobile.ui.fragments.collections;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.entities.ArmamentEntity;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentBottomNav;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentNavigation;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.ArmamentRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections.ArmamentViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ArmamentFragment extends Fragment implements FragmentBottomNav, FragmentNavigation {
    private final String TAG = ArmamentFragment.class.getSimpleName();

    private ArmamentViewModel armamentViewModel;
    private ArmamentRecyclerAdapter armamentRecyclerAdapter;
    private RecyclerView recyclerView;
    private CardView weaponsCard;
    private CardView techniqueCard;

    private String armamentId;

    public ArmamentFragment() {
        // Required empty public constructor
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        armamentViewModel = new ViewModelProvider(this).get(ArmamentViewModel.class);

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_armament, container, false);
        ArmamentRecyclerAdapter.OnItemClickListener armamentClickListener =
                (armament, position) -> armamentViewModel.getArmamentById(armament.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<ArmamentEntity>() {
                            @Override
                            public void onSuccess(ArmamentEntity entity) {
                                armamentId = entity.getId();
                                navigateToFragment(view, "armament");
                            }
                            @Override
                            public void onError(Throwable e) { Log.e(TAG, e.getMessage()); }
                        });

        armamentRecyclerAdapter = new ArmamentRecyclerAdapter(requireContext().getApplicationContext(), armamentClickListener);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        weaponsCard = view.findViewById(R.id.fragment_armament_weapon_category);
        techniqueCard = view.findViewById(R.id.fragment_armament_technique_category);
        recyclerView = view.findViewById(R.id.fragment_armament_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        weaponsCard.setOnClickListener(v ->
                armamentViewModel.getWeapons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<ArmamentEntity>>() {
                    @Override
                    public void onSuccess(List<ArmamentEntity> armamentEntities) {
                        armamentRecyclerAdapter.setItems(armamentEntities);
                        recyclerView.setAdapter(armamentRecyclerAdapter);
                        weaponsCard.setVisibility(View.GONE);
                        techniqueCard.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e) { }
                }));

        techniqueCard.setOnClickListener(v ->
                armamentViewModel.getTechnique()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<List<ArmamentEntity>>() {
                            @Override
                            public void onSuccess(List<ArmamentEntity> armamentEntities) {
                                armamentRecyclerAdapter.setItems(armamentEntities);
                                recyclerView.setAdapter(armamentRecyclerAdapter);
                                weaponsCard.setVisibility(View.GONE);
                                techniqueCard.setVisibility(View.GONE);
                            }
                            @Override
                            public void onError(Throwable e) { }
                        }));

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

    @Override
    public void navigateToFragment(View view, String fragmentName) {
        Bundle bundle = new Bundle();

        try { bundle.putString("id", armamentId); }
        catch (IndexOutOfBoundsException e) { bundle.putString("id", ""); }

        if(fragmentName.equals("armament"))
            Navigation.findNavController(view).navigate(R.id.action_armamentFragment_to_armamentItemFragment, bundle);
    }
}