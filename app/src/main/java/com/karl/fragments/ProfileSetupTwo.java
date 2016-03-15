package com.karl.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.karl.fyp.ProfileSetUp;
import com.karl.fyp.R;

/**
 * Copyright Karl jones 2016.
 * ProfileSetupTwo
 *
 * This class controls the second screen of the set up process.
 */

public class ProfileSetupTwo extends android.support.v4.app.Fragment {

    View rootView;

    private static final String TAG = "ProfileSetup2";

    EditText userNameEditText;

    public ProfileSetupTwo() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_setup_two, container, false);

        final Spinner userGenderSpinner = (Spinner) rootView.findViewById(R.id.spinner);
        String[] genders = getResources().getStringArray(R.array.gender);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_dropdown_item, genders);
        userGenderSpinner.setAdapter(adapter);
        userGenderSpinner.setSelection(adapter.getPosition("Male"));
        userGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((ProfileSetUp) getActivity()).setUserGender(userGenderSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        userNameEditText = (EditText) rootView.findViewById(R.id.editText3);
        userNameEditText.addTextChangedListener(user_name_listener);

        // Inflate the layout for this fragment
        return rootView;
    }

    private final TextWatcher user_name_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ((ProfileSetUp) getActivity()).setUserName(userNameEditText.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };


}
