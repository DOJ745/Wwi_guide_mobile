package by.bstu.faa.wwi_guide_mobile.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.data_objects.RegData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.ui.adapters.CountrySpinnerAdapter;
import by.bstu.faa.wwi_guide_mobile.view_models.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RegisterViewModel registerViewModel;

    private CountrySpinnerAdapter countrySpinnerAdapter;

    private RegData userRegData;
    private String selectedCountryId;

    public RegisterFragment() {
        // Required empty public constructor
    }

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

        for (int i = 0; i < 1; i++){
            CountryDto testCountry = new CountryDto();
            testCountry.setName("PLEASE, WAIT..." );
            testCountry.setFlagUrl("EMPTY");
            temp.add(testCountry);
        }

        countrySpinnerAdapter = new CountrySpinnerAdapter(this.getContext(), R.layout.country_row, temp);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        registerViewModel.init();

        registerViewModel.getElementsDtoResponseLiveData().observe(this, countryResponse -> {
            if (countryResponse != null) {
                countrySpinnerAdapter.setItems(countryResponse);
            }
        });

        registerViewModel.getRegRepoResponse().observe(this, regResponse ->
        {
            if (regResponse != null) {
                Toast.makeText(this.getContext(),
                        "REG RESPONSE: " + regResponse.getRegStatus(),
                        Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this.getContext(),
                        "REG NULL RESPONSE! ",
                        Toast.LENGTH_LONG).show();
            }
        });

        userRegData = new RegData();
        registerViewModel.getCountryResponse();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_fragment, container, false);

        EditText loginField = view.findViewById(R.id.reg_login_input);
        EditText passwordField = view.findViewById(R.id.reg_password_input);
        EditText repeatPasswordField = view.findViewById(R.id.reg_rep_password_input);
        Button regButton = view.findViewById(R.id.reg_register_button);

        List<EditText> textFields = new ArrayList<>();
        textFields.add(loginField);
        textFields.add(passwordField);
        textFields.add(repeatPasswordField);

        initEnterTextElements(textFields);

        Spinner rankSpinner = view.findViewById(R.id.reg_country_spinner);
        rankSpinner.setAdapter(countrySpinnerAdapter);
        rankSpinner.setPromptId(R.string.id_for_spinner);
        rankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selectedCountryId = countrySpinnerAdapter.getItem(pos).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                selectedCountryId = countrySpinnerAdapter.getItem(0).getId();
            }
        });

        regButton.setOnClickListener(v -> {

            if( !loginField.getText().equals("") &&
                    !passwordField.getText().equals("") &&
                    !repeatPasswordField.getText().equals("") &&
                    passwordField.getText().toString().equals(repeatPasswordField.getText().toString()) ) {

                userRegData.setLogin(loginField.getText().toString());
                userRegData.setPassword(repeatPasswordField.getText().toString());
                userRegData.setCountryId(selectedCountryId);

                registerViewModel.regUser(userRegData);
            }
            else if (!passwordField.getText().toString().equals(repeatPasswordField.getText().toString())){
                Toast.makeText(this.getContext(),
                        "Пароли не совпадают!",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this.getContext(),
                    "Проверьте введённые данные!",
                    Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void initEnterTextElements(List<EditText> textFields) {

        for (EditText field: textFields) {

            field.setError("Минимум 4 символа!");
            field.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void afterTextChanged(Editable editable) {
                    if (field.getText().length() != 0 && field.getText().length() > 3) {
                        field.setError(null);
                    }
                    else {
                        field.setError("Обязательное поле!");
                    }
                }
            });
        }

    }
}