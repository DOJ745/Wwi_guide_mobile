package by.bstu.faa.wwi_guide_mobile.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AchievementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AchievementFragment extends Fragment {
    private final String ACHIEVEMENTS_FRAGMENT = "ACHIEVEMENTS FRAGMENT";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AchievementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AchievementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AchievementFragment newInstance(String param1, String param2) {
        AchievementFragment fragment = new AchievementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d(ACHIEVEMENTS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(ACHIEVEMENTS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return inflater.inflate(R.layout.fragment_achievement, container, false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(ACHIEVEMENTS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_STATE_RESTORED);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(ACHIEVEMENTS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(ACHIEVEMENTS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(ACHIEVEMENTS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(ACHIEVEMENTS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_STOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(ACHIEVEMENTS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY_VIEW);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(ACHIEVEMENTS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(ACHIEVEMENTS_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DETACH);
    }
}