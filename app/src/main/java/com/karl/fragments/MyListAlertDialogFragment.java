package com.karl.fragments;


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

import com.karl.barcodereader.BarcodeCaptureActivity;
import com.karl.fyp.BarcodeScannerActivity;
import com.karl.fyp.NewManualEntryActivity;
import com.karl.fyp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
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
                        if(itemClicked.equals(getString(R.string.main_activity_new_entry_barcode))){
                            startActivity(new Intent(getActivity(), BarcodeScannerActivity.class));
                        } else if (itemClicked.equals(getString(R.string.main_activity_new_entry_manual))){
                            startActivity(new Intent(getActivity(), NewManualEntryActivity.class));
                        }
                    }
                })
                .create();
    }
}
