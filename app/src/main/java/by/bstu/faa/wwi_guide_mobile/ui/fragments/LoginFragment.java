package by.bstu.faa.wwi_guide_mobile.ui.fragments;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.security.SecurePreferences;
import by.bstu.faa.wwi_guide_mobile.view_models.LoginViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginFragment extends Fragment implements FragmentMethods {

    private static final String ARG_TOKEN = "token";
    private static final String ARG_LOGIN = "login";
    private static final String ARG_PASSWORD = "password";
    private static final String ARG_CHECKBOX = "checkbox";

    private LoginViewModel loginViewModel;

    private LoginData loginData;
    private UserDto userData;

    private String token;
    private String loginParam;
    private String passwordParam;
    private String checkboxParam;

    private SecurePreferences preferences;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public LoginFragment() {
        // Required empty public constructor
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOGIN, param1);
        args.putString(ARG_PASSWORD, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*preferences = new SecurePreferences(
                this.requireContext(),
                "user-info",
                "8a8023a47791351b2547ff2468ffbd89d15c80f1b4870f6853158e9a11ec50b7",
                true);*/
        preferences = SecurePreferences.getInstance(this.requireContext());

        if (getArguments() != null) {
            loginParam = getArguments().getString(ARG_LOGIN);
            passwordParam = getArguments().getString(ARG_PASSWORD);
        }
        else {
            loginParam = "";
            passwordParam = "";
        }

        if (preferences.containsKey(ARG_CHECKBOX) &&
                preferences.containsKey(ARG_LOGIN) &&
                preferences.containsKey(ARG_PASSWORD)) {
            checkboxParam = preferences.getString(ARG_CHECKBOX);
            loginParam = preferences.getString(ARG_LOGIN);
            passwordParam = preferences.getString(ARG_PASSWORD);
        }
        else {
            checkboxParam = "";
            loginParam = "";
            passwordParam = "";
        }

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.init();
        loginData = new LoginData();
        userData = new UserDto();

        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_CREATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_CREATE_VIEW);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        TextView loginMsgResponse = view.findViewById(R.id.fragment_login_msg_response);

        Button loginButton = view.findViewById(R.id.fragment_login_enter_button);
        Button regFragmentButton = view.findViewById(R.id.fragment_login_toReg_button);

        EditText loginField = view.findViewById(R.id.fragment_login_login_input);
        EditText passwordField = view.findViewById(R.id.fragment_login_password_input);

        CheckBox rememberMeBox = view.findViewById(R.id.fragment_login_remember_me);

        setTextFieldListeners(loginField, passwordField, rememberMeBox);

        rememberMeBox.setChecked(checkboxParam.equals("true"));

        rememberMeBox.setOnClickListener(v -> {
            if(rememberMeBox.isChecked()) {

                checkboxParam = "true";
                try {
                    preferences.put(ARG_CHECKBOX, checkboxParam);
                    preferences.put(ARG_LOGIN, loginField.getText().toString());
                    preferences.put(ARG_PASSWORD, passwordField.getText().toString());

                    String username = preferences.getString(ARG_LOGIN);
                    String password = preferences.getString(ARG_PASSWORD);

                    Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT,
                            "\tDecrypted password:" + password +
                                    "\n Decrypted username: " + username);
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            else {
                preferences.removeValue(ARG_CHECKBOX);
                preferences.removeValue(ARG_LOGIN);
                preferences.removeValue(ARG_PASSWORD);
                Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "checkBox unchecked");
            }
        });

        //String sourceString = "<b>Some bold text</b> Regular text <i>italic text</i>";
        //loginMsgResponse.setText(Html.fromHtml(sourceString));

        loginField.setText(loginParam);
        passwordField.setText(passwordParam);

        loginViewModel.getLoginRepoResponse().observe(getViewLifecycleOwner(), loginResponse -> {

            if (loginResponse != null) {
                if (!loginResponse.getMsgStatus().equals("") && loginResponse.getMsgError() == null) {
                    switch (loginResponse.getMsgStatus()) {
                        case (CONSTANTS.WEB_APP_SUCCESS_RESPONSES.LOGIN_SUCCESS):
                            setUserData(userData, loginResponse);
                            token = loginResponse.getToken();
                            preferences.put(ARG_TOKEN, token);

                            mDisposable.add(loginViewModel.insertOrUpdateUser(userData)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "User has been written into database"),
                                    throwable -> Log.e(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "Unable to get username", throwable))
                            );

                            break;

                        case (CONSTANTS.WEB_APP_ERR_RESPONSES.LOGIN_INCORRECT_PASSWORD):
                            loginMsgResponse.setText(R.string.err_login_wrong_user_password);
                            break;

                        case (CONSTANTS.WEB_APP_ERR_RESPONSES.LOGIN_NO_SUCH_USER):
                            loginMsgResponse.setText(R.string.err_login_wrong_user_login);
                            break;

                        default:
                            loginMsgResponse.setText(R.string.err_login_failed);
                            break;
                    }
                }
                else
                    loginMsgResponse.setText(loginResponse.getMsgError());
            }
        });

        loginButton.setOnClickListener(v -> {
            if(loginField.getError() == null && passwordField.getError() == null) {
                loginData.setLogin(loginField.getText().toString());
                loginData.setPassword(passwordField.getText().toString());
                loginViewModel.loginUser(loginData);
            }
            else{ loginMsgResponse.setText(R.string.err_mismatch_data); }
        });

        regFragmentButton.setOnClickListener(this::navigateToFragment);

        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_CREATED);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_VIEW_STATE_RESTORED);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        mDisposable.clear();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_STOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY_VIEW);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DESTROY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LIFECYCLE_STATES.ON_DETACH);
    }

    private void setTextFieldListeners(EditText loginField, EditText passwordField, CheckBox rememberMeBox) {
        loginField.setError("Минимум 4 символа!");
        loginField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (loginField.getText().length() > 3) {
                    loginField.setError(null);
                    rememberMeBox.setEnabled(true);
                }
                else {
                    loginField.setError("Обязательное поле!");
                    rememberMeBox.setEnabled(false);
                }
            }
        });

        passwordField.setError("Минимум 6 символов!");
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (passwordField.getText().length() > 5) {
                    passwordField.setError(null);
                    rememberMeBox.setEnabled(true);
                }
                else {
                    passwordField.setError("Обязательное поле!");
                    rememberMeBox.setEnabled(false);
                }
            }
        });
    }

    public void navigateToFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment, null);
    }

    private void setUserData(@NonNull UserDto userData, UserDto loginResponse) {
        userData.setLogin(loginResponse.getLogin());
        userData.setPassword(loginResponse.getPassword());
        userData.setCountryId(loginResponse.getCountryId());
        userData.setAchievements(loginResponse.getAchievements());
        userData.setRankId(loginResponse.getRankId());
        userData.setScore(loginResponse.getScore());
        userData.setRoles(loginResponse.getRoles());
    }
}