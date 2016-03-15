package com.karl.alerts;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;

/**
 * Copyright Karl jones 2016.
 *
 * This class creates an alert dialog fragment. It will apply the title and the message to the
 * fragment.
 */

public class MyAlertDialogFragment extends DialogFragment {

    public MyAlertDialogFragment() {}

    public static MyAlertDialogFragment newInstance(String title, String message){
        MyAlertDialogFragment fragment = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Return a new alert dialog fragment.
     * @param savedInstanceState: bundle passed from constructor.
     * @return dialog: new alert dialog.
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }
}
