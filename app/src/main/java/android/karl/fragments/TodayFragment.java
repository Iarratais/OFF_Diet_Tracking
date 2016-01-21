package android.karl.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.karl.fyp.Food;
import android.karl.examples.ExampleGoals;
import android.karl.fyp.Goals;
import android.karl.fyp.MainActivity;
import android.karl.fyp.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
public class TodayFragment extends Fragment {

    View rootView;
    ArrayList<Food> ef;
    Goals goals;

    float total_calories = 0;
    float total_fats = 0;
    float total_sat_fats = 0;
    float total_carbs = 0;
    float total_sugar = 0;
    float total_protein = 0;
    float total_salt = 0;
    float total_sodium = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.content_fragment_today, container, false);

        ef = new ArrayList<>();

        // Dairygold
        ef.add(new Food("Dairygold", 57f, 6.3f, 2.3f, 0f, 0.1f, 0f, 0.199f, 0.0787f));

        // Nutella
        ef.add(new Food("Nutella", 83f, 4.75f, 1.64f, 8.59f, 8.51f, 0.9f, 0.0141f, 0.00555f));

        // Royal Bacon - MCDonalds
        ef.add(new Food("Royal Bacon", 504f, 25.6f, 12f, 33.5f, 8.08f, 33.5f, 1.6f, 0.628f));

        // Power crunch protein bar
        ef.add(new Food("Power Crunch", 375f, 11.9f, 0f, 33.8f, 0f, 34.7f, 0f, 0f));

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

        return rootView;
    }

    // Add the data into the bar chart
    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = new ArrayList<>();

        // Add the data to the ArrayList
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry value1 = new BarEntry(total_sodium, 0); // Sodium
        valueSet1.add(value1);
        BarEntry value2 = new BarEntry(total_salt, 1); // Salt
        valueSet1.add(value2);
        BarEntry value3 = new BarEntry(total_protein, 2); // Proteins
        valueSet1.add(value3);
        BarEntry value4 = new BarEntry(total_sugar, 3); // Sugar
        valueSet1.add(value4);
        BarEntry value5 = new BarEntry(total_carbs, 4); // Carbohydrates
        valueSet1.add(value5);
        BarEntry value6 = new BarEntry(total_sat_fats, 5); // Saturated Fat
        valueSet1.add(value6);
        BarEntry value7 = new BarEntry(total_fats, 6); // Fats
        valueSet1.add(value7);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Foods");
        barDataSet1.setColors(ColorTemplate.PASTEL_COLORS);

        dataSets.add(barDataSet1);

        return dataSets;
    }

    // Set the values for the titles
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

        final String calories = df.format(total_calories);
        final String fats = df.format(total_fats) + getString(R.string.grams_abbv);
        final String sat_fats = df.format(total_sat_fats) + getString(R.string.grams_abbv);
        final String salts = df.format(total_salt) + getString(R.string.grams_abbv);
        final String sodiums = df.format(total_sodium) + getString(R.string.grams_abbv);
        final String carbs = df.format(total_carbs) + getString(R.string.grams_abbv);
        final String sugars = df.format(total_sugar) + getString(R.string.grams_abbv);
        final String proteins = df.format(total_protein) + getString(R.string.grams_abbv);

        calories_stats.setText(calories);
        fat_stats.setText(fats);
        sat_fat_stats.setText(sat_fats);
        salt_stats.setText(salts);
        sodium_stats.setText(sodiums);
        carbs_stats.setText(carbs);
        sugar_stats.setText(sugars);
        protein_stats.setText(proteins);

        setColorOfCalories(goals.getCalories(), calories_stats);
        setColorOfFat(goals.getFat(), fat_stats);
        setColorOfSatFat(goals.getSat_fat(), sat_fat_stats);
        setColorOfSalt(goals.getSalt(), salt_stats);
        setColorOfSodium(goals.getSodium(), sodium_stats);
        setColorOfCarbs(goals.getCarbs(), carbs_stats);
        setColorOfSugar(goals.getSugar(), sugar_stats);
        setColorofProteins(goals.getProtein(), protein_stats);

        calories_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_calorie_goal) + " " + goals.getCalories();
                if (total_calories > goals.getCalories()) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(total_calories - goals.getCalories());
                }
                makeToast(message);
            }
        });
        fat_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_fat_goal) + " " + goals.getFat();
                if (total_fats > goals.getFat()) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(total_fats - goals.getFat());
                }
                makeToast(message);
            }
        });
        sat_fat_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_sat_fat_goal) + " " + goals.getSat_fat();
                if (total_sat_fats > goals.getSat_fat()) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(total_sat_fats - goals.getSat_fat());
                }
                makeToast(message);
            }
        });
        salt_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_salt_goal) + " " + goals.getSalt();
                if (total_salt > goals.getSalt()) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(total_salt - goals.getSalt());
                }
                makeToast(message);
            }
        });
        sodium_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_sodium_goal) + " " + goals.getSodium();
                if (total_sodium > goals.getSodium()) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(total_sodium - goals.getSodium());
                }
                makeToast(message);
            }
        });
        carbs_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_carbs_goal) + " " + goals.getCarbs();
                if (total_carbs > goals.getCarbs()) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(total_carbs - goals.getCarbs());
                }
                makeToast(message);
            }
        });
        sugar_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_sugar_goal) + " " + goals.getSugar();
                if (total_sugar > goals.getSugar()) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(total_sugar - goals.getSugar());
                }
                makeToast(message);
            }
        });
        protein_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.today_fragment_your_protein_goal) + " " + goals.getProtein();
                if (total_protein > goals.getProtein()) {
                    message += "\n" + getString(R.string.today_fragment_you_have_exceeded_your_goal) + " " + df.format(total_protein - goals.getProtein());
                }
                makeToast(message);
            }
        });
    }

    public void setColorOfCalories(float calories, TextView text) {
        if(calories < total_calories) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorOfFat(float fat, TextView text) {
        if(fat < total_fats) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorOfSatFat(float satFat, TextView text) {
        if(satFat < total_sat_fats) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorOfSalt(float salt, TextView text) {
        if(salt < total_salt) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorOfSodium(float sodium, TextView text) {
        if(sodium < total_sodium) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorOfCarbs(float carb, TextView text) {
        if(carb < total_carbs) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorOfSugar(float sugar, TextView text) {
        if(sugar < total_sugar) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void setColorofProteins(float protein, TextView text) {
        if(protein < total_protein) {
            text.setTextColor(getResources().getColor(R.color.nutrientTooHigh));
        }
    }

    public void calculateTotals() {
        for (int i = 0; i < ef.size(); i++) {
            total_calories += ef.get(i).getCalories();
            total_fats += ef.get(i).getFats();
            total_sat_fats += ef.get(i).getSat_fats();
            total_carbs += ef.get(i).getCarbs();
            total_sugar += ef.get(i).getSugar();
            total_protein += ef.get(i).getProtein();
            total_salt += ef.get(i).getSalt();
            total_sodium += ef.get(i).getSodium();
        }
    }

    public void makeToast(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
