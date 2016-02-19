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
public class ProfileSetupThree extends android.support.v4.app.Fragment {

    private static final String TAG = "ProfileSetup3";

    View rootView;

    EditText height;
    EditText weight;

    public ProfileSetupThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_setup_three, container, false);

        height = (EditText) rootView.findViewById(R.id.editText3);
        height.addTextChangedListener(user_height);
        weight = (EditText) rootView.findViewById(R.id.edittext5);
        weight.addTextChangedListener(user_weight);

        // Inflate the layout for this fragment
        return rootView;
    }

    private final TextWatcher user_height = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ((ProfileSetUp) getActivity()).setUser_height(height.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private final TextWatcher user_weight = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ((ProfileSetUp) getActivity()).setUser_weight(weight.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
