package com.karl.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.karl.fyp.MainActivity;
import com.karl.fyp.R;

/**
 * Copyright Karl jones 2016.
 * RecipeKeepFragment
 *
 * This fragment shows information about another application of mine.
 */

public class RecipeKeepFragment extends android.support.v4.app.Fragment {

    View rootView;

    public RecipeKeepFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_recipe_keep, container, false);

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.recipe_keep));

        ImageView recipeKeepImageIcon = (ImageView) rootView.findViewById(R.id.imgLogo);
        recipeKeepImageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setMessage(getString(R.string.recipe_keep_now_leaving_application))
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.karl.recipekeeper")));
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(),"Error occurred, please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(true)
                        .show();
            }
        });
        return rootView;
    }
}
