package by.bstu.faa.wwi_guide_mobile.ui.main.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.data_objects.Token;
import by.bstu.faa.wwi_guide_mobile.view_models.MainViewModel;
import by.bstu.faa.wwi_guide_mobile.ui.main.adapters.YearResultAdapter;
import by.bstu.faa.wwi_guide_mobile.view_models.YearViewModel;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    private YearViewModel yearViewModel;
    private YearResultAdapter yearAdapter;
    private Token token;

    private Button getYearsButton;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        yearAdapter = new YearResultAdapter();

        yearViewModel = ViewModelProviders.of(this).get(YearViewModel.class);
        yearViewModel.init();
        yearViewModel.getYearsDtoResponseLiveData().observe(this, yearsResponse -> {
            if (yearsResponse != null) {
                yearAdapter.setItems(yearsResponse);
            }
        });

        token = new Token();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);

        RecyclerView yearRecyclerView = view.findViewById(R.id.main_fragment_year_list);
        yearRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        yearRecyclerView.setAdapter(yearAdapter);

        getYearsButton = view.findViewById(R.id.main_fragment_button);

        token.setToken("");

        getYearsButton.setOnClickListener(v -> yearViewModel.getYears(token));

        return view;
    }

    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }
*/
}