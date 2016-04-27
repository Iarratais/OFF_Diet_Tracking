package com.karl.fragments;


import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.karl.analysis.Analyse;
import com.karl.analysis.GoalAnalysis;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Day;
import com.karl.models.Goals;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Copyright Karl jones 2016.
 *
 *
 */

public class MonthAnalysisFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "MonthAnalysisFragment";

    private static final int HISTORY_THRESHOLD = 4;

    GoalAnalysis ga;

    View rootView;

    public MonthAnalysisFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_month_analysis, container, false);

        Monthly_Analysis_Task ma = new Monthly_Analysis_Task();
        ma.execute();

        ArrayList<Day> historyFromDatabase = getInformationFromDatabase();
        if(historyFromDatabase != null) {
            ga = new GoalAnalysis(historyFromDatabase, getContext());
            HorizontalBarChart chart = (HorizontalBarChart) rootView.findViewById(R.id.month_analysis_chart);
            BarData chartData = new BarData(getXAxisValues(), getDataSet());
            chart.setData(chartData);
            chart.animateXY(2000, 2000);
            chart.setDescription(" ");
            chart.invalidate();

            inputGoals();
            inputGoalsOver();
        } else {
            LinearLayout graphLayout = (LinearLayout) rootView.findViewById(R.id.graph_layout);
            graphLayout.setVisibility(View.GONE);
            hideGoalsIfNone();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        Monthly_Analysis_Task ma = new Monthly_Analysis_Task();
        ma.execute();

        ArrayList<Day> historyFromDatabase = getInformationFromDatabase();
        if(historyFromDatabase != null) {
            ga = new GoalAnalysis(historyFromDatabase, getContext());
            HorizontalBarChart chart = (HorizontalBarChart) rootView.findViewById(R.id.month_analysis_chart);
            BarData chartData = new BarData(getXAxisValues(), getDataSet());
            chart.setData(chartData);
            chart.animateXY(2000, 2000);
            chart.setDescription(" ");
            chart.invalidate();
        } else {
            LinearLayout graphLayout = (LinearLayout) rootView.findViewById(R.id.graph_layout);
            graphLayout.setVisibility(View.GONE);
        }
        super.onResume();
    }

    /**
     * Retrieve the goals from the database.
     * @return Goals    the users goals.
     */
    public Goals getGoals(){
        MySQLiteHelper db = new MySQLiteHelper(getContext());
        Goals resGoals = new Goals();
        Cursor res = db.getGoals();
        if(res.getCount() != 0){
            while(res.moveToNext()){
                for(int i = 0; i < res.getColumnCount(); i++){
                    resGoals.setCalories(res.getString(1));
                    resGoals.setFat(res.getString(2));
                    resGoals.setSaturatedFat(res.getString(3));
                    resGoals.setSalt(res.getString(4));
                    resGoals.setSodium(res.getString(5));
                    resGoals.setCarbohydrates(res.getString(6));
                    resGoals.setSugar(res.getString(7));
                    resGoals.setProtein(res.getString(8));
                }
            }
        }
        return resGoals;
    }

    int historyDatabaseEntries = 0;

    public ArrayList<Day> getInformationFromDatabase(){
        MySQLiteHelper db = new MySQLiteHelper(getContext());

        ArrayList<Day> historyInformationFromDatabase = new ArrayList<>();

        Cursor HistoryFromDatabase = db.getHistoryByDate(generateTimeQuery());

        if(HistoryFromDatabase.getCount() < HISTORY_THRESHOLD) {
            return null;
        } else {
            while(HistoryFromDatabase.moveToNext()) {
                // history_id date calories fats sat_fat salt sodium carbohydrates sugar protein
                Day day = new Day();
                day.setId(HistoryFromDatabase.getString(0));
                day.setDate(HistoryFromDatabase.getString(1));
                day.setCalories(HistoryFromDatabase.getString(2));
                day.setFats(HistoryFromDatabase.getString(3));
                day.setSaturated_fat(HistoryFromDatabase.getString(4));
                day.setSalt(HistoryFromDatabase.getString(5));
                day.setSodium(HistoryFromDatabase.getString(6));
                day.setCarbs(HistoryFromDatabase.getString(7));
                day.setSugar(HistoryFromDatabase.getString(8));
                day.setProtein(HistoryFromDatabase.getString(9));

                historyInformationFromDatabase.add(day);
            }
        }
        historyDatabaseEntries = HistoryFromDatabase.getCount();
        return historyInformationFromDatabase;
    }

    /**
     * This gets the month and the year that needs to be queried from
     * the database.
     * @return String: time query to get the information.
     */
    public String generateTimeQuery(){
        return getPreviousMonth() + getCurrentYear();
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
    public String getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);

        return String.valueOf(thisYear);
    }

    public void setPageTitle(){
        TextView analysis_title = (TextView) rootView.findViewById(R.id.analysis_month_title);
        analysis_title.setText(getString(R.string.analysis_fragment_during_month_year, getPreviousMonthTitle()));
    }

    /**
     * Set the information into the cards and the typeface.
     * @param information String[]: information to fill into the layout.
     */
    public void inputInformationIntoViews(String[] information){
        Typeface normalTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/New_Cicle_Gordita.ttf");

        TextView calorie_analysis = (TextView) rootView.findViewById(R.id.calorie_analysis);
        calorie_analysis.setText(constructStringForDisplay(getString(R.string.calories), information[0]));
        calorie_analysis.setTypeface(normalTypeface);
        calorie_analysis.setTextSize(18);

        TextView fat_analysis = (TextView) rootView.findViewById(R.id.fat_analysis);
        fat_analysis.setText(constructStringForDisplay(getString(R.string.fats), information[1]));
        fat_analysis.setTypeface(normalTypeface);
        fat_analysis.setTextSize(18);

        TextView sat_fat_analysis = (TextView) rootView.findViewById(R.id.sat_fat_analysis);
        sat_fat_analysis.setText(constructStringForDisplay(getString(R.string.saturated_fats), information[2]));
        sat_fat_analysis.setTypeface(normalTypeface);
        sat_fat_analysis.setTextSize(18);

        TextView salt_analysis = (TextView) rootView.findViewById(R.id.salt_analysis);
        salt_analysis.setText(constructStringForDisplay(getString(R.string.salt), information[3]));
        salt_analysis.setTypeface(normalTypeface);
        salt_analysis.setTextSize(18);

        TextView sodium_analysis = (TextView) rootView.findViewById(R.id.sodium_analysis);
        sodium_analysis.setText(constructStringForDisplay(getString(R.string.sodium), information[4]));
        sodium_analysis.setTypeface(normalTypeface);
        sodium_analysis.setTextSize(18);

        TextView carbohydrates_analysis = (TextView) rootView.findViewById(R.id.carbohydrates_analysis);
        carbohydrates_analysis.setText(constructStringForDisplay(getString(R.string.carbohydrates), information[5]));
        carbohydrates_analysis.setTypeface(normalTypeface);
        carbohydrates_analysis.setTextSize(18);

        TextView sugar_analysis = (TextView) rootView.findViewById(R.id.sugar_analysis);
        sugar_analysis.setText(constructStringForDisplay(getString(R.string.sugar), information[6]));
        sugar_analysis.setTypeface(normalTypeface);
        sugar_analysis.setTextSize(18);

        TextView protein_analysis = (TextView) rootView.findViewById(R.id.protein_analysis);
        protein_analysis.setText(constructStringForDisplay(getString(R.string.protein), information[7]));
        protein_analysis.setTypeface(normalTypeface);
        protein_analysis.setTextSize(18);

        TextView analysis_subtext = (TextView) rootView.findViewById(R.id.analysis_subtext);
        // If the entry count is less than ten, inform the user that this information is not
        // based off much information that is put in.
        if(historyDatabaseEntries < 10){
            analysis_subtext.setText(getString(R.string.analysis_fragment_based_on, historyDatabaseEntries) + ". " + getString(R.string.analysis_fragment_low_entries_warning));
        } else {
            analysis_subtext.setText(getString(R.string.analysis_fragment_based_on, historyDatabaseEntries));
        }
    }

    /**
     * This hides the personal goals textviews.
     */
    public void hideGoalsIfNone(){
        TextView caloriesGoals = (TextView) rootView.findViewById(R.id.calorie_analysis_personal_goal);
        caloriesGoals.setVisibility(View.GONE);

        TextView fatGoals = (TextView) rootView.findViewById(R.id.fat_analysis_personal_goal);
        fatGoals.setVisibility(View.GONE);

        TextView satFatGoals = (TextView) rootView.findViewById(R.id.sat_fat_analysis_personal_goal);
        satFatGoals.setVisibility(View.GONE);

        TextView saltGoals = (TextView) rootView.findViewById(R.id.salt_analysis_personal_goal);
        saltGoals.setVisibility(View.GONE);

        TextView sodiumGoals = (TextView) rootView.findViewById(R.id.sodium_analysis_personal_goal);
        sodiumGoals.setVisibility(View.GONE);

        TextView carbohydratesGoals = (TextView) rootView.findViewById(R.id.carbohydrate_analysis_personal_goal);
        carbohydratesGoals.setVisibility(View.GONE);

        TextView sugarGoals = (TextView) rootView.findViewById(R.id.sugar_analysis_personal_goal);
        sugarGoals.setVisibility(View.GONE);

        TextView proteinGoals = (TextView) rootView.findViewById(R.id.protein_analysis_personal_goal);
        proteinGoals.setVisibility(View.GONE);
    }

    /**
     * This fills out the goals data into the interface.
     */
    public void inputGoals(){
        Goals goals = getGoals();

        Typeface normalTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/New_Cicle_Gordita.ttf");

        TextView caloriesGoals = (TextView) rootView.findViewById(R.id.calorie_analysis_personal_goal);
        caloriesGoals.setText(getString(R.string.analysis_fragment_personal_goal, goals.getCalories()));
        caloriesGoals.setTypeface(normalTypeface);
        caloriesGoals.setTextSize(18);

        TextView fatGoals = (TextView) rootView.findViewById(R.id.fat_analysis_personal_goal);
        fatGoals.setText(getString(R.string.analysis_fragment_personal_goal, goals.getFat()) + getString(R.string.grams_abbv));
        fatGoals.setTypeface(normalTypeface);
        fatGoals.setTextSize(18);

        TextView satFatGoals = (TextView) rootView.findViewById(R.id.sat_fat_analysis_personal_goal);
        satFatGoals.setText(getString(R.string.analysis_fragment_personal_goal, goals.getSaturatedFat()) + getString(R.string.grams_abbv));
        satFatGoals.setTypeface(normalTypeface);
        satFatGoals.setTextSize(18);

        TextView saltGoals = (TextView) rootView.findViewById(R.id.salt_analysis_personal_goal);
        saltGoals.setText(getString(R.string.analysis_fragment_personal_goal, goals.getSalt()) + getString(R.string.grams_abbv));
        saltGoals.setTypeface(normalTypeface);
        saltGoals.setTextSize(18);

        TextView sodiumGoals = (TextView) rootView.findViewById(R.id.sodium_analysis_personal_goal);
        sodiumGoals.setText(getString(R.string.analysis_fragment_personal_goal, goals.getSodium()) + getString(R.string.grams_abbv));
        sodiumGoals.setTypeface(normalTypeface);
        sodiumGoals.setTextSize(18);

        TextView carbohydratesGoals = (TextView) rootView.findViewById(R.id.carbohydrate_analysis_personal_goal);
        carbohydratesGoals.setText(getString(R.string.analysis_fragment_personal_goal, goals.getCarbohydrates()) + getString(R.string.grams_abbv));
        carbohydratesGoals.setTypeface(normalTypeface);
        carbohydratesGoals.setTextSize(18);

        TextView sugarGoals = (TextView) rootView.findViewById(R.id.sugar_analysis_personal_goal);
        sugarGoals.setText(getString(R.string.analysis_fragment_personal_goal, goals.getSugar()) + getString(R.string.grams_abbv));
        sugarGoals.setTypeface(normalTypeface);
        sugarGoals.setTextSize(18);

        TextView proteinGoals = (TextView) rootView.findViewById(R.id.protein_analysis_personal_goal);
        proteinGoals.setText(getString(R.string.analysis_fragment_personal_goal, goals.getProtein()) + getString(R.string.grams_abbv));
        proteinGoals.setTypeface(normalTypeface);
        proteinGoals.setTextSize(18);
    }

    /**
     * Hide the textviews for goals broken if none.
     */
    public void hideGoneOverIfNone(){
        TextView caloriesGone = (TextView) rootView.findViewById(R.id.calorie_analysis_times_gone_over);
        caloriesGone.setVisibility(View.GONE);

        TextView fatGone = (TextView) rootView.findViewById(R.id.fat_analysis_gone_over);
        fatGone.setVisibility(View.GONE);

        TextView satFatGone = (TextView) rootView.findViewById(R.id.sat_fat_analysis_gone_over);
        satFatGone.setVisibility(View.GONE);

        TextView saltGone = (TextView) rootView.findViewById(R.id.salt_analysis_gone_over);
        saltGone.setVisibility(View.GONE);

        TextView sodiumGone = (TextView) rootView.findViewById(R.id.sodium_analysis_gone_over);
        sodiumGone.setVisibility(View.GONE);

        TextView carbohydrateGone = (TextView) rootView.findViewById(R.id.carbohydrate_analysis_gone_over);
        carbohydrateGone.setVisibility(View.GONE);

        TextView sugarGone = (TextView) rootView.findViewById(R.id.sugar_analysis_gone_over);
        sugarGone.setVisibility(View.GONE);

        TextView proteinGone = (TextView) rootView.findViewById(R.id.protein_analysis_gone_over);
        proteinGone.setVisibility(View.GONE);
    }

    /**
     * This uses the information from the GoalsAnalysis activity to fill in information about how many times the user went
     * over their goals.
     */
    public void inputGoalsOver(){
        Typeface normalTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/New_Cicle_Gordita.ttf");

        TextView caloriesGone = (TextView) rootView.findViewById(R.id.calorie_analysis_times_gone_over);
        caloriesGone.setText(getString(R.string.analysis_fragment_times_gone_over_goals, ga.getCalories_broken()));
        caloriesGone.setTypeface(normalTypeface);
        caloriesGone.setTextSize(18);

        TextView fatGone = (TextView) rootView.findViewById(R.id.fat_analysis_gone_over);
        fatGone.setText(getString(R.string.analysis_fragment_times_gone_over_goals, ga.getFats_broken()));
        fatGone.setTypeface(normalTypeface);
        fatGone.setTextSize(18);

        TextView satFatGone = (TextView) rootView.findViewById(R.id.sat_fat_analysis_gone_over);
        satFatGone.setText(getString(R.string.analysis_fragment_times_gone_over_goals, ga.getSaturated_fats_broken()));
        satFatGone.setTypeface(normalTypeface);
        satFatGone.setTextSize(18);

        TextView saltGone = (TextView) rootView.findViewById(R.id.salt_analysis_gone_over);
        saltGone.setText(getString(R.string.analysis_fragment_times_gone_over_goals, ga.getSalt_broken()));
        saltGone.setTypeface(normalTypeface);
        saltGone.setTextSize(18);

        TextView sodiumGone = (TextView) rootView.findViewById(R.id.sodium_analysis_gone_over);
        sodiumGone.setText(getString(R.string.analysis_fragment_times_gone_over_goals, ga.getSodium_broken()));
        sodiumGone.setTypeface(normalTypeface);
        sodiumGone.setTextSize(18);

        TextView carbohydrateGone = (TextView) rootView.findViewById(R.id.carbohydrate_analysis_gone_over);
        carbohydrateGone.setText(getString(R.string.analysis_fragment_times_gone_over_goals, ga.getCarbohydrates_broken()));
        carbohydrateGone.setTypeface(normalTypeface);
        carbohydrateGone.setTextSize(18);

        TextView sugarGone = (TextView) rootView.findViewById(R.id.sugar_analysis_gone_over);
        sugarGone.setText(getString(R.string.analysis_fragment_times_gone_over_goals, ga.getSugar_broken()));
        sugarGone.setTypeface(normalTypeface);
        sugarGone.setTextSize(18);

        TextView proteinGone = (TextView) rootView.findViewById(R.id.protein_analysis_gone_over);
        proteinGone.setText(getString(R.string.analysis_fragment_times_gone_over_goals, ga.getProtein_broken()));
        proteinGone.setTypeface(normalTypeface);
        proteinGone.setTextSize(18);
    }

    /**
     * Put together the string to put in the component.
     * @param one the nutrient.
     * @param two the day.
     * @return nutrient and day.
     */
    public String constructStringForDisplay(String one, String two){
        return getString(R.string.analysis_fragment_you_ate_the_most_on, one, two);
    }

    public void noInformationInDatabaseToShow(){
        ScrollView sv = (ScrollView) rootView.findViewById(R.id.month_scroll_view);
        hideView(sv);

        ImageView nothingToShowImageView = (ImageView) rootView.findViewById(R.id.nothing_to_show_month_analysis);
        showView(nothingToShowImageView);

        TextView nothingToShowTextView = (TextView) rootView.findViewById(R.id.nothing_to_show_text_month_analysis);
        showView(nothingToShowTextView);
    }

    public void hideView(View view){
        view.setVisibility(View.GONE);
    }

    public void showView(View view){
        view.setVisibility(View.VISIBLE);
    }

    /**
     *
     * @return ArrayList of data for the chart
    */
    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = new ArrayList<>();

        // Add the data to the ArrayList
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry value1 = new BarEntry(ga.getSodium_broken(), 0); // Sodium
        valueSet1.add(value1);
        BarEntry value2 = new BarEntry(ga.getSalt_broken(), 1); // Salt
        valueSet1.add(value2);
        BarEntry value3 = new BarEntry(ga.getProtein_broken(), 2); // Proteins
        valueSet1.add(value3);
        BarEntry value4 = new BarEntry(ga.getSugar_broken(), 3); // Sugar
        valueSet1.add(value4);
        BarEntry value5 = new BarEntry(ga.getCarbohydrates_broken(), 4); // Carbohydrates
        valueSet1.add(value5);
        BarEntry value6 = new BarEntry(ga.getSaturated_fats_broken(), 5); // Saturated Fat
        valueSet1.add(value6);
        BarEntry value7 = new BarEntry(ga.getFats_broken(), 6); // Fats
        valueSet1.add(value7);
        BarEntry value8 = new BarEntry(ga.getCalories_broken(), 7); // calories
        valueSet1.add(value8);

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
        xAxis.add(getString(R.string.calories));

        return xAxis;
    }

    class Monthly_Analysis_Task extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {

            ArrayList<Day> historyFromDatabase = getInformationFromDatabase();

            Analyse analyse;

            if(historyFromDatabase != null) {
                analyse = new Analyse(getContext(), historyFromDatabase);
            } else {
                return null;
            }

            if(analyse != null) {
                return new String[]{analyse.getDayMostCalories(), analyse.getDayMostFats(), analyse.getDayMostSatFat(), analyse.getDayMostSalt(), analyse.getDayMostSodium(),
                        analyse.getDayMostCarbohydrates(), analyse.getDayMostSugar(), analyse.getDayMostProtein()};
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] days) {
            if(days != null) {
                setPageTitle();
                inputInformationIntoViews(days);
                super.onPostExecute(days);
            } else{
                noInformationInDatabaseToShow();
                Log.d(TAG, "Nothing to show on post execute");
            }
        }
    }

    /**
     * Get the previous month.
     * @return String : previous month.
     */
    public String getPreviousMonthTitle(){
        Calendar calendar = Calendar.getInstance();
        int thisMonth = calendar.get(Calendar.MONTH);

        // We want the previous month.
        thisMonth -= 1;

        if(thisMonth < 0) {
            thisMonth = 11;
        }

        // Give back the month as a full word.
        switch(thisMonth) {
            case 0:
                return getString(R.string.january);
            case 1:
                return getString(R.string.february);
            case 2:
                return getString(R.string.march);
            case 3:
                return getString(R.string.april);
            case 4:
                return getString(R.string.may);
            case 5:
                return getString(R.string.june);
            case 6:
                return getString(R.string.july);
            case 7:
                return getString(R.string.august);
            case 8:
                return getString(R.string.september);
            case 9:
                return getString(R.string.october);
            case 10:
                return getString(R.string.november);
            case 11:
                return getString(R.string.december);
            default:
                return getString(R.string.last_month);
        }
    }
}
