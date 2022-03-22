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
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.ui.adapters.CountrySpinnerAdapter;
import by.bstu.faa.wwi_guide_mobile.view_models.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RegisterViewModel registerViewModel;
    private CountrySpinnerAdapter countrySpinnerAdapter;
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

        List<CountryDto> temp = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            CountryDto testCountry = new CountryDto();
            testCountry.setName("TEST" + i);
            testCountry.setFlagUrl("TEST");
            temp.add(testCountry);
        }

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.init();
        countrySpinnerAdapter = new CountrySpinnerAdapter(this.getContext(), R.layout.country_row, temp);

        registerViewModel.getElementsDtoResponseLiveData().observe(this, countryResponse -> {
            if (countryResponse != null) {
                //rankSpinnerAdapter = new RankSpinnerAdapter(this.getContext(), R.layout.rank_row, ranksResponse);
                countrySpinnerAdapter.setItems(countryResponse);
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
        rankSpinner.setAdapter(countrySpinnerAdapter);
        rankSpinner.setPromptId(R.string.id_for_spinner);

        Button getRanksButton = view.findViewById(R.id.reg_fragment_button);
        token.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxZTY3YjBjNzNiMGRiZDI2OTY4N2QwZCIsInJvbGVzIjpbIlVTRVIiXSwiaWF0IjoxNjQ3OTU4ODM0LCJleHAiOjE2NDc5NzMyMzR9.l0yIn8yRh2gn1CzezGSk0nQ1l3Xi9fGmg9OsAW9VBBY");
        getRanksButton.setOnClickListener(v -> registerViewModel.getElements(token));

        return view;
    }
}