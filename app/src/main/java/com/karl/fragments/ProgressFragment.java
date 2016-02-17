package com.karl.fragments;

import android.app.Fragment;
import com.karl.fyp.MainActivity;
import com.karl.fyp.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ProgressFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_progress, container, false);

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.progress_fragment_title));

        return rootView;
    }

}
