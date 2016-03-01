package com.karl.analysis;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.karl.fyp.R;

/**
 * Created by Karl on 01/03/2016.
 *
 * Analyse the users previous month of data.
 */
public class Monthly_Analysis extends AsyncTask<Context, Void, Void>{

    private Context context;
    private static String[] days;

    @Override
    protected Void doInBackground(Context... params) {
        context = params[0];
        Resources res = context.getResources();

        days = new String[]{res.getString(R.string.monday), res.getString(R.string.tuesday), res.getString(R.string.wednesday), res.getString(R.string.thursday), res.getString(R.string.friday),
                res.getString(R.string.saturday), res.getString(R.string.sunday)};


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
