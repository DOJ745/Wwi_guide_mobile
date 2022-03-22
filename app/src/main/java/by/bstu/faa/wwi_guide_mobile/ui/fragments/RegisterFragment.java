package by.bstu.faa.wwi_guide_mobile.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.RankDto;
import by.bstu.faa.wwi_guide_mobile.ui.adapters.RankSpinnerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.adapters.YearRecyclerAdapter;
import by.bstu.faa.wwi_guide_mobile.view_models.RegisterViewModel;
import by.bstu.faa.wwi_guide_mobile.view_models.YearViewModel;

public class RegisterFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RegisterViewModel registerViewModel;
    private RankSpinnerAdapter rankSpinnerAdapter;
    private TokenData token;

    public RegisterFragment() {
        // Required empty public constructor
    }


    /**
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

        List<RankDto> temp = new ArrayList<>();
        rankSpinnerAdapter = new RankSpinnerAdapter(this.getContext(),
                R.layout.rank_row, temp);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.init();

        registerViewModel.getElementsDtoResponseLiveData().observe(this, ranksResponse -> {
            if (ranksResponse != null) {
                rankSpinnerAdapter.setItems(ranksResponse);
            }
        });

        token = new TokenData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_fragment, container, false);

        Spinner rankSpinner = view.findViewById(R.id.rank_spinner);

        rankSpinner.setAdapter(rankSpinnerAdapter);
        rankSpinner.setPromptId(R.string.id_for_spinner);

        Button getRanksButton = view.findViewById(R.id.reg_fragment_button);
        token.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxZTY4N2ZhZTIzZWIzMGM3Y2QyYjEyMCIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTY0Nzk0MDkwNSwiZXhwIjoxNjQ3OTU1MzA1fQ.aKRK9YOf0qANCCjOB1vYqZG-Lp_RpckvsH1MaxTquxk");
        getRanksButton.setOnClickListener(v -> registerViewModel.getElements(token));

        //rankSpinner.setSelection(2, true);

        return view;
    }
}