package by.bstu.faa.wwi_guide_mobile.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;


public class LoginFragment extends Fragment implements FragmentMethods{

    private static final String ARG_LOGIN = "login";
    private static final String ARG_PASSWORD = "password";

    private String loginParam;
    private String passwordParam;

    public LoginFragment() {
        // Required empty public constructor
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, CONSTANTS.LOG_TAGS.CONSTRUCTOR);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
            loginParam = getArguments().getString(ARG_LOGIN);
            passwordParam = getArguments().getString(ARG_PASSWORD);
        }
        else {
            loginParam = "";
            passwordParam = "";
        }

        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        TextView testTextView = view.findViewById(R.id.textView);

        Button regFragmentButton = view.findViewById(R.id.login_toReg_button);

        EditText loginField = view.findViewById(R.id.login_login_input);
        EditText passwordField = view.findViewById(R.id.login_password_input);

        String sourceString = "<b>Some bold text</b> Regular text <i>italic text</i>";
        testTextView.setText(Html.fromHtml(sourceString));

        loginField.setText(loginParam);
        passwordField.setText(passwordParam);

        regFragmentButton.setOnClickListener(v -> replaceFragment());

        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "onViewCreated");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(CONSTANTS.LOG_TAGS.LOGIN_FRAGMENT, "onDetach");
    }

    public void replaceFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, RegisterFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null) // name can be null
                .commit();
    }
}