package com.karl.fragments;


import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthAnalysisFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "MonthAnalysisFragment";

    View rootView;

    public MonthAnalysisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_month_analysis, container, false);

        getInformationFromDatabase();

        return rootView;
    }

    public String[] makeDaysArray(){
        return new String[]{getString(R.string.monday).substring(0, 3).toUpperCase(), getString(R.string.tuesday).substring(0, 3).toUpperCase(),
                getString(R.string.wednesday).substring(0, 3).toUpperCase(), getString(R.string.thursday).substring(0, 3).toUpperCase(),
                getString(R.string.friday).substring(0, 3).toUpperCase(), getString(R.string.saturday).substring(0, 3).toUpperCase(),
                getString(R.string.sunday).substring(0, 3).toUpperCase()};
    }

    public void getInformationFromDatabase(){
        MySQLiteHelper db = new MySQLiteHelper(getContext());
        Log.d(TAG, generateTimeQuery());
        Cursor res = db.getHistoryByDate(generateTimeQuery());
        if(res.getCount() > 0) {
            Log.d(TAG, "IT FOUND SOMETHING");
        } else {
            Log.d(TAG, "You should be ashamed that this code does not work");
        }
    }

    /**
     * This gets the month and the year that needs to be queried from
     * the database.
     * @return String: time query to get the information.
     */
    public String generateTimeQuery(){
        return getPreviousMonth() + getYear();
    }

    /**
     * Get the previous month.
     * @return String : previous month.
     */
    public String getPreviousMonth() {
        Calendar calendar = Calendar.getInstance();
        int thisMonth = calendar.get(Calendar.MONTH);

        // We want the previous month.
        int lastMonth = thisMonth - 1;

        String lastmonth = "";

        if (lastMonth < 1) {
            lastmonth = "12";
        }
        if (lastMonth < 10) {
            lastmonth = "0" + lastMonth;
        }

        return lastmonth;
    }

    /**
     * Get the current year.
     * @return String : current year.
     */
    public String getYear(){
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);

        return String.valueOf(thisYear);
    }

    class Monthly_Analysis extends AsyncTask<Void, Void, Void> {

        private String[] days;

        @Override
        protected Void doInBackground(Void... params) {

            days = makeDaysArray();



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
