package com.karl.fragments;


import android.graphics.Typeface;
import com.karl.dao.FoodDAO;
import com.karl.dao.IFoodDAO;
import com.karl.models.Food;
import com.karl.models.Goals;
import android.os.AsyncTask;
import android.os.Bundle;
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
 * A placeholder fragment containing a simple view.
 */
public class ResultServingFragment extends android.support.v4.app.Fragment {

    View rootView;

    ProgressBar progressBar;

    private String barcode = "0000000000000";

    public ResultServingFragment(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_result_per100, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.lookupProgressBar);
        progressBar.setVisibility(View.GONE);

        BarcodeSearchTask bst = new BarcodeSearchTask();
        bst.execute(barcode);

        setTypeface();

        return rootView;
    }

    public void setTypeface() {

        Typeface titleTypeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
        Typeface normalTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/New_Cicle_Gordita.ttf");

        // Stats textviews
        TextView text  = (TextView) rootView.findViewById(R.id.today_calories_stats);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_fat_stats);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_sat_fat_stats);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_salt_stats);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_sodium_stats);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_carbs_stats);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_sugar_stats);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_protein_stats);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);

        // Nutrients
        text = (TextView) rootView.findViewById(R.id.today_calories_text_view);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_fat_text_view);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_sat_fat_text_view);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_salt_text_view);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_sodium_text_view);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_carbohydrate_text_view);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_sugar_text_view);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
        text = (TextView) rootView.findViewById(R.id.today_protein_text_view);
        text.setTypeface(normalTypeface);
        text.setTextSize(20);
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

        /**
         * Set up the information in the view.
         * @param foods list of foods
         */
        @Override
        protected void onPostExecute(List<Food> foods) {
            super.onPostExecute(foods);

            final DecimalFormat df = new DecimalFormat("#.###");

            TextView calories_stats  = (TextView) rootView.findViewById(R.id.today_calories_stats);
            TextView fat_stats = (TextView) rootView.findViewById(R.id.today_fat_stats);
            TextView sat_fat_stats = (TextView) rootView.findViewById(R.id.today_sat_fat_stats);
            TextView salt_stats = (TextView) rootView.findViewById(R.id.today_salt_stats);
            TextView sodium_stats = (TextView) rootView.findViewById(R.id.today_sodium_stats);
            TextView carbs_stats = (TextView) rootView.findViewById(R.id.today_carbs_stats);
            TextView sugar_stats = (TextView) rootView.findViewById(R.id.today_sugar_stats);
            TextView protein_stats = (TextView) rootView.findViewById(R.id.today_protein_stats);

            final String calories = df.format(Float.parseFloat(foods.get(1).getCalories()));
            final String fats = df.format(Float.parseFloat(foods.get(1).getFats())) + getString(R.string.grams_abbv);
            final String sat_fats = df.format(Float.parseFloat(foods.get(1).getSat_fats())) + getString(R.string.grams_abbv);
            final String salts = df.format(Float.parseFloat(foods.get(1).getSalt())) + getString(R.string.grams_abbv);
            final String sodiums = df.format(Float.parseFloat(foods.get(1).getSodium())) + getString(R.string.grams_abbv);
            final String carbs = df.format(Float.parseFloat(foods.get(1).getCarbs())) + getString(R.string.grams_abbv);
            final String sugars = df.format(Float.parseFloat(foods.get(1).getSugar())) + getString(R.string.grams_abbv);
            final String proteins = df.format(Float.parseFloat(foods.get(1).getProtein())) + getString(R.string.grams_abbv);

            calories_stats.setText(calories);
            fat_stats.setText(fats);
            sat_fat_stats.setText(sat_fats);
            salt_stats.setText(salts);
            sodium_stats.setText(sodiums);
            carbs_stats.setText(carbs);
            sugar_stats.setText(sugars);
            protein_stats.setText(proteins);

            HorizontalBarChart chart = (HorizontalBarChart) rootView.findViewById(R.id.today_chart);
            BarData chartData = new BarData(getXAxisValues(), getDataSet(foods.get(1).getFats(), foods.get(1).getSat_fats(), foods.get(1).getProtein(),
                    foods.get(1).getSodium(), foods.get(1).getSalt(), foods.get(1).getSugar(), foods.get(1).getCarbs()));
            chart.setData(chartData);
            chart.setDescription(" ");
            chart.animateXY(2000, 2000);
            chart.invalidate();

            progressBar.setVisibility(View.GONE);
        }

        /**
         * Get the information for the barchart.
         * @param total_fats of the food.
         * @param total_sat_fats of the food.
         * @param total_protein of the food.
         * @param total_sodium of the food.
         * @param total_salt of the food.
         * @param total_sugar of the food.
         * @param total_carbs of the food.
         * @return list of data for the barchart.
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
         * Give the titles for the barchart.
         * @return arraylist of titles for the barchart.
         */
        private ArrayList<String> getXAxisValues() {
            ArrayList<String> xAxis = new ArrayList<>();
            xAxis.add("Sodium");
            xAxis.add("Salt");
            xAxis.add("Protein");
            xAxis.add("Sugar");
            xAxis.add("Carbs");
            xAxis.add("Sat Fat");
            xAxis.add("Fat");

            return xAxis;
        }

    }
}

