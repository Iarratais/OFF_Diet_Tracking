package android.karl.fragments;

import android.app.Fragment;
import android.karl.fyp.MainActivity;
import android.karl.fyp.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Search fragment
 *
 * Copyright Karl Jones 2016
 */
public class LookupFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lookup, container, false);

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.lookup_fragment_title));

        return rootView;

    }

}
