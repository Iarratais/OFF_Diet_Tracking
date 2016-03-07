package com.karl.fragments;


import android.content.Context;
import android.content.res.Resources;
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
public class YearAnalysisFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "YearAnalysisFragment";

    private static final int HISTORY_THRESHOLD = 21;

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

    @Override
    public void onResume() {
        Yearly_Analysis ya = new Yearly_Analysis();
        ya.execute();
        super.onResume();
    }

    public String[] makeDaysArray(){
        return new String[]{getString(R.string.monday).substring(0, 3).toUpperCase(), getString(R.string.tuesday).substring(0, 3).toUpperCase(),
                getString(R.string.wednesday).substring(0, 3).toUpperCase(), getString(R.string.thursday).substring(0, 3).toUpperCase(),
                getString(R.string.friday).substring(0, 3).toUpperCase(), getString(R.string.saturday).substring(0, 3).toUpperCase(),
                getString(R.string.sunday).substring(0, 3).toUpperCase()};
    }

    private int entry_count = 0;

    public ArrayList<Day> getInformationFromDatabase(){
        MySQLiteHelper db = new MySQLiteHelper(getContext());

        ArrayList<Day> historydays = new ArrayList<>();

        Log.d(TAG, generateTimeQuery());
        Cursor res = db.getHistoryByDate(generateTimeQuery());

        // If there is less than three weeks worth of data, then there is nothing to show.
        if(res.getCount() < HISTORY_THRESHOLD) {
            return null;
        } else {
            while(res.moveToNext()) {
                // Column count = 10
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

    /**
     * Set the title of the section, for example, During 2016.
     */
    public void setAnalysisTitle(){
        TextView analysis_title = (TextView) rootView.findViewById(R.id.analysis_year_title);
        analysis_title.setText(getString(R.string.analysis_fragment_during_month_year, getYear()));
    }

    /**
     * Set the information into the cards and the typeface.
     * @param information String[]: information to fill into the layout.
     */
    public void setUpInformation(String[] information){
        Typeface normalTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/New_Cicle_Gordita.ttf");

        TextView calorie_analysis = (TextView) rootView.findViewById(R.id.calorie_analysis_year);
        calorie_analysis.setText(putTogetherString(getString(R.string.calories), information[0]));
        calorie_analysis.setTypeface(normalTypeface);
        calorie_analysis.setTextSize(18);

        TextView fat_analysis = (TextView) rootView.findViewById(R.id.fat_analysis_year);
        fat_analysis.setText(putTogetherString(getString(R.string.fats), information[1]));
        fat_analysis.setTypeface(normalTypeface);
        fat_analysis.setTextSize(18);

        TextView sat_fat_analysis = (TextView) rootView.findViewById(R.id.sat_fat_analysis_year);
        sat_fat_analysis.setText(putTogetherString(getString(R.string.saturated_fats), information[2]));
        sat_fat_analysis.setTypeface(normalTypeface);
        sat_fat_analysis.setTextSize(18);

        TextView salt_analysis = (TextView) rootView.findViewById(R.id.salt_analysis_year);
        salt_analysis.setText(putTogetherString(getString(R.string.salt), information[3]));
        salt_analysis.setTypeface(normalTypeface);
        salt_analysis.setTextSize(18);

        TextView sodium_analysis = (TextView) rootView.findViewById(R.id.sodium_analysis_year);
        sodium_analysis.setText(putTogetherString(getString(R.string.sodium), information[4]));
        sodium_analysis.setTypeface(normalTypeface);
        sodium_analysis.setTextSize(18);

        TextView carbohydrates_analysis = (TextView) rootView.findViewById(R.id.carbohydrates_analysis_year);
        carbohydrates_analysis.setText(putTogetherString(getString(R.string.carbohydrates), information[5]));
        carbohydrates_analysis.setTypeface(normalTypeface);
        carbohydrates_analysis.setTextSize(18);

        TextView sugar_analysis = (TextView) rootView.findViewById(R.id.sugar_analysis_year);
        sugar_analysis.setText(putTogetherString(getString(R.string.sugar), information[6]));
        sugar_analysis.setTypeface(normalTypeface);
        sugar_analysis.setTextSize(18);

        TextView protein_analysis = (TextView) rootView.findViewById(R.id.protein_analysis_year);
        protein_analysis.setText(putTogetherString(getString(R.string.protein), information[7]));
        protein_analysis.setTypeface(normalTypeface);
        protein_analysis.setTextSize(18);

        TextView analysis_subtext = (TextView) rootView.findViewById(R.id.analysis_subtext_year);
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

    /**
     * When there is no information, the application needs to show this.
     */
    public void nothingToShow(){
        ScrollView sv = (ScrollView) rootView.findViewById(R.id.year_scroll_view);
        hideView(sv);

        ImageView nothing_to_show = (ImageView) rootView.findViewById(R.id.nothing_to_show_year_analysis);
        showView(nothing_to_show);

        TextView nothing_to_show_text = (TextView) rootView.findViewById(R.id.nothing_to_show_text_year_analysis);
        showView(nothing_to_show_text);
    }

    /**
     * Hide a view.
     * @param view View: to be hidden
     */
    public void hideView(View view){
        view.setVisibility(View.GONE);
    }

    public void showView(View view){
        view.setVisibility(View.VISIBLE);
    }

    class Yearly_Analysis extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {

            ArrayList<Day> history = getInformationFromDatabase();

            if(history != null){
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
            }
        }
    }
}
