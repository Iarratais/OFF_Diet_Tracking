package com.karl.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Typeface;
import com.karl.models.Food;
import com.karl.examples.ExampleGoals;
import com.karl.models.Goals;
import com.karl.fyp.MainActivity;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
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

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Today fragment
 *
 * Copyright Karl Jones 2016
 */
public class TodayFragment extends android.support.v4.app.Fragment {

    View rootView;
    ArrayList<Food> ef;
    Goals goals;

    MySQLiteHelper db;

    String total_calories = "0.0";
    String total_fats = "0.0";
    String total_sat_fats = "0.0";
    String total_carbs = "0.0";
    String total_sugar = "0.0";
    String total_protein = "0.0";
    String total_salt = "0.0";
    String total_sodium = "0.0";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.content_fragment_today, container, false);

        db = new MySQLiteHelper(getActivity());

        ef = new ArrayList<>();

        // Dairygold
        ef.add(new Food("Dairygold", "000000", "57", "6.3", "2.3", "0.0", "0.1", "0.0", "0.199", "0.0787"));

        // Nutella
        ef.add(new Food("Nutella", "000000", "83", "4.75", "1.64", "8.59", "8.51", "0.9", "0.0141", "0.00555"));

        // Royal Bacon - MCDonalds
        ef.add(new Food("Royal Bacon", "000000", "504", "25.6", "12", "33.5", "8.08", "33.5", "1.6", "0.628"));

        // Power crunch protein bar
        ef.add(new Food("Power Crunch", "000000", "375", "11.9", "0.0", "33.8", "0.0", "34.7", "0.0", "0"));

        calculateTotals();

        // Barchart information and initialisation
        HorizontalBarChart chart = (HorizontalBarChart) rootView.findViewById(R.id.today_chart);
        BarData chartData = new BarData(getXAxisValues(), getDataSet());
        chart.setData(chartData);
        chart.setDescription(" ");
        chart.animateXY(2000, 2000);
        chart.invalidate();

        // Set the fonts of the UI
        setTypeface();

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.today_fragment_title));

        getInformation();

        getInfoFromToday();

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
     *
     * @return ArrayList of data for the titles of the chart
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

    public void getInformation() {
        setUpGoalsTable();
    }

    public Goals getGoals() {
        goals = new Goals();
        ExampleGoals eg = new ExampleGoals();

        goals.setCalories(eg.getExample_goal_calories());
        goals.setCarbs(eg.getExample_goal_carbs());
        goals.setFat(eg.getExample_goal_fat());
        goals.setProtein(eg.getExample_goal_protein());
        goals.setSalt(eg.getExample_goal_salt());
        goals.setSat_fat(eg.getExample_goal_sat_fat());
        goals.setSugar(eg.getExample_goal_sugar());
        goals.setSodium(eg.getExample_goal_sodium());

        System.out.println(goals.toString());

        return goals;
    }

    public void setUpGoalsTable() {
        final Goals goals = getGoals();
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
        setColorOfSatFat(Float.parseFloat(goals.getSat_fat()), sat_fat_stats);
        setColorOfSalt(Float.parseFloat(goals.getSalt()), salt_stats);
        setColorOfSodium(Float.parseFloat(goals.getSodium()), sodium_stats);
        setColorOfCarbs(Float.parseFloat(goals.getCarbs()), carbs_stats);
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
                String message = getString(R.string.today_fragment_your_sat_fat_goal) + " " + goals.getSat_fat();
                if (Float.parseFloat(total_sat_fats) > Float.parseFloat(goals.getSat_fat())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_sat_fats) - Float.parseFloat(goals.getSat_fat()));
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
                String message = getString(R.string.today_fragment_your_carbs_goal) + " " + goals.getCarbs();
                if (Float.parseFloat(total_carbs) > Float.parseFloat(goals.getCarbs())) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(Float.parseFloat(total_carbs) - Float.parseFloat(goals.getCarbs()));
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

    public void calculateTotals() {
        float temp_calories = Float.parseFloat(total_calories);
        float temp_fat = Float.parseFloat(total_fats);
        float temp_sat_fat = Float.parseFloat(total_sat_fats);
        float temp_carbs = Float.parseFloat(total_carbs);
        float temp_sugar = Float.parseFloat(total_sugar);
        float temp_protein = Float.parseFloat(total_protein);
        float temp_salt = Float.parseFloat(total_salt);
        float temp_sodium = Float.parseFloat(total_sodium);

        for (int i = 0; i < ef.size(); i++) {
            temp_calories += Float.parseFloat(ef.get(i).getCalories());
            temp_fat += Float.parseFloat(ef.get(i).getFats());
            temp_sat_fat += Float.parseFloat(ef.get(i).getSaturated_fat());
            temp_carbs += Float.parseFloat(ef.get(i).getCarbs());
            temp_sugar += Float.parseFloat(ef.get(i).getSugar());
            temp_protein += Float.parseFloat(ef.get(i).getProtein());
            temp_salt += Float.parseFloat(ef.get(i).getSalt());
            temp_sodium += Float.parseFloat(ef.get(i).getSodium());
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

    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void makeAlert(String title, String message) {
        DialogFragment dialogFragment = MyAlertDialogFragment.newInstance(title, message);
        dialogFragment.show(getActivity().getFragmentManager(), "dialog");
    }

    ArrayList<Food> foods;

    public void getInfoFromToday() {
        foods = new ArrayList<>();

        Cursor res = db.returnTodaysEntries();
        if(res.getCount() == 0) {
            System.out.println("There is no information in todays entries");
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

    public void getInfoFromTodayStats() {
        Cursor res = db.returnTodayStatsEntries();
        System.out.println(res.getColumnCount());
        if(res.getCount() == 0) {
            System.out.println("There is no information in todays stat entries");
        } else {
            int i = 0;
            while(res.moveToNext()){
                if(res.getString(0).equals(foods.get(i).getId())){
                    foods.get(i).setCalories(res.getString(2));
                    foods.get(i).setFats(res.getString(3));
                    foods.get(i).setSaturated_fat(res.getString(4));
                    foods.get(i).setCarbs(res.getString(5));
                    foods.get(i).setSugar(res.getString(6));
                    foods.get(i).setProtein(res.getString(7));
                    foods.get(i).setSalt(res.getString(8));
                    foods.get(i).setSodium(res.getString(9));
                }
                System.out.println(foods.get(i).toString());
                i++;
            }
        }
    }

}
