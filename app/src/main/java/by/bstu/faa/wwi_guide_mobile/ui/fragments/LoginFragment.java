package by.bstu.faa.wwi_guide_mobile.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String USER_TOKEN = "token";

    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
        Log.d(Constants.Values.LOG_TAG_LOGIN_FRAGMENT, Constants.Values.LOG_CONSTRUCTOR);
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

        Log.d(Constants.Values.LOG_TAG_LOGIN_FRAGMENT, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        Log.d(Constants.Values.LOG_TAG_LOGIN_FRAGMENT, "onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        TextView testTextView = view.findViewById(R.id.textView);
        String sourceString = "<b>Some bold text</b> Regular text <i>italic text</i>";
        testTextView.setText(Html.fromHtml(sourceString));

        Log.d(Constants.Values.LOG_TAG_LOGIN_FRAGMENT, "onViewCreated");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(Constants.Values.LOG_TAG_LOGIN_FRAGMENT, "onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(Constants.Values.LOG_TAG_LOGIN_FRAGMENT, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(Constants.Values.LOG_TAG_LOGIN_FRAGMENT, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(Constants.Values.LOG_TAG_LOGIN_FRAGMENT, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(Constants.Values.LOG_TAG_LOGIN_FRAGMENT, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(Constants.Values.LOG_TAG_LOGIN_FRAGMENT, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.Values.LOG_TAG_LOGIN_FRAGMENT, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(Constants.Values.LOG_TAG_LOGIN_FRAGMENT, "onDetach");
    }
}