package com.karl.fragments;


import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.karl.analysis.Analyse;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Day;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthAnalysisFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "MonthAnalysisFragment";

    private static final int HISTORY_THRESHOLD = 4;

    Analyse analyse;

    View rootView;

    public MonthAnalysisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_month_analysis, container, false);

        Monthly_Analysis ma = new Monthly_Analysis();
        ma.execute();

        return rootView;
    }

    @Override
    public void onResume() {
        Monthly_Analysis ma = new Monthly_Analysis();
        ma.execute();
        super.onResume();
    }

    public String[] makeDaysArray(){
        return new String[]{getString(R.string.mondays).substring(0, 3).toUpperCase(), getString(R.string.tuesdays).substring(0, 3).toUpperCase(),
                getString(R.string.wednesdays).substring(0, 3).toUpperCase(), getString(R.string.thursdays).substring(0, 3).toUpperCase(),
                getString(R.string.fridays).substring(0, 3).toUpperCase(), getString(R.string.saturdays).substring(0, 3).toUpperCase(),
                getString(R.string.sundays).substring(0, 3).toUpperCase()};
    }

    int entry_count = 0;

    public ArrayList<Day> getInformationFromDatabase(){
        MySQLiteHelper db = new MySQLiteHelper(getContext());

        ArrayList<Day> historydays = new ArrayList<>();

        Cursor res = db.getHistoryByDate(generateTimeQuery());

        if(res.getCount() < HISTORY_THRESHOLD) {
            return null;
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

        entry_count = res.getCount();

        return historydays;
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

    public void setAnalysisTitle(){
        TextView analysis_title = (TextView) rootView.findViewById(R.id.analysis_month_title);
        analysis_title.setText(getString(R.string.analysis_fragment_during_month_year, getPreviousMonthTitle()));
    }

    /**
     * Set the information into the cards and the typeface.
     * @param information String[]: information to fill into the layout.
     */
    public void setUpInformation(String[] information){
        Typeface normalTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/New_Cicle_Gordita.ttf");

        TextView calorie_analysis = (TextView) rootView.findViewById(R.id.calorie_analysis);
        calorie_analysis.setText(putTogetherString(getString(R.string.calories), information[0]));
        calorie_analysis.setTypeface(normalTypeface);
        calorie_analysis.setTextSize(18);

        TextView fat_analysis = (TextView) rootView.findViewById(R.id.fat_analysis);
        fat_analysis.setText(putTogetherString(getString(R.string.fats), information[1]));
        fat_analysis.setTypeface(normalTypeface);
        fat_analysis.setTextSize(18);

        TextView sat_fat_analysis = (TextView) rootView.findViewById(R.id.sat_fat_analysis);
        sat_fat_analysis.setText(putTogetherString(getString(R.string.saturated_fats), information[2]));
        sat_fat_analysis.setTypeface(normalTypeface);
        sat_fat_analysis.setTextSize(18);

        TextView salt_analysis = (TextView) rootView.findViewById(R.id.salt_analysis);
        salt_analysis.setText(putTogetherString(getString(R.string.salt), information[3]));
        salt_analysis.setTypeface(normalTypeface);
        salt_analysis.setTextSize(18);

        TextView sodium_analysis = (TextView) rootView.findViewById(R.id.sodium_analysis);
        sodium_analysis.setText(putTogetherString(getString(R.string.sodium), information[4]));
        sodium_analysis.setTypeface(normalTypeface);
        sodium_analysis.setTextSize(18);

        TextView carbohydrates_analysis = (TextView) rootView.findViewById(R.id.carbohydrates_analysis);
        carbohydrates_analysis.setText(putTogetherString(getString(R.string.carbohydrates), information[5]));
        carbohydrates_analysis.setTypeface(normalTypeface);
        carbohydrates_analysis.setTextSize(18);

        TextView sugar_analysis = (TextView) rootView.findViewById(R.id.sugar_analysis);
        sugar_analysis.setText(putTogetherString(getString(R.string.sugar), information[6]));
        sugar_analysis.setTypeface(normalTypeface);
        sugar_analysis.setTextSize(18);

        TextView protein_analysis = (TextView) rootView.findViewById(R.id.protein_analysis);
        protein_analysis.setText(putTogetherString(getString(R.string.protein), information[7]));
        protein_analysis.setTypeface(normalTypeface);
        protein_analysis.setTextSize(18);

        TextView analysis_subtext = (TextView) rootView.findViewById(R.id.analysis_subtext);
        if(entry_count < 10){
            analysis_subtext.setText(getString(R.string.analysis_fragment_based_on, entry_count) + ". " + getString(R.string.analysis_fragment_low_entries_warning));
        } else {
            analysis_subtext.setText(getString(R.string.analysis_fragment_based_on, entry_count));
        }
    }

    /**
     * Put together the string to put in the component.
     * @param one the nutrient.
     * @param two the day.
     * @return nutrient and day.
     */
    public String putTogetherString(String one, String two){
        return getString(R.string.analysis_fragment_you_ate_the_most_on, one, two);
    }

    public void nothingToShow(){
        ScrollView sv = (ScrollView) rootView.findViewById(R.id.month_scroll_view);
        hideView(sv);

        ImageView nothing_to_show = (ImageView) rootView.findViewById(R.id.nothing_to_show_month_analysis);
        showView(nothing_to_show);

        TextView nothing_to_show_text = (TextView) rootView.findViewById(R.id.nothing_to_show_text_month_analysis);
        showView(nothing_to_show_text);
    }

    public void hideView(View view){
        view.setVisibility(View.GONE);
    }

    public void showView(View view){
        view.setVisibility(View.VISIBLE);
    }

    class Monthly_Analysis extends AsyncTask<Void, Void, String[]> {


        @Override
        protected String[] doInBackground(Void... params) {

            ArrayList<Day> history = getInformationFromDatabase();

            if(history != null) {
                analyse = new Analyse(getContext(), history);
            } else {
                return null;
            }

            return new String[] {analyse.getDayMostCalories(), analyse.getDayMostFats(), analyse.getDayMostSatFat(), analyse.getDayMostSalt(), analyse.getDayMostSodium(),
                    analyse.getDayMostCarbohydrates(), analyse.getDayMostSugar(), analyse.getDayMostProtein()};
        }

        @Override
        protected void onPostExecute(String[] days) {
            if(days != null) {
                setAnalysisTitle();

                setUpInformation(days);
                super.onPostExecute(days);
            } else{
                nothingToShow();
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
