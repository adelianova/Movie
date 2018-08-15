package adel.co.asyst.movieadel.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import adel.co.asyst.movieadel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends BottomSheetDialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner yearsp, sortsp;
    Button filterbtn;
    String selectedSorting, selectedYear;
    ArrayList<String> listSort = new ArrayList<>();
    OnSubmitButtonListener listener;

    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance(String years, String sort) {
        FilterFragment filterFragment = new FilterFragment();

        Bundle bundle = new Bundle();
        bundle.putString("years", years);
        bundle.putString("sort", sort);

        filterFragment.setArguments(bundle);
        return filterFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        yearsp = view.findViewById(R.id.spinner_year);
        sortsp = view.findViewById(R.id.spinner_filter);
        filterbtn = view.findViewById(R.id.button_filter);
        yearsp.setOnItemSelectedListener(this);
        sortsp.setOnItemSelectedListener(this);
        ArrayList<String> years = new ArrayList<String>();
        years.add("none");
        for (int i = 2020; i > 1900; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
        yearsp.setAdapter(adapter);
        filterbtn.setOnClickListener(this);

        listSort.add("popularity.asc");
        listSort.add("popularity.desc");
        listSort.add("release_date.asc");
        listSort.add("release_date.desc");
        ArrayAdapter<String> sortAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listSort);
        sortsp.setAdapter(sortAdapter);

        if (getArguments() != null) {
            selectedYear = getArguments().getString("years", "");
            selectedSorting = getArguments().getString("sort", "");

            int posYear = adapter.getPosition(selectedYear);
            yearsp.setSelection(posYear);
            int posSort = sortAdapter.getPosition(selectedSorting);
            sortsp.setSelection(posSort);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_filter:
                String year = yearsp.getSelectedItem().toString();
                String sort = sortsp.getSelectedItem().toString();
                if (selectedYear == "none") {
                    listener.onSubmitButton("", sort);

                } else {
                    listener.onSubmitButton(year, sort);
                }
                dismiss();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedYear = yearsp.getSelectedItem().toString();
        selectedSorting = sortsp.getSelectedItem().toString();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FilterFragment.OnSubmitButtonListener) {
            listener = (FilterFragment.OnSubmitButtonListener) context;
        } else {
            throw new RuntimeException(context.toString() + "Activity harus implement interface");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnSubmitButtonListener {
        void onSubmitButton(String year, String sort);
    }
}
