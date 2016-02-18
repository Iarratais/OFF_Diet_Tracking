package com.karl.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.karl.fyp.R;


public class ProfileSetupOne extends android.support.v4.app.Fragment {

    View rootView;

    public ProfileSetupOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_setup_one, container, false);

        return rootView;
    }
}
