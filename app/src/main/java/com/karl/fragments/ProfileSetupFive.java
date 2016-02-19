package com.karl.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.karl.fyp.ProfileSetUp;
import com.karl.fyp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSetupFive extends android.support.v4.app.Fragment {

    private static final String TAG = "ProfileSetupFive";
    View rootView;

    EditText desired_weight;

    public ProfileSetupFive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile_setup_five, container, false);

        desired_weight = (EditText) rootView.findViewById(R.id.editText4);
        desired_weight.addTextChangedListener(desired_weight_listener);

        // Inflate the layout for this fragment
        return rootView;
    }

    private final TextWatcher desired_weight_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ((ProfileSetUp) getActivity()).setUser_desired(desired_weight.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
