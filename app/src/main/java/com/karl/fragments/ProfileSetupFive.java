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
 * ProfileSetupFive
 *
 * This class controls the fifth profile page.
 */
public class ProfileSetupFive extends android.support.v4.app.Fragment {

    private static final String TAG = "ProfileSetupFive";

    View rootView;

    EditText desiredWeightEditText;

    public ProfileSetupFive() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile_setup_five, container, false);

        desiredWeightEditText = (EditText) rootView.findViewById(R.id.editText4);
        desiredWeightEditText.addTextChangedListener(desiredWeightEditTextListener);

        // Inflate the layout for this fragment
        return rootView;
    }

    private final TextWatcher desiredWeightEditTextListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ((ProfileSetUp) getActivity()).setUserDesired(desiredWeightEditText.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}
