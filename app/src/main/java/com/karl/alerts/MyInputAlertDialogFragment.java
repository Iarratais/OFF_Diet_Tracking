package com.karl.alerts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.fyp.SearchResultActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Copyright Karl jones 2016.
 *
 * Create an input alert dialog. This will apply the title to the alert dialog. You must specify
 * the action that the application will take given a certain title in the code below (see line 48).
 */

public class MyInputAlertDialogFragment extends android.support.v4.app.DialogFragment{

    public MyInputAlertDialogFragment(){}

    public static MyInputAlertDialogFragment newInstance(String title){
        MyInputAlertDialogFragment fragment = new MyInputAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final String title = getArguments().getString("title");
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.input_dialog_2, null);
        alert.setView(view);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText text = (EditText) view.findViewById(R.id.edittext);
                String message = text.getText().toString();

                // This checks which input it is taking. It may be logging weight or taking in a
                // weight of food.
                if (title != null) {
                    if(title.equals(getString(R.string.progress_fragment_log_your_weight))){
                        MySQLiteHelper db = new MySQLiteHelper(getContext());
                        if(!message.equals("")) {
                            db.logWeight(getDate(), message, null);
                        } else {
                            Toast.makeText(getContext(), getString(R.string
                                    .main_activity_new_entry_weight_not_saved), Toast
                                    .LENGTH_SHORT).show();
                        }
                    } else {
                        ((SearchResultActivity) getActivity()).calculateInformation(message);
                    }
                }
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

    /**
     * Get the current date.
     * @return String: date in format - DAY00000000
     */
    public String getDate() {
        Calendar c = Calendar.getInstance();
        String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(c.get(Calendar.MONTH) + 1);
        String year = Integer.toString(c.get(Calendar.YEAR));

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        String weekDay = dayFormat.format(c.getTime());

        if(day.length() < 2) {
            String temp = day;
            day = "0" + temp;
        }
        if(month.length() < 2) {
            String temp = month;
            month = "0" + temp;
        }
        return weekDay.substring(0, 3).toUpperCase() + day + month + year;
    }
}
