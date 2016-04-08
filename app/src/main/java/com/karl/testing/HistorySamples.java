package com.karl.testing;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Food;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Copyright Karl jones 2016.
 *
 * The HistorySamples mock object creates database entries that can be used to test functionality
 * of the application. It creates randomised objects for January and February for the application
 * to use for testing.
 */
public class HistorySamples {

    private static final String TAG = "HistorySamples";

    private static final String START_DATE = "1012016";
    private static final String START_DATE_FEB = "01022016";
    private static final int INCREMENT = 1000000;           // This is the same as moving up by a
    // day.

    // Max of each of the nutrients.
    private static final double FINAL_CALORIES = 3000f;
    private static final double FINAL_FAT = 100f;
    private static final double FINAL_SAT_FAT = 50;
    private static final double FINAL_SODIUM = 2.4f;
    private static final double FINAL_CARBS = 300f;
    private static final double FINAL_PROTEIN = 100f;
    private static final double FINAL_SUGAR = 160f;
    private static final double FINAL_SALT = 15f;

    private static Food food;
    private static MySQLiteHelper db;

    public HistorySamples(MySQLiteHelper db){
        this.db = db;
    }

    /**
     * Create randomised entries for the month of Jan.
     * @param context of the application.
     */
    public void setUpStatsJan(Context context){
        // Get the identifiers for the days.
        Resources res = context.getResources();
        final String[] days = new String[]{res.getString(R.string.monday).substring(0,3).toUpperCase(), res.getString(R.string.tuesday).substring(0,3).toUpperCase(),
                res.getString(R.string.wednesday).substring(0,3).toUpperCase(), res.getString(R.string.thursday).substring(0,3).toUpperCase(),
                res.getString(R.string.friday).substring(0,3).toUpperCase(), res.getString(R.string.saturday).substring(0,3).toUpperCase(),
                res.getString(R.string.sunday).substring(0,3).toUpperCase()};

        final DecimalFormat df = new DecimalFormat("#.###");

        Log.d(TAG, "START DATE " + Integer.parseInt(START_DATE));
        Log.d(TAG, "INCREMENT " + INCREMENT);

        // The first day of january is a friday
        int day_num = 4;

        /**
         * Loop through 30 days.
         */
        for(int i = Integer.parseInt(START_DATE); i <= 31012016; i += INCREMENT){

            food = new Food();

            // Set the date
            if(Integer.toString(i).length() < 8) {
                food.setDate(days[day_num] + "0" + Integer.toString(i));
            } else {
                food.setDate(days[day_num] + Integer.toString(i));
            }
            day_num++;

            if(day_num == 7){
                day_num = 0;
            }

            // Set up random nutrients.
            double cals = (Math.random() * (int)FINAL_CALORIES);
            food.setCalories(Integer.toString((int) cals));
            food.setFats(df.format(Math.random() * FINAL_FAT));
            food.setSaturated_fat(df.format(Math.random() * FINAL_SAT_FAT));
            food.setSodium(df.format(Math.random() * FINAL_SODIUM));
            food.setCarbohydrates(df.format(Math.random() * FINAL_CARBS));
            food.setSalt(df.format(Math.random() * FINAL_SALT));
            food.setSugar(df.format(Math.random() * FINAL_SUGAR));
            food.setProtein(df.format(Math.random() * FINAL_PROTEIN));

            db.createHistoryEntry(food);
        }
    }

    /**
     * Create randomised entries for the month of Feb.
     * @param context of the application.
     */
    public void setUpStatsFeb(Context context){
        // Get the identifiers for the days.
        Resources res = context.getResources();
        final String[] days = new String[]{res.getString(R.string.monday).substring(0,3).toUpperCase(), res.getString(R.string.tuesday).substring(0,3).toUpperCase(),
                res.getString(R.string.wednesday).substring(0,3).toUpperCase(), res.getString(R.string.thursday).substring(0,3).toUpperCase(),
                res.getString(R.string.friday).substring(0,3).toUpperCase(), res.getString(R.string.saturday).substring(0,3).toUpperCase(),
                res.getString(R.string.sunday).substring(0,3).toUpperCase()};

        final DecimalFormat df = new DecimalFormat("#.###");

        Log.d(TAG, "START DATE " + Integer.parseInt(START_DATE));
        Log.d(TAG, "INCREMENT " + INCREMENT);

        // The first day of february is a Monday.
        int day_num = 0;

        Random r = new Random();
        /**
         * Loop through 28 days (ignoring 2016 being a leap year).
         */
        for(int i = Integer.parseInt(START_DATE_FEB); i <= 28022016; i += INCREMENT){

            food = new Food();

            // Set the date
            if(Integer.toString(i).length() < 8) {
                food.setDate(days[day_num] + "0" + Integer.toString(i));
            } else {
                food.setDate(days[day_num] + Integer.toString(i));
            }
            day_num++;

            if(day_num == 7){
                day_num = 0;
            }

            // Set up random nutrients.
            double cals = (Math.random() * (int)FINAL_CALORIES);
            food.setCalories(Integer.toString((int) cals));
            food.setFats(df.format(Math.random() * FINAL_FAT));
            food.setSaturated_fat(df.format(Math.random() * FINAL_SAT_FAT));
            food.setSodium(df.format(Math.random() * FINAL_SODIUM));
            food.setCarbohydrates(df.format(Math.random() * FINAL_CARBS));
            food.setSalt(df.format(Math.random() * FINAL_SALT));
            food.setSugar(df.format(Math.random() * FINAL_SUGAR));
            food.setProtein(df.format(Math.random() * FINAL_PROTEIN));

            db.createHistoryEntry(food);
        }
    }
}
