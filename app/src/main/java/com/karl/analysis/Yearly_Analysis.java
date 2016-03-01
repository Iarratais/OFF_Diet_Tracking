package com.karl.analysis;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.karl.fyp.R;

/**
 * Created by Karl on 01/03/2016.
 *
 * Analyse yearly data.
 */
public class Yearly_Analysis extends AsyncTask<Context, Void, Void>{

    private static String[] months;
    private Context context;

    @Override
    protected Void doInBackground(Context... params) {
        context = params[0];
        Resources res = context.getResources();

        months = new String[] {res.getString(R.string.january), res.getString(R.string.february), res.getString(R.string.march), res.getString(R.string.april),
                res.getString(R.string.may), res.getString(R.string.june), res.getString(R.string.july),
                res.getString(R.string.august), res.getString(R.string.september), res.getString(R.string.october),
                res.getString(R.string.november), res.getString(R.string.december)};

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
