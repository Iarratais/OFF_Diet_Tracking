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

import com.karl.analysis.Analyse;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Day;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class YearAnalysisFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "YearAnalysisFragment";

    Analyse analyse;

    View rootView;

    public YearAnalysisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_year_analysis, container, false);

        Yearly_Analysis ya = new Yearly_Analysis();
        ya.execute();

        return rootView;
    }

    public String[] makeDaysArray(){
        return new String[]{getString(R.string.monday).substring(0, 3).toUpperCase(), getString(R.string.tuesday).substring(0, 3).toUpperCase(),
                getString(R.string.wednesday).substring(0, 3).toUpperCase(), getString(R.string.thursday).substring(0, 3).toUpperCase(),
                getString(R.string.friday).substring(0, 3).toUpperCase(), getString(R.string.saturday).substring(0, 3).toUpperCase(),
                getString(R.string.sunday).substring(0, 3).toUpperCase()};
    }

    public ArrayList<Day> getInformationFromDatabase(){
        MySQLiteHelper db = new MySQLiteHelper(getContext());

        ArrayList<Day> historydays = new ArrayList<>();

        Log.d(TAG, generateTimeQuery());
        Cursor res = db.getHistoryByDate(generateTimeQuery());

        if(res.getCount() == 0) {
            Log.d(TAG, "You should be ashamed that this code does not work");
        } else {
            while(res.moveToNext()) {
                // Column count = 10
                // history_id date calories fats sat_fat salt sodium carbohydrates sugar protein
                Day day = new Day();
                day.setId(res.getString(0));
                day.setDate(res.getString(1));
                day.setCalories(res.getString(2));
                day.setFats(res.getString(3));
                day.setSaturated_fat(res.getString(4));
                day.setSalt(res.getString(5));
                day.setSodium(res.getString(6));
                day.setCarbs(res.getString(7));
                day.setSugar(res.getString(8));
                day.setProtein(res.getString(9));

                historydays.add(day);
            }
        }

        Log.d(TAG, "Based off: " + res.getCount() + " entries");

        return historydays;
    }

    /**
     * This gets the month and the year that needs to be queried from
     * the database.
     * @return String: time query to get the information.
     */
    public String generateTimeQuery(){
        return getYear();
    }

    /**
     * Get the previous month.
     * @return String : previous month.
     */
    public String getPreviousMonth() {
        Calendar calendar = Calendar.getInstance();
        int thisMonth = calendar.get(Calendar.MONTH);

        String lastmonth = "";

        if (thisMonth < 10) {
            lastmonth = "0" + thisMonth;
        } else {
            lastmonth = String.valueOf(thisMonth);
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

    class Yearly_Analysis extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            analyse = new Analyse(getContext(), getInformationFromDatabase());

            Log.d(TAG, "Calories: " + analyse.getMostCalories());
            Log.d(TAG, "Day with most calories consumed: " + analyse.getDayMostCalories());

            Log.d(TAG, "Fats: " + analyse.getMostFats());
            Log.d(TAG, "Day with most fats consumed: " + analyse.getDayMostFats());

            Log.d(TAG, "Sat Fats: " + analyse.getMostSatFat());
            Log.d(TAG, "Day with most saturated fats: " + analyse.getDayMostSatFat());

            Log.d(TAG, "Salts: " + analyse.getMostSalt());
            Log.d(TAG, "Day with most salts: " + analyse.getDayMostSalt());

            Log.d(TAG, "Sodium: " + analyse.getMostSodium());
            Log.d(TAG, "Day with most sodium: " + analyse.getDayMostSodium());

            Log.d(TAG, "Carbohydrates: " + analyse.getMostCarbohydrates());
            Log.d(TAG, "Day with most carbs: " + analyse.getDayMostCarbohydrates());

            Log.d(TAG, "Sugar: " + analyse.getMostSugar());
            Log.d(TAG, "Day with most sugar: " + analyse.getDayMostSugar());

            Log.d(TAG, "Protein: " + analyse.getMostProtein());
            Log.d(TAG, "Day with most protein: " + analyse.getDayMostProtein());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
