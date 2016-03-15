package com.karl.fragments;

import android.app.DialogFragment;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.karl.alerts.MyAlertDialogFragment;
import com.karl.fyp.MainActivity;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Food;
import com.karl.models.Goals;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Copyright Karl jones 2016.
 * TodayFragment
 *
 * This shows the user a barchart with information of the foods that they have consumed and
 * entered into the system for the day.
 */
public class TodayFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "TodayFragment";

    View rootView;

    MySQLiteHelper db;

    String total_calories = "0.0";
    String total_fats = "0.0";
    String total_sat_fats = "0.0";
    String total_carbs = "0.0";
    String total_sugar = "0.0";
    String total_protein = "0.0";
    String total_salt = "0.0";
    String total_sodium = "0.0";

    ArrayList<Food> foods;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.content_fragment_today, container, false);

        db = new MySQLiteHelper(getActivity());

        // Set the fonts of the UI
        setViewTypeface();

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.today_fragment_title));

        getInformation();

        // Barchart information and initialisation
        HorizontalBarChart chart = (HorizontalBarChart) rootView.findViewById(R.id.today_chart);
        BarData chartData = new BarData(getXAxisValues(), getDataSet());
        chart.setData(chartData);
        chart.setDescription(" ");
        chart.animateXY(2000, 2000);
        chart.invalidate();

        return rootView;
    }

    /**
     *
     * @return ArrayList of data for the chart
     */
    private ArrayList<BarDataSet> getDataSet() {
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
     * Give the x-axis names
     * @return ArrayList of data for the titles of the chart
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

    /**
     * Set the typeface for the information displayed
     */
    public void setViewTypeface() {

        Typeface titleTypeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
        Typeface normalTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/New_Cicle_Gordita.ttf");

        // Set up titles
        // Chart title
        TextView chart_title = (TextView) rootView.findViewById(R.id.today_chart_title);
        chart_title.setTypeface(titleTypeFace);
        chart_title.setTextSize(25);

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

    /**
     * Deal with the information this fragment needs.
     */
    public void getInformation() {
        getInformationFromToday();
        calculateNutrientTotalsForToday();
        setUpGoalsTable();
    }

    /**
     * Get the goals
     * @return Goal, containing information about the users goals.
     */
    public Goals getGoalsFromDatabase(){
        Cursor res = db.getGoals();

        if(res.getCount() == 0) {
            makeToast(getString(R.string.error_please_set_goals_first));
        } else {
            Goals goal = new Goals();
            while(res.moveToNext()){
                goal.setCalories(res.getString(1));
                goal.setFat(res.getString(2));
                goal.setSaturatedFat(res.getString(3));
                goal.setSalt(res.getString(4));
                goal.setSodium(res.getString(5));
                goal.setCarbohydrates(res.getString(6));
                goal.setSugar(res.getString(7));
                goal.setProtein(res.getString(8));
            }
            return goal;
        }
        return null;
    }

    /**
     * Set the information into the buttons.
     */
    public void setUpGoalsTable() {
        final Goals goals = getGoalsFromDatabase();
        final DecimalFormat df = new DecimalFormat("#.###");

        TextView resultCalorieStatsTextView         = (TextView) rootView.findViewById(R.id.today_calories_stats);
        TextView resultFatStatsTextView             = (TextView) rootView.findViewById(R.id.today_fat_stats);
        TextView resultSatFatStatsTextView          = (TextView) rootView.findViewById(R.id.today_sat_fat_stats);
        TextView resultSaltStatsTextView            = (TextView) rootView.findViewById(R.id.today_salt_stats);
        TextView resultSodiumStatsTextView          = (TextView) rootView.findViewById(R.id.today_sodium_stats);
        TextView resultCarbohydratesStatsTextView   = (TextView) rootView.findViewById(R.id.today_carbs_stats);
        TextView resultSugarStatsTextView           = (TextView) rootView.findViewById(R.id.today_sugar_stats);
        TextView resultProteinStatsTextView         = (TextView) rootView.findViewById(R.id.today_protein_stats);

        final String resultCalories   = df.format(Float.parseFloat(goals.getCalories()));
        final String resultFats       = df.format(Float.parseFloat(goals.getFat())) +
                getString(R.string.grams_abbv);
        final String resultSatFats    = df.format(Float.parseFloat(goals.getSaturatedFat())) + getString(R.string.grams_abbv);
        final String resultSalts      = df.format(Float.parseFloat(goals.getSalt())) +
                getString(R.string.grams_abbv);
        final String resultSodium     = df.format(Float.parseFloat(goals.getSodium())) +
                getString(R.string.grams_abbv);
        final String resultCarbohydrates      = df.format(Float.parseFloat(goals.getCarbohydrates())) + getString(R.string.grams_abbv);
        final String resultSugars     = df.format(Float.parseFloat(goals.getSugar())) +
                getString(R.string.grams_abbv);
        final String resultProteins   = df.format(Float.parseFloat(goals.getProtein())
        ) + getString(R.string.grams_abbv);


        resultCalorieStatsTextView.setText(resultCalories);
        resultFatStatsTextView.setText(resultFats);
        resultSatFatStatsTextView.setText(resultSatFats);
        resultSaltStatsTextView.setText(resultSalts);
        resultSodiumStatsTextView.setText(resultSodium);
        resultCarbohydratesStatsTextView.setText(resultCarbohydrates);
        resultSugarStatsTextView.setText(resultSugars);
        resultProteinStatsTextView.setText(resultProteins);

        setColorOfCalories(Float.parseFloat(goals.getCalories()), resultCalorieStatsTextView);
        setColorOfFat(Float.parseFloat(goals.getFat()), resultFatStatsTextView);
        setColorOfSatFat(Float.parseFloat(goals.getSaturatedFat()), resultSatFatStatsTextView);
        setColorOfSalt(Float.parseFloat(goals.getSalt()), resultSaltStatsTextView);
        setColorOfSodium(Float.parseFloat(goals.getSodium()), resultSodiumStatsTextView);
        setColorOfCarbs(Float.parseFloat(goals.getCarbohydrates()), resultCarbohydratesStatsTextView);
        setColorOfSugar(Float.parseFloat(goals.getSugar()), resultSugarStatsTextView);
        setColorofProteins(Float.parseFloat(goals.getProtein()), resultProteinStatsTextView);

        resultCalorieStatsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_calorie_goal) + " " + goals.getCalories();
                if (Float.parseFloat(total_calories) > Float.parseFloat(goals.getCalories())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_calories) - Float.parseFloat(goals.getCalories()));
                }
                createAlertDialog(getString(R.string.calories), message);
            }
        });
        resultFatStatsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_fat_goal) + " " + goals.getFat();
                if (Float.parseFloat(total_fats) > Float.parseFloat(goals.getFat())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_fats) - Float.parseFloat(goals.getFat()));
                }
                createAlertDialog(getString(R.string.fat), message);
            }
        });
        resultSatFatStatsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_sat_fat_goal) + " " + goals.getSaturatedFat();
                if (Float.parseFloat(total_sat_fats) > Float.parseFloat(goals.getSaturatedFat())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_sat_fats) - Float.parseFloat(goals.getSaturatedFat()));
                }
                createAlertDialog(getString(R.string.saturated_fat), message);
            }
        });
        resultSaltStatsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_salt_goal) + " " + goals.getSalt();
                if (Float.parseFloat(total_salt) > Float.parseFloat(goals.getSalt())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_salt) - Float.parseFloat(goals.getSalt()));
                }
                createAlertDialog(getString(R.string.salt), message);
            }
        });
        resultSodiumStatsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_sodium_goal) + " " + goals.getSodium();
                if (Float.parseFloat(total_sodium) > Float.parseFloat(goals.getSodium())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_sodium) - Float.parseFloat(goals.getSodium()));
                }
                createAlertDialog(getString(R.string.sodium), message);
            }
        });
        resultCarbohydratesStatsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_carbs_goal) + " " + goals.getCarbohydrates();
                if (Float.parseFloat(total_carbs) > Float.parseFloat(goals.getCarbohydrates())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_carbs) - Float.parseFloat(goals.getCarbohydrates()));
                }
                createAlertDialog(getString(R.string.carbohydrate), message);
            }
        });
        resultSugarStatsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_sugar_goal) + " " + goals.getSugar();
                if (Float.parseFloat(total_sugar) > Float.parseFloat(goals.getSugar())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_sugar) - Float.parseFloat(goals.getSugar()));
                }
                createAlertDialog(getString(R.string.sugar), message);
            }
        });
        resultProteinStatsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_protein_goal) + " " + goals.getProtein();
                if (Float.parseFloat(total_protein) > Float.parseFloat(goals.getProtein())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_protein) - Float.parseFloat(goals.getProtein()));
                }
                createAlertDialog(getString(R.string.protein), message);
            }
        });
    }

    public void setColorOfCalories(float calories, TextView text) {
        if(calories < Float.parseFloat(total_calories)) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorOfFat(float fat, TextView text) {
        if(fat < Float.parseFloat(total_fats)) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorOfSatFat(float satFat, TextView text) {
        if(satFat < Float.parseFloat(total_sat_fats)) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorOfSalt(float salt, TextView text) {
        if(salt < Float.parseFloat(total_salt)) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorOfSodium(float sodium, TextView text) {
        if(sodium < Float.parseFloat(total_sodium)) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorOfCarbs(float carb, TextView text) {
        if(carb < Float.parseFloat(total_carbs)) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorOfSugar(float sugar, TextView text) {
        if(sugar < Float.parseFloat(total_sugar)) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorofProteins(float protein, TextView text) {
        if(protein < Float.parseFloat(total_protein)) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    /**
     * Calculate the totals to be able to display the information.
     */
    public void calculateNutrientTotalsForToday() {
        float temp_calories = Float.parseFloat(total_calories);
        float temp_fat = Float.parseFloat(total_fats);
        float temp_sat_fat = Float.parseFloat(total_sat_fats);
        float temp_carbs = Float.parseFloat(total_carbs);
        float temp_sugar = Float.parseFloat(total_sugar);
        float temp_protein = Float.parseFloat(total_protein);
        float temp_salt = Float.parseFloat(total_salt);
        float temp_sodium = Float.parseFloat(total_sodium);

        for (int i = 0; i < foods.size(); i++) {
            temp_calories += Float.parseFloat(foods.get(i).getCalories());
            temp_fat += Float.parseFloat(foods.get(i).getFats());
            temp_sat_fat += Float.parseFloat(foods.get(i).getSaturated_fat());
            temp_carbs += Float.parseFloat(foods.get(i).getCarbohydrates());
            temp_sugar += Float.parseFloat(foods.get(i).getSugar());
            temp_protein += Float.parseFloat(foods.get(i).getProtein());
            temp_salt += Float.parseFloat(foods.get(i).getSalt());
            temp_sodium += Float.parseFloat(foods.get(i).getSodium());
        }

        total_calories = String.valueOf(temp_calories);
        total_fats = String.valueOf(temp_fat);
        total_sat_fats = String.valueOf(temp_sat_fat);
        total_carbs = String.valueOf(temp_carbs);
        total_sugar = String.valueOf(temp_sugar);
        total_protein = String.valueOf(temp_protein);
        total_salt = String.valueOf(temp_salt);
        total_sodium = String.valueOf(temp_sodium);
    }

    /**
     * Create a toast
     * @param message to be displayed
     */
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Create an alert dialog for the user.
     * @param title of the alert.
     * @param message to be contained in the alert.
     */
    public void createAlertDialog(String title, String message) {
        DialogFragment dialogFragment = MyAlertDialogFragment.newInstance(title, message);
        dialogFragment.show(getActivity().getFragmentManager(), "dialog");
    }

    /**
     * Get information from the Today table.
     */
    public void getInformationFromToday() {
        foods = new ArrayList<>();

        Cursor todaysEntries = db.returnTodaysEntries();
        if(todaysEntries.getCount() == 0) {
            noInformationToShowUser();
        } else {
            while(todaysEntries.moveToNext()){
                Food food = new Food();
                food.setId(todaysEntries.getString(0));
                food.setName(todaysEntries.getString(2));
                food.setBarcode_number(todaysEntries.getString(3));
                foods.add(food);
            }
        }
        getInformationFromTodayStats();
    }

    /**
     * Get information from the Today Stats table.
     */
    public void getInformationFromTodayStats() {
        Cursor todayStatEntries = db.returnTodayStatsEntries();
        if(todayStatEntries.getCount() == 0) {
            noInformationToShowUser();
        } else {
            int i = 0;
            while(todayStatEntries.moveToNext()){
                if(todayStatEntries.getString(0).equals(foods.get(i).getId())){
                    foods.get(i).setCalories(todayStatEntries.getString(2));
                    foods.get(i).setFats(todayStatEntries.getString(3));
                    foods.get(i).setSaturated_fat(todayStatEntries.getString(4));
                    foods.get(i).setCarbohydrates(todayStatEntries.getString(5));
                    foods.get(i).setSugar(todayStatEntries.getString(6));
                    foods.get(i).setProtein(todayStatEntries.getString(7));
                    foods.get(i).setSalt(todayStatEntries.getString(8));
                    foods.get(i).setSodium(todayStatEntries.getString(9));
                }
                i++;
            }
        }
    }

    public void noInformationToShowUser(){
        ArrayList<View> views = new ArrayList<>();

        // Title of the chart
        View view = rootView.findViewById(R.id.today_chart_title);
        views.add(view);

        // Today chart
        view = rootView.findViewById(R.id.today_chart);
        views.add(view);

        // Calories
        view = rootView.findViewById(R.id.today_calories_text_view);
        views.add(view);
        view = rootView.findViewById(R.id.today_calories_stats);
        views.add(view);

        // Fats
        view = rootView.findViewById(R.id.today_fat_text_view);
        views.add(view);
        view = rootView.findViewById(R.id.today_fat_stats);
        views.add(view);

        // Sat fats
        view = rootView.findViewById(R.id.today_sat_fat_text_view);
        views.add(view);
        view = rootView.findViewById(R.id.today_sat_fat_stats);
        views.add(view);

        // Salt
        view = rootView.findViewById(R.id.today_salt_text_view);
        views.add(view);
        view = rootView.findViewById(R.id.today_salt_stats);
        views.add(view);

        // Sodium
        view = rootView.findViewById(R.id.today_sodium_text_view);
        views.add(view);
        view = rootView.findViewById(R.id.today_sodium_stats);
        views.add(view);

        // Carbohydrates
        view = rootView.findViewById(R.id.today_carbohydrate_text_view);
        views.add(view);
        view = rootView.findViewById(R.id.today_carbs_stats);
        views.add(view);

        // Sugar
        view = rootView.findViewById(R.id.today_sugar_text_view);
        views.add(view);
        view = rootView.findViewById(R.id.today_sugar_stats);
        views.add(view);

        // Protein
        view = rootView.findViewById(R.id.today_protein_text_view);
        views.add(view);
        view = rootView.findViewById(R.id.today_protein_stats);
        views.add(view);

        view = rootView.findViewById(R.id.nothing_to_show_today);
        view.setVisibility(View.VISIBLE);
        view = rootView.findViewById(R.id.nothing_to_show_text_today);
        view.setVisibility(View.VISIBLE);

        setViewVisibilityFalse(views);
    }

    public void setViewVisibilityFalse(ArrayList<View> v){
        for(int i = 0; i < v.size(); i++){
            v.get(i).setVisibility(View.GONE);
        }
    }
}
