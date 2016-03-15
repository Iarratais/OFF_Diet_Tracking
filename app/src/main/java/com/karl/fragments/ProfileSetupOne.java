package com.karl.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.karl.fyp.R;

/**
 * Copyright Karl jones 2016.
 * ProfileSetupOne
 *
 * This class deals with the first screen of the profile setup.
 */

public class ProfileSetupOne extends android.support.v4.app.Fragment {

    private static final String TAG = "ProfileSetup1";

    View rootView;

    public ProfileSetupOne() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_setup_one, container, false);

        TextView welcomeTextView = (TextView) rootView.findViewById(R.id.textView2);
        welcomeTextView.setVisibility(View.GONE);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);

        welcomeTextView.setVisibility(View.VISIBLE);
        welcomeTextView.startAnimation(fadeInAnimation);

        return rootView;
    }
}
