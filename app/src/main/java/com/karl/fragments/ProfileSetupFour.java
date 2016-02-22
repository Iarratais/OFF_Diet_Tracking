package com.karl.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.karl.fyp.MainActivity;
import com.karl.fyp.ProfileSetUp;
import com.karl.fyp.R;

import tyrantgit.explosionfield.ExplosionField;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSetupFour extends android.support.v4.app.Fragment {

    View rootView;

    private static final String TAG = "ProfileSetup4";

    public ProfileSetupFour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_setup_four, container, false);

        final ExplosionField explosionField = ExplosionField.attach2Window(getActivity());

        final Button btn = (Button) rootView.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProfileSetUp) getActivity()).createNewUser(btn);
                //startActivity(new Intent(getActivity(), MainActivity.class));
                //getActivity().finish();
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }


}
