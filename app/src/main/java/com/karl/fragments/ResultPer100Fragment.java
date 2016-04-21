package com.karl.fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import com.karl.dao.FoodDAO;
import com.karl.dao.IFoodDAO;

import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.NewManualEntryActivity;
import com.karl.fyp.SearchResultActivity;
import com.karl.models.Food;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karl.fyp.R;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Karl jones 2016.
 * ResultPer100Fragment
 *
 * This fragment shows the user information about a food type by 100g/ml.
 */

public class ResultPer100Fragment extends android.support.v4.app.Fragment {

    private static final String TAG = "ResultPer100Fragment";

    View rootView;

    ProgressBar progressBar;

    private String inputBarcode = "0000000000000";
    MySQLiteHelper db;

    public ResultPer100Fragment(){}

    public ResultPer100Fragment(String inputBarcode) {
        this.inputBarcode = inputBarcode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_result_per100, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.lookupProgressBar);
        progressBar.setVisibility(View.GONE);

        db = new MySQLiteHelper(getActivity());

        if(db.checkIfItemExists100(inputBarcode)) {
            Log.d(TAG, "Item does not exist in the database");
            if(isNetworkOnline()) {
                BarcodeSearchTask bst = new BarcodeSearchTask();
                bst.execute(inputBarcode);
            } else {
                sendToManual(inputBarcode);
            }
        } else {
            Log.d(TAG, "Item does exist in the database");

            List<Food> foodItem = new ArrayList<>();
            Cursor res = db.getQuery100Information(inputBarcode);
            if(res.getCount() == 0){
                Log.d(TAG, "Error");
            } else {
                Food food = new Food();
                while(res.moveToNext()){
                    food.setCalories(res.getString(2));
                    food.setFats(res.getString(3));
                    food.setSaturated_fat(res.getString(4));
                    food.setSalt(res.getString(5));
                    food.setSodium(res.getString(6));
                    food.setCarbohydrates(res.getString(7));
                    food.setSugar(res.getString(8));
                    food.setProtein(res.getString(9));
                }
                foodItem.add(food);
            }
            fillInInfo(foodItem);
        }

        setViewTypeface();

        return rootView;
    }

    public void sendToManual(String barcode){
        Intent i = new Intent(getActivity(), NewManualEntryActivity.class);
        i.putExtra("barcode", barcode);
        startActivity(i);
    }

    /**
     * Check if the network is connected or not.
     * @return network connected status.
     */
    public boolean isNetworkOnline() {
        boolean status = false;

        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService
                    (Context.CONNECTIVITY_SERVICE);
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

    /**
     * Set the typeface for the TextViews in the layout.
     */
    public void setViewTypeface() {
        Typeface normalTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/New_Cicle_Gordita.ttf");

        TextView layoutTextView  = (TextView) rootView.findViewById(R.id.today_calories_stats);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_fat_stats);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_sat_fat_stats);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_salt_stats);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_sodium_stats);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_carbs_stats);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_sugar_stats);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_protein_stats);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);

        // Nutrients
        layoutTextView = (TextView) rootView.findViewById(R.id.today_calories_text_view);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_fat_text_view);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_sat_fat_text_view);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_salt_text_view);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_sodium_text_view);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_carbohydrate_text_view);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_sugar_text_view);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
        layoutTextView = (TextView) rootView.findViewById(R.id.today_protein_text_view);
        layoutTextView.setTypeface(normalTypeface);
        layoutTextView.setTextSize(20);
    }


    public void pushInformationBack(Food food){
        if(food != null) {
            ((SearchResultActivity) getActivity()).setInformation(food);
        }
    }

    public void fillInInfo(List<Food> foods){

        final DecimalFormat df = new DecimalFormat("#.###");

        TextView resultCalorieStatsTextView         = (TextView) rootView.findViewById(R.id.today_calories_stats);
        TextView resultFatStatsTextView             = (TextView) rootView.findViewById(R.id.today_fat_stats);
        TextView resultSatFatStatsTextView          = (TextView) rootView.findViewById(R.id.today_sat_fat_stats);
        TextView resultSaltStatsTextView            = (TextView) rootView.findViewById(R.id.today_salt_stats);
        TextView resultSodiumStatsTextView          = (TextView) rootView.findViewById(R.id.today_sodium_stats);
        TextView resultCarbohydratesStatsTextView   = (TextView) rootView.findViewById(R.id.today_carbs_stats);
        TextView resultSugarStatsTextView           = (TextView) rootView.findViewById(R.id.today_sugar_stats);
        TextView resultProteinStatsTextView         = (TextView) rootView.findViewById(R.id.today_protein_stats);

        final String resultCalories   = df.format(Float.parseFloat(foods.get(0).getCalories()));
        final String resultFats       = df.format(Float.parseFloat(foods.get(0).getFats())) +
                getString(R.string.grams_abbv);
        final String resultSatFats    = df.format(Float.parseFloat(foods.get(0)
                .getSaturated_fat())) + getString(R.string.grams_abbv);
        final String resultSalts      = df.format(Float.parseFloat(foods.get(0).getSalt())) +
                getString(R.string.grams_abbv);
        final String resultSodium     = df.format(Float.parseFloat(foods.get(0).getSodium())) +
                getString(R.string.grams_abbv);
        final String resultCarbohydrates      = df.format(Float.parseFloat(foods.get(0)
                .getCarbohydrates())) + getString(R.string.grams_abbv);
        final String resultSugars     = df.format(Float.parseFloat(foods.get(0).getSugar())) +
                getString(R.string.grams_abbv);
        final String resultProteins   = df.format(Float.parseFloat(foods.get(0).getProtein())
        ) + getString(R.string.grams_abbv);

        ((SearchResultActivity) getActivity()).setActionbarTitle(foods.get(0).getName());

        // Send the serving size back to the activity
        pushInformationBack(foods.get(0));

        resultCalorieStatsTextView.setText(resultCalories);
        resultFatStatsTextView.setText(resultFats);
        resultSatFatStatsTextView.setText(resultSatFats);
        resultSaltStatsTextView.setText(resultSalts);
        resultSodiumStatsTextView.setText(resultSodium);
        resultCarbohydratesStatsTextView.setText(resultCarbohydrates);
        resultSugarStatsTextView.setText(resultSugars);
        resultProteinStatsTextView.setText(resultProteins);

        HorizontalBarChart chart = (HorizontalBarChart) rootView.findViewById(R.id.today_chart);
        BarData chartData = new BarData(getXAxisValues(), getDataSet(foods.get(0).getFats(), foods.get(0).getSaturated_fat(), foods.get(0).getProtein(),
                foods.get(0).getSodium(), foods.get(0).getSalt(), foods.get(0).getSugar(), foods.get(0).getCarbohydrates()));
        chart.setData(chartData);
        chart.setDescription(" ");
        chart.animateXY(2000, 2000);
        chart.invalidate();

        progressBar.setVisibility(View.GONE);
    }

    /**
     * Get the dataset that is to be used in the barchart.
     * @param total_fats fats
     * @param total_sat_fats saturated fats
     * @param total_protein protein
     * @param total_sodium sodium
     * @param total_salt salt
     * @param total_sugar sugar
     * @param total_carbs carbohydrates
     * @return ArrayList with the data for the chart
     */
    private ArrayList<BarDataSet> getDataSet(String total_fats, String total_sat_fats, String total_protein, String total_sodium, String total_salt, String total_sugar, String total_carbs) {
        ArrayList<BarDataSet> dataSets = new ArrayList<>();

        // Add the data to the ArrayList
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry value1 = new BarEntry(Float.parseFloat(total_sodium), 0); // Sodium
        valueSet1.add(value1);
        BarEntry value2 = new BarEntry(Float.parseFloat(total_salt), 1); // Salt
        valueSet1.add(value2);
        BarEntry value3 = new BarEntry(Float.parseFloat(total_protein), 2); // Proteins
        valueSet1.add(value3);
        BarEntry value4 = new BarEntry(Float.parseFloat(total_sugar), 3); // Sugar
        valueSet1.add(value4);
        BarEntry value5 = new BarEntry(Float.parseFloat(total_carbs), 4); // Carbohydrates
        valueSet1.add(value5);
        BarEntry value6 = new BarEntry(Float.parseFloat(total_sat_fats), 5); // Saturated Fat
        valueSet1.add(value6);
        BarEntry value7 = new BarEntry(Float.parseFloat(total_fats), 6); // Fats
        valueSet1.add(value7);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, " ");
        barDataSet1.setColors(ColorTemplate.PASTEL_COLORS);

        dataSets.add(barDataSet1);

        return dataSets;
    }

    /**
     * Get the labels for the X-axis of the barchart.
     * @return arraylist with labels.
     */
    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add(getString(R.string.sodium));
        xAxis.add(getString(R.string.salt));
        xAxis.add(getString(R.string.protein));
        xAxis.add(getString(R.string.sugar));
        xAxis.add(getString(R.string.carbohydrate));
        xAxis.add(getString(R.string.saturated_fat));
        xAxis.add(getString(R.string.fat));

        return xAxis;
    }

    class BarcodeSearchTask extends AsyncTask<String, Integer, List<Food>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
        }

        // User can pass in as many strings, params is an array
        // On a separate thread
        @Override
        protected List<Food> doInBackground(String... params) {
            IFoodDAO foodDAO = new FoodDAO();

            try {
                return foodDAO.fetchFood(params[0]);        // First element
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<Food>();
            }
        }

        // This is on the UI thread
        @Override
        protected void onPostExecute(List<Food> foods) {
            super.onPostExecute(foods);

            db.insertIntoQuery100(foods.get(0));

            fillInInfo(foods);
        }
    }
}
