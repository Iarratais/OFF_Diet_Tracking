package com.karl.fragments;

import android.app.DialogFragment;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.karl.fyp.MainActivity;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Food;
import com.karl.models.Goals;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Today fragment
 *
 * Copyright Karl Jones 2016
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
        setTypeface();

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
    public void setTypeface() {

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
        getInfoFromToday();
        calculateTotals();
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

        TextView calories_stats  = (TextView) rootView.findViewById(R.id.today_calories_stats);
        TextView fat_stats = (TextView) rootView.findViewById(R.id.today_fat_stats);
        TextView sat_fat_stats = (TextView) rootView.findViewById(R.id.today_sat_fat_stats);
        TextView salt_stats = (TextView) rootView.findViewById(R.id.today_salt_stats);
        TextView sodium_stats = (TextView) rootView.findViewById(R.id.today_sodium_stats);
        TextView carbs_stats = (TextView) rootView.findViewById(R.id.today_carbs_stats);
        TextView sugar_stats = (TextView) rootView.findViewById(R.id.today_sugar_stats);
        TextView protein_stats = (TextView) rootView.findViewById(R.id.today_protein_stats);

        final String calories = df.format(Float.parseFloat(total_calories));
        final String fats = df.format(Float.parseFloat(total_fats)) + getString(R.string.grams_abbv);
        final String sat_fats = df.format(Float.parseFloat(total_sat_fats)) + getString(R.string.grams_abbv);
        final String salts = df.format(Float.parseFloat(total_salt)) + getString(R.string.grams_abbv);
        final String sodiums = df.format(Float.parseFloat(total_sodium)) + getString(R.string.grams_abbv);
        final String carbs = df.format(Float.parseFloat(total_carbs)) + getString(R.string.grams_abbv);
        final String sugars = df.format(Float.parseFloat(total_sugar)) + getString(R.string.grams_abbv);
        final String proteins = df.format(Float.parseFloat(total_protein)) + getString(R.string.grams_abbv);

        calories_stats.setText(calories);
        fat_stats.setText(fats);
        sat_fat_stats.setText(sat_fats);
        salt_stats.setText(salts);
        sodium_stats.setText(sodiums);
        carbs_stats.setText(carbs);
        sugar_stats.setText(sugars);
        protein_stats.setText(proteins);

        setColorOfCalories(Float.parseFloat(goals.getCalories()), calories_stats);
        setColorOfFat(Float.parseFloat(goals.getFat()), fat_stats);
        setColorOfSatFat(Float.parseFloat(goals.getSaturatedFat()), sat_fat_stats);
        setColorOfSalt(Float.parseFloat(goals.getSalt()), salt_stats);
        setColorOfSodium(Float.parseFloat(goals.getSodium()), sodium_stats);
        setColorOfCarbs(Float.parseFloat(goals.getCarbohydrates()), carbs_stats);
        setColorOfSugar(Float.parseFloat(goals.getSugar()), sugar_stats);
        setColorofProteins(Float.parseFloat(goals.getProtein()), protein_stats);

        calories_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_calorie_goal) + " " + goals.getCalories();
                if (Float.parseFloat(total_calories) > Float.parseFloat(goals.getCalories())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_calories) - Float.parseFloat(goals.getCalories()));
                }
                makeAlert(getString(R.string.calories), message);
            }
        });
        fat_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_fat_goal) + " " + goals.getFat();
                if (Float.parseFloat(total_fats) > Float.parseFloat(goals.getFat())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_fats) - Float.parseFloat(goals.getFat()));
                }
                makeAlert(getString(R.string.fat), message);
            }
        });
        sat_fat_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_sat_fat_goal) + " " + goals.getSaturatedFat();
                if (Float.parseFloat(total_sat_fats) > Float.parseFloat(goals.getSaturatedFat())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_sat_fats) - Float.parseFloat(goals.getSaturatedFat()));
                }
                makeAlert(getString(R.string.saturated_fat), message);
            }
        });
        salt_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_salt_goal) + " " + goals.getSalt();
                if (Float.parseFloat(total_salt) > Float.parseFloat(goals.getSalt())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_salt) - Float.parseFloat(goals.getSalt()));
                }
                makeAlert(getString(R.string.salt), message);
            }
        });
        sodium_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_sodium_goal) + " " + goals.getSodium();
                if (Float.parseFloat(total_sodium) > Float.parseFloat(goals.getSodium())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_sodium) - Float.parseFloat(goals.getSodium()));
                }
                makeAlert(getString(R.string.sodium), message);
            }
        });
        carbs_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_carbs_goal) + " " + goals.getCarbohydrates();
                if (Float.parseFloat(total_carbs) > Float.parseFloat(goals.getCarbohydrates())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_carbs) - Float.parseFloat(goals.getCarbohydrates()));
                }
                makeAlert(getString(R.string.carbohydrate), message);
            }
        });
        sugar_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_sugar_goal) + " " + goals.getSugar();
                if (Float.parseFloat(total_sugar) > Float.parseFloat(goals.getSugar())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_sugar) - Float.parseFloat(goals.getSugar()));
                }
                makeAlert(getString(R.string.sugar), message);
            }
        });
        protein_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_protein_goal) + " " + goals.getProtein();
                if (Float.parseFloat(total_protein) > Float.parseFloat(goals.getProtein())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_protein) - Float.parseFloat(goals.getProtein()));
                }
                makeAlert(getString(R.string.protein), message);
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
    public void calculateTotals() {
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
    public void makeAlert(String title, String message) {
        DialogFragment dialogFragment = MyAlertDialogFragment.newInstance(title, message);
        dialogFragment.show(getActivity().getFragmentManager(), "dialog");
    }

    /**
     * Get information from the Today table.
     */
    public void getInfoFromToday() {
        foods = new ArrayList<>();

        Cursor res = db.returnTodaysEntries();
        if(res.getCount() == 0) {
            Log.d(TAG, "There is no information in todays entries");
            nothingToShow();
        } else {
            while(res.moveToNext()){
                Food food = new Food();
                food.setId(res.getString(0));
                food.setName(res.getString(2));
                food.setBarcode_number(res.getString(3));
                foods.add(food);
            }
        }
        getInfoFromTodayStats();
    }

    /**
     * Get information from the Today Stats table.
     */
    public void getInfoFromTodayStats() {
        Cursor res = db.returnTodayStatsEntries();
        if(res.getCount() == 0) {
            Log.d(TAG, "There is no information in todays stat entries");
            nothingToShow();
        } else {
            int i = 0;
            while(res.moveToNext()){
                if(res.getString(0).equals(foods.get(i).getId())){
                    foods.get(i).setCalories(res.getString(2));
                    foods.get(i).setFats(res.getString(3));
                    foods.get(i).setSaturated_fat(res.getString(4));
                    foods.get(i).setCarbohydrates(res.getString(5));
                    foods.get(i).setSugar(res.getString(6));
                    foods.get(i).setProtein(res.getString(7));
                    foods.get(i).setSalt(res.getString(8));
                    foods.get(i).setSodium(res.getString(9));
                }
                i++;
            }
        }
    }

    public void nothingToShow(){
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
