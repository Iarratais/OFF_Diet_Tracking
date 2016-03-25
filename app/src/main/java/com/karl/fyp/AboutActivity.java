package com.karl.fyp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Copyright Karl jones 2016.
 * AboutActivity
 *
 * This gives the user information about the application.
 */

public class AboutActivity extends android.support.v4.app.Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_about, container, false);

        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.about_fragment_title));

        TextView versionNumberTextView = (TextView) rootView.findViewById(R.id.version_number_textview);
        versionNumberTextView.setText("Version " + BuildConfig.VERSION_NAME);

        Button FAQButton = (Button) rootView.findViewById(R.id.faq_button);
        FAQButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FAQActivity.class));
            }
        });

        return rootView;
    }
}
