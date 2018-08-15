package adel.co.asyst.movieadel.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import adel.co.asyst.movieadel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    EditText searchet;
    Button searchbtn;
    OnSubmitListener listener;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String search) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("search", search);

        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchet = view.findViewById(R.id.edit_text_search);
        searchbtn = view.findViewById(R.id.button_search);

        searchbtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_search:
                String search = searchet.getText().toString();
                if (!TextUtils.isEmpty(search)) {
                    listener.onSubmitButton(search);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Harus diisi ", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SearchFragment.OnSubmitListener) {
            listener = (SearchFragment.OnSubmitListener) context;
        } else {
            throw new RuntimeException(context.toString() + "Activity harus implement interface");
        }
    }

    public interface OnSubmitListener {
        void onSubmitButton(String search);
    }
}
