package by.bstu.faa.wwi_guide_mobile.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.ui.adapters.RankSpinnerAdapter;

public class RegisterFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_fragment, container, false);

        Spinner rankSpinner = view.findViewById(R.id.rank_spinner);

        String[] dayOfWeek = { "Понедельник", "Вторник", "Среда", "Четверг",
                "Котопятница", "Субкота", "Воскресенье" };

        RankSpinnerAdapter adapter = new RankSpinnerAdapter(
                this.getContext(),
                R.layout.rank_row,
                dayOfWeek);

        // Вызываем адаптер
        rankSpinner.setAdapter(adapter);
        rankSpinner.setPromptId(R.string.id_for_spinner);
        rankSpinner.setSelection(2, true);

        /*

        getYearsButton = view.findViewById(R.id.main_fragment_button);

        token.setToken("");

        getYearsButton.setOnClickListener(v -> yearViewModel.getYears(token));*/

        return view;
    }
}