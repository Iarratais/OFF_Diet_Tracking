package com.karl.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.karl.fyp.ProfileSetUp;
import com.karl.fyp.R;

/**
 * Copyright Karl jones 2016.
 * ProfileSetupThree
 *
 * This class is in control of the third screen of the new profile setup.
 */

public class ProfileSetupThree extends android.support.v4.app.Fragment {

    private static final String TAG = "ProfileSetup3";

    View rootView;

    EditText userHeightEditTextSetup;
    EditText userWeightEditTextSetup;

    public ProfileSetupThree() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_setup_three, container, false);

        userHeightEditTextSetup = (EditText) rootView.findViewById(R.id.editText3);
        userHeightEditTextSetup.addTextChangedListener(user_height);
        userWeightEditTextSetup = (EditText) rootView.findViewById(R.id.edittext5);
        userWeightEditTextSetup.addTextChangedListener(user_weight);

        // Inflate the layout for this fragment
        return rootView;
    }

    private final TextWatcher user_height = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ((ProfileSetUp) getActivity()).setUserHeight(userHeightEditTextSetup.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private final TextWatcher user_weight = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ((ProfileSetUp) getActivity()).setUserWeight(userWeightEditTextSetup.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}
