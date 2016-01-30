package com.karl.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.karl.dao.FoodDAO;
import com.karl.dao.IFoodDAO;
import com.karl.fyp.MainActivity;
import com.karl.fyp.R;
import com.karl.fyp.SearchResultActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * Search fragment
 *
 * Copyright Karl Jones 2016
 */
public class LookupFragment extends Fragment {

    View rootView;

    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_lookup, container, false);

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.lookup_fragment_title));

        View button = rootView.findViewById(R.id.scan_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });

        progressBar = (ProgressBar) rootView.findViewById(R.id.lookupProgressBar);
        progressBar.setVisibility(View.GONE);

        return rootView;
    }

    /**
     * Temporary method while the user can not scan barcodes
     * @return barcode string
     */
    protected String showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        String barcode = "0000000000000";

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isNetworkOnline() && checkLength(editText.getText().toString())) {
                            setUpTask(editText.getText().toString());
                        }
                        if (!isNetworkOnline()) {
                            Snackbar.make(getView(), getString(R.string.error_sorry_check_your_network_settings), Snackbar.LENGTH_SHORT)
                                    .setAction(getString(R.string.settings_fragment_title), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                        }
                                    })
                                    .show();
                        }
                        if (!checkLength(editText.getText().toString())) {
                            Snackbar.make(getView(), getString(R.string.lookup_fragment_your_barcode_needs_13_digits), Snackbar.LENGTH_SHORT).show();

                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        return editText.getText().toString();
    }

    /**
     *
     * @param barcode to pass to the results activity
     */
    public void getResults(String barcode) {
        Intent i = new Intent(getActivity(), SearchResultActivity.class);
        i.putExtra("barcode", barcode);
        startActivity(i);
    }

    /**
     *
     * @return network connected status
     */
    public boolean isNetworkOnline() {
        boolean status = false;

        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
            if(networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED){
                status = true;
            } else {
                networkInfo = connectivityManager.getNetworkInfo(1);
                if(networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    status = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }

        return status;
    }

    public boolean checkLength(String barcode) {
        boolean is13 = false;
        if(barcode.length() == 13) {
            is13 = true;
        }
        return is13;
    }


    public void setUpTask(String barcode) {
        BarcodeSearchTask bst = new BarcodeSearchTask();
        bst.execute(barcode);

        progressBar.setVisibility(View.VISIBLE);
    }

    class BarcodeSearchTask extends AsyncTask<String, Integer, Boolean> {

        String barcode = "0000000000000";

        /**
         *
         * @param params string put into the task
         * @return exists
         */
        @Override
        protected Boolean doInBackground(String... params) {
            IFoodDAO foodDAO = new FoodDAO();

            barcode = params[0];

            try {
                return foodDAO.checkProduct(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * UI thread interaction
         * @param exists if the product exists of not
         */
        protected void onPostExecute(Boolean exists) {
            super.onPostExecute(exists);

            if(exists)
                getResults(barcode);
            else
                Snackbar.make(getView(), getString(R.string.error_item_does_not_exist), Snackbar.LENGTH_SHORT).show();

            progressBar.setVisibility(View.GONE);

        }
    }
}