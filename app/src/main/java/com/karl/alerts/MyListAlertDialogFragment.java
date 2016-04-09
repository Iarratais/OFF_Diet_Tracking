package com.karl.alerts;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.karl.barcodereader.BarcodeCaptureActivity;
import com.karl.fyp.NewManualEntryActivity;
import com.karl.fyp.R;

/**
 * Copyright Karl jones 2016.
 *
 * Create a list alert dialog. Currently only takes in the title and provides the list dialog
 * that appears when the user are given the option to do multiple actions from the home screen.
 */

public class MyListAlertDialogFragment extends android.support.v4.app.DialogFragment {


    public MyListAlertDialogFragment() {
        // Required empty public constructor
    }

    public static MyListAlertDialogFragment newInstance(int title){
        MyListAlertDialogFragment fragment = new MyListAlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        int title = getArguments().getInt("title");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item);
        arrayAdapter.add(getString(R.string.main_activity_new_entry_barcode));
        arrayAdapter.add(getString(R.string.main_activity_new_entry_manual));
        arrayAdapter.add(getString(R.string.progress_fragment_log_your_weight));

        return new AlertDialog.Builder(getActivity())
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setTitle(title)
                .setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String itemClicked = arrayAdapter.getItem(which);
                        if (itemClicked.equals(getString(R.string.main_activity_new_entry_barcode))) {
                            startActivity(new Intent(getActivity(), BarcodeCaptureActivity.class));
                        } else if (itemClicked.equals(getString(R.string.main_activity_new_entry_manual))) {
                            startActivity(new Intent(getActivity(), NewManualEntryActivity.class));
                        } else if (itemClicked.equals(getString(R.string.progress_fragment_log_your_weight))) {
                            android.support.v4.app.DialogFragment inputdialog = MyInputAlertDialogFragment.newInstance(getString(R.string.progress_fragment_log_your_weight));
                            inputdialog.show(getFragmentManager().beginTransaction(), "dialog");
                        }
                    }
                })
                .create();
    }
}
