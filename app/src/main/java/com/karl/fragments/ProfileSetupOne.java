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

public class ProfileSetupOne extends android.support.v4.app.Fragment {

    private static final String TAG = "ProfileSetup1";

    View rootView;

    public ProfileSetupOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_setup_one, container, false);

        TextView intro = (TextView) rootView.findViewById(R.id.textView2);
        intro.setVisibility(View.GONE);

        Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);

        intro.setVisibility(View.VISIBLE);
        intro.startAnimation(fadeIn);

        return rootView;
    }
}
