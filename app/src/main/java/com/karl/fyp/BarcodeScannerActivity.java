package com.karl.fyp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.karl.barcodereader.BarcodeCaptureActivity;
import com.karl.dao.FoodDAO;
import com.karl.dao.IFoodDAO;

/**
 * Copyright Karl jones 2016.
 * BarcodeScannerActivity
 *
 * This controls the barcode scanner in the application.
 */

public class BarcodeScannerActivity extends Activity implements View.OnClickListener {

    // use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView barcodeValue;
    private ProgressBar progressBar;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeScannerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        statusMessage = (TextView)findViewById(R.id.status_message);
        barcodeValue = (TextView)findViewById(R.id.barcode_value);

        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);

        progressBar = (ProgressBar) findViewById(R.id.lookupProgressBar);
        progressBar.setVisibility(View.GONE);

        autoFocus.setChecked(true);

        findViewById(R.id.read_barcode).setOnClickListener(this);

//        // launch barcode activity.
//        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
//        intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
//        intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());
//
//        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    statusMessage.setText(R.string.barcode_success);
                    barcodeValue.setText(barcode.displayValue);
                    if(isDeviceOnline() && checkLength(barcode.displayValue)){
                        setUpTask(barcode.displayValue);
                    } else if (!isDeviceOnline()) {
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_sorry_check_your_network_settings), Snackbar.LENGTH_INDEFINITE)
                                .setAction(getString(R.string.settings_fragment_title), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                    }
                                })
                                .show();
                    }
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    //statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Check if the device is online.
     * @return network connected status.
     */
    public boolean isDeviceOnline() {
        boolean networkStatus = false;
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
            if(networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED){
                networkStatus = true;
            } else {
                networkInfo = connectivityManager.getNetworkInfo(1);
                if(networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    networkStatus = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            networkStatus = false;
        }

        return networkStatus;
    }

    /**
     * Check the length of the barcode.
     * @param barcode to be checked.
     * @return true if right amount.
     */
    public boolean checkLength(String barcode) {
        boolean isCorrectLength = false;
        if(barcode.length() == 13) {
            isCorrectLength = true;
        }
        return isCorrectLength;
    }

    /**
     * Set up the task.
     * @param barcode to be passed to task.
     */
    public void setUpTask(String barcode) {
        BarcodeSearchTask bst = new BarcodeSearchTask();
        bst.execute(barcode);

        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Send the user to get the results.
     * @param barcode to pass to the results activity.
     */
    public void sendUserToResultsPage(String barcode) {
        Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
        i.putExtra("barcode", barcode);
        startActivity(i);
        finish();
    }

    class BarcodeSearchTask extends AsyncTask<String, Integer, Boolean> {

        String barcode = "0000000000000";

        /**
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

            // If the item does not exist, send the user with the barcode to the manual entry
            // points.
            if(exists)
                sendUserToResultsPage(barcode);
            else {
                Intent i = new Intent(getApplicationContext(), NewManualEntryActivity.class);
                i.putExtra("scan_success", false);
                i.putExtra("barcode", barcode);
                startActivity(i);
            }
            progressBar.setVisibility(View.GONE);
        }
    }
}
