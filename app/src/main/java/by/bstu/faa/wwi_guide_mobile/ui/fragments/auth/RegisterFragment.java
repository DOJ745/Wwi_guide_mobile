package by.bstu.faa.wwi_guide_mobile.ui.fragments.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.RegData;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters.CountrySpinnerAdapter;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.FragmentNavigation;
import by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models.auth.RegisterViewModel;

public class RegisterFragment extends Fragment implements FragmentNavigation {
    private final String TAG = RegisterFragment.class.getSimpleName();

    private RegisterViewModel registerViewModel;
    private CountrySpinnerAdapter countrySpinnerAdapter;

    private RegData userRegData;
    private String selectedCountryId;
    private List<String> dataToTransfer;

    private EditText loginField;
    private EditText passwordField;
    private EditText repeatPasswordField;
    private TextView regMsgResponse;

    public RegisterFragment() {
        // Required empty public constructor
        Log.d(TAG, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        registerViewModel.init();
        userRegData = new RegData();
        dataToTransfer = new ArrayList<>();
        registerViewModel.getCountryResponse();

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        List<CountryDto> temp = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            CountryDto testCountry = new CountryDto();
            testCountry.setName("PLEASE, WAIT..." );
            testCountry.setImg("EMPTY");
            temp.add(testCountry);
        }
        countrySpinnerAdapter = new CountrySpinnerAdapter(
                this.getContext(),
                R.layout.row_country,
                temp);

        registerViewModel.getElementsDtoResponseLiveData().observe(getViewLifecycleOwner(), countryResponse -> {
            if (countryResponse != null) { countrySpinnerAdapter.setItems(countryResponse); }
        });

        Log.d(TAG, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(533);
        alphaAnimation.setFillAfter(true);

        loginField = view.findViewById(R.id.fragment_reg_login_input);
        passwordField = view.findViewById(R.id.fragment_reg_password_input);
        repeatPasswordField = view.findViewById(R.id.fragment_reg_rep_password_input);
        Button regButton = view.findViewById(R.id.fragment_reg_register_button);
        Button loginFragmentButton = view.findViewById(R.id.fragment_reg_toLogin_button);
        Spinner rankSpinner = view.findViewById(R.id.fragment_reg_country_spinner);
        TextView passwordRequirements = view.findViewById(R.id.fragment_reg_password_requirements);
        regMsgResponse = view.findViewById(R.id.fragment_reg_msg_response);

        loginFragmentButton.setOnClickListener(view1 -> navigateToFragment(view1, "reg"));

        registerViewModel.getRegRepoResponse().observe(getViewLifecycleOwner(), regResponse ->
        {
            if (regResponse != null) {
                if (!regResponse.getMsgStatus().equals("") && regResponse.getMsgError() == null) {
                    switch (regResponse.getMsgStatus()) {
                        case (CONSTANTS.WEB_APP_SUCCESS_RESPONSES.REG_SUCCESS):
                            regMsgResponse.startAnimation(alphaAnimation);
                            regMsgResponse.setText(R.string.success_registration);
                            dataToTransfer.add(loginField.getText().toString());
                            dataToTransfer.add(passwordField.getText().toString());
                            break;

                        case (CONSTANTS.WEB_APP_ERR_RESPONSES.REG_SUCH_USER_EXIST):
                            regMsgResponse.startAnimation(alphaAnimation);
                            regMsgResponse.setText(R.string.err_reg_user_exist);
                            break;

                        default:
                            regMsgResponse.startAnimation(alphaAnimation);
                            regMsgResponse.setText(R.string.err_reg_failed);
                            break;
                    }
                }
                else
                    if(regResponse.getMsgError() != null) {
                        regMsgResponse.startAnimation(alphaAnimation);
                        regMsgResponse.setText(regResponse.getMsgError());
                    }
            }
        });

        rankSpinner.setAdapter(countrySpinnerAdapter);
        rankSpinner.setPromptId(R.string.id_for_spinner);
        rankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedCountryId = countrySpinnerAdapter.getItem(pos).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                selectedCountryId = countrySpinnerAdapter.getItem(0).getId();
            }
        });

        setTextFieldListeners(loginField, passwordField, repeatPasswordField, passwordRequirements);

        regButton.setOnClickListener(v -> {
            if(loginField.getError() == null && passwordField.getError() == null &&
                    repeatPasswordField.getError() == null &&
                    passwordField.getText().toString().equals(repeatPasswordField.getText().toString()) ) {
                userRegData.setLogin(loginField.getText().toString());
                userRegData.setPassword(repeatPasswordField.getText().toString());
                userRegData.setCountryId(selectedCountryId);

                registerViewModel.regUser(userRegData);
            }
            else if(!passwordField.getText().toString().equals(repeatPasswordField.getText().toString())) {
                regMsgResponse.startAnimation(alphaAnimation);
                regMsgResponse.setText(R.string.err_reg_mismatch_password);
            }
            else {
                regMsgResponse.startAnimation(alphaAnimation);
                regMsgResponse.setText(R.string.err_mismatch_data);
            }
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

    private void setTextFieldListeners(EditText loginField,
                                       EditText passwordField,
                                       EditText repPasswordField,
                                       TextView passwordRequirements) {

        loginField.setError("?????????????? 4 ??????????????!");
        loginField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (loginField.getText().length() > 3 && loginField.getText().length() < 47) { loginField.setError(null); }
                else { loginField.setError("???????????????????????? ????????!"); }
            }
        });

        passwordField.setError("?????????????? 6 ????????????????!");
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                /* Pattern explain

                    ^                 # start-of-string
                    (?=.*[0-9])       # a digit must occur at least once
                    (?=.*[a-z])       # a lower case letter must occur at least once
                    (?=.*[A-Z])       # an upper case letter must occur at least once
                    (?=.*[@#$%^&+=_]) # a special character must occur at least once
                    (?=\S+$)          # no whitespace allowed in the entire string
                    .{6,18}           # anything, at least 6 places though and less than 18
                    $                 # end-of-string

                     */
                String regexPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{6,18}$";
                if (Pattern.matches(regexPattern, passwordField.getText().toString())) {
                    passwordField.setError(null);
                    passwordRequirements.setVisibility(View.GONE);
                }
                else {
                    passwordField.setError("???? ?????????????????? ???????????????????? ?? ????????????!");
                    passwordRequirements.setVisibility(View.VISIBLE);
                }
            }
        });

        repPasswordField.setError("?????????????? 6 ????????????????!");
        repPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (repPasswordField.getText().length() > 5 && repPasswordField.getText().length() < 17 )
                { repPasswordField.setError(null); }
                else { repPasswordField.setError("???????????????????????? ????????!"); }
            }
        });
    }

    @Override
    public void navigateToFragment(View view, String fragmentName) {
        Bundle regResult = new Bundle();

        try {
            regResult.putString("login", dataToTransfer.get(0));
            regResult.putString("password", dataToTransfer.get(1));
        }
        catch (IndexOutOfBoundsException e) {
            regResult.putString("login", "");
            regResult.putString("password", "");
        }

        if("reg".equals(fragmentName))
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment2, regResult);
    }
}