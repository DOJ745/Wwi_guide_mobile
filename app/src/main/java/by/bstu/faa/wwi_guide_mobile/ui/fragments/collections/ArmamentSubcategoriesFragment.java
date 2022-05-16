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
import androidx.navigation.Navigation;

import by.bstu.faa.wwi_guide_mobile.MainActivity;
import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentBottomNav;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentNavigation;

public class ArmamentSubcategoriesFragment extends Fragment implements FragmentNavigation, FragmentBottomNav {
    private final String TAG = ArmamentSubcategoriesFragment.class.getSimpleName();

    private String categoryName;

    public ArmamentSubcategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
        showBottomNav(MainActivity.BottomNavigationView, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_armament_subcategories, container, false);
        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        CardView techniqueSubcategoryAviationCard = view.findViewById(R.id.fragment_armament_subcategories_aviation);
        CardView techniqueSubcategoryNavyCard = view.findViewById(R.id.fragment_armament_subcategories_navy);
        CardView techniqueSubcategoryGroundCard = view.findViewById(R.id.fragment_armament_subcategories_ground);


        techniqueSubcategoryGroundCard.setOnClickListener(v -> {
            categoryName = CONSTANTS.TECHNIQUE_SUBCATEGORIES.GROUND;
            navigateToFragment(view, "armament_list");
        });
        techniqueSubcategoryAviationCard.setOnClickListener(v -> {
                    categoryName = CONSTANTS.TECHNIQUE_SUBCATEGORIES.AVIATION;
                    navigateToFragment(view, "armament_list");
        });

        techniqueSubcategoryNavyCard.setOnClickListener(v -> {
                    categoryName = CONSTANTS.TECHNIQUE_SUBCATEGORIES.NAVY;
                    navigateToFragment(view, "armament_list");
        });
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
        try { bundle.putString("category", categoryName); }
        catch (IndexOutOfBoundsException e) { bundle.putString("category", ""); }

        if(fragmentName.equals("armament_list"))
            Navigation.findNavController(view).navigate(R.id.action_armamentSubcategoriesFragment_to_armamentListFragment, bundle);
    }
}