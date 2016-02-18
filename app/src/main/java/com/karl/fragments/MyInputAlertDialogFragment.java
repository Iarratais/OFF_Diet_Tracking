package com.karl.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.karl.fyp.R;
import com.karl.fyp.SearchResultActivity;

/**
 * Created by Karl on 07/02/2016.
 */
public class MyInputAlertDialogFragment extends android.support.v4.app.DialogFragment{

    private static int amount = 0;

    public MyInputAlertDialogFragment(){}

    public static MyInputAlertDialogFragment newInstance(String title){
        MyInputAlertDialogFragment fragment = new MyInputAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        String title = getArguments().getString("title");

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.input_dialog_2, null);
        alert.setView(view);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText text = (EditText) view.findViewById(R.id.edittext);
                ((SearchResultActivity)getActivity()).calculateInformation(text.getText().toString());
            }
        });
        alert.setTitle(title);
        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alert.create();
    }

}
