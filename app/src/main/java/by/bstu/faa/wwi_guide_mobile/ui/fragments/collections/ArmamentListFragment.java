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
import by.bstu.faa.wwi_guide_mobile.database.entities.ArmamentEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentBottomNav;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentNavigation;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.ArmamentRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.collections.ArmamentViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ArmamentListFragment extends Fragment implements FragmentNavigation, FragmentBottomNav {
    private final String TAG = ArmamentListFragment.class.getSimpleName();

    private static final String ARG_CATEGORY = "category";

    private String armamentId;
    private String categoryName;
    private RecyclerView recyclerView;
    private ArmamentViewModel armamentViewModel;
    private ArmamentRecyclerAdapter armamentRecyclerAdapter;

    public ArmamentListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryName = getArguments().getString(ARG_CATEGORY);
        }
        armamentViewModel = new ViewModelProvider(this).get(ArmamentViewModel.class);
        showBottomNav(MainActivity.BottomNavigationView, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_armament_list, container, false);
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
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_armament_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        switch (categoryName) {
            case CONSTANTS.TECHNIQUE_SUBCATEGORIES.GROUND:
                armamentViewModel.getUserFromDB()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableMaybeObserver<UserEntity>() {
                            @Override
                            public void onSuccess(UserEntity entity) {
                                armamentViewModel.getTechniqueBySub(CONSTANTS.TECHNIQUE_SUBCATEGORIES.GROUND)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableSingleObserver<List<ArmamentEntity>>() {
                                            @Override
                                            public void onSuccess(List<ArmamentEntity> armamentEntities) {
                                                createElementsList(armamentEntities, entity);
                                            }
                                            @Override
                                            public void onError(Throwable e) { Log.e(TAG, e.getMessage()); }
                                        });
                            }
                            @Override
                            public void onError(Throwable e) { Log.e(TAG, e.getMessage()); }
                            @Override
                            public void onComplete() { }
                        });
                break;

            case CONSTANTS.TECHNIQUE_SUBCATEGORIES.AVIATION:
                armamentViewModel.getUserFromDB()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableMaybeObserver<UserEntity>() {
                            @Override
                            public void onSuccess(UserEntity entity) {
                                armamentViewModel.getTechniqueBySub(CONSTANTS.TECHNIQUE_SUBCATEGORIES.AVIATION)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableSingleObserver<List<ArmamentEntity>>() {
                                            @Override
                                            public void onSuccess(List<ArmamentEntity> armamentEntities) {
                                                createElementsList(armamentEntities, entity);
                                            }
                                            @Override
                                            public void onError(Throwable e) { Log.e(TAG, e.getMessage()); }
                                        });
                            }
                            @Override
                            public void onError(Throwable e) { Log.e(TAG, e.getMessage()); }
                            @Override
                            public void onComplete() { }
                        });
                break;

            case CONSTANTS.TECHNIQUE_SUBCATEGORIES.NAVY:
                armamentViewModel.getUserFromDB()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableMaybeObserver<UserEntity>() {
                            @Override
                            public void onSuccess(UserEntity entity) {
                                armamentViewModel.getTechniqueBySub(CONSTANTS.TECHNIQUE_SUBCATEGORIES.NAVY)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableSingleObserver<List<ArmamentEntity>>() {
                                            @Override
                                            public void onSuccess(List<ArmamentEntity> armamentEntities) {
                                                createElementsList(armamentEntities, entity);
                                            }
                                            @Override
                                            public void onError(Throwable e) { Log.e(TAG, e.getMessage()); }
                                        });

                            }
                            @Override
                            public void onError(Throwable e) { Log.e(TAG, e.getMessage()); }
                            @Override
                            public void onComplete() { }
                        });
                break;

            default:
                armamentViewModel.getUserFromDB()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableMaybeObserver<UserEntity>() {
                            @Override
                            public void onSuccess(UserEntity entity) {
                                armamentViewModel.getWeapons()
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableSingleObserver<List<ArmamentEntity>>() {
                                            @Override
                                            public void onSuccess(List<ArmamentEntity> armamentEntities) {
                                                createElementsList(armamentEntities, entity);
                                            }
                                            @Override
                                            public void onError(Throwable e) { }
                                        });
                            }
                            @Override
                            public void onError(Throwable e) { }
                            @Override
                            public void onComplete() { }
                        });
                break;
        }
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

    private void createElementsList(List<ArmamentEntity> armamentEntities, UserEntity entity) {
        armamentRecyclerAdapter.setItems(armamentEntities);
        for(ArmamentEntity armament: armamentRecyclerAdapter.getItems()){
            for(String achievementId: entity.getAchievements()){
                if(armament.getAchievementId().equals(achievementId))
                    armament.setTitle(armament.getTitle() + " (ПРОЧИТАНО)");
            }
        }
        armamentRecyclerAdapter.setItems(armamentRecyclerAdapter.getItems());
        recyclerView.setAdapter(armamentRecyclerAdapter);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToFragment(View view, String fragmentName) {
        Bundle bundle = new Bundle();
        try { bundle.putString("id", armamentId); }
        catch (IndexOutOfBoundsException e) { bundle.putString("id", ""); }

        if(fragmentName.equals("armament"))
            Navigation.findNavController(view).navigate(R.id.action_armamentListFragment_to_armamentItemFragment, bundle);
    }
}