package com.karl.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.karl.fyp.ProfileSetUp;
import com.karl.fyp.R;

/**
 * Copyright Karl jones 2016.
 * ProfileSetupFour
 *
 * This class controls the fourth screen of the profile set up.
 */
public class ProfileSetupFour extends android.support.v4.app.Fragment {

    View rootView;

    public ProfileSetupFour() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_setup_four, container, false);

        final Button saveNewUserProfileButton = (Button) rootView.findViewById(R.id.button);
        saveNewUserProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProfileSetUp) getActivity()).createNewUser(saveNewUserProfileButton);
            }
        });

        return rootView;
    }
}
