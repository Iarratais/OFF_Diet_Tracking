package com.karl.fragments;


import android.os.Bundle;
import android.app.Fragment;
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
 * A simple {@link Fragment} subclass.
 */
public class ProfileSetupTwo extends android.support.v4.app.Fragment {

    View rootView;

    EditText username;

    public ProfileSetupTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_setup_two, container, false);

        final Spinner gender = (Spinner) rootView.findViewById(R.id.spinner);
        String[] genders = getResources().getStringArray(R.array.gender);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_dropdown_item, genders);
        gender.setAdapter(adapter);
        gender.setSelection(adapter.getPosition("Unspecified"));
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((ProfileSetUp) getActivity()).setUser_gender(gender.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        username = (EditText) rootView.findViewById(R.id.editText3);
        username.addTextChangedListener(user_name_listener);

        // Inflate the layout for this fragment
        return rootView;
    }

    private final TextWatcher user_name_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ((ProfileSetUp) getActivity()).setUser_name(username.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


}
