package com.karl.fragments;

import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.karl.adapters.DiaryListAdapter;
import com.karl.alerts.MyAlertDialogFragment;
import com.karl.fyp.MainActivity;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Food;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Copyright Karl jones 2016.
 *
 * The diary fragment gets information from the database in relation to the food that the user
 * logged for the current day.
 */

public class DiaryFragment extends android.support.v4.app.Fragment {

    View rootView;

    private static final String TAG = "DiaryFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_diary, container, false);

        ((MainActivity) getActivity()).setActionBarTitle(setDateTitle());

        makeList();

        return rootView;
    }

    /**
     * Create a dialog alert for the user.
     * @param title of the alert
     * @param message to be contained in the alert
     */
    public void makeAlert(String title, String message) {
        DialogFragment dialogFragment = MyAlertDialogFragment.newInstance(title, message);
        dialogFragment.show(getActivity().getFragmentManager(), "dialog");
    }

    /**
     * Create the list to display the information to the user.
     */
    public void makeList() {
        ListView list = (ListView) rootView.findViewById(R.id.today_chart_information);
        ArrayList<Food> listItems;

        listItems = getInformation();

        DiaryListAdapter adapter = new DiaryListAdapter(getActivity(), listItems);
        list.setAdapter(adapter);
    }

    /**
     * Get the information from the database. If there is no information, display the message to
     * the user informing them that there is no information in the database.
     * @return ArrayList<Food> containing food items for the day.
     */
    public ArrayList<Food> getInformation() {
        MySQLiteHelper db = new MySQLiteHelper(getContext());

        Cursor todaysEntriesFromDatabase = db.returnAllTodayEntries();
        Cursor todaysStatEntriesFromDatabase = db.returnAllTodaysStats();

        ArrayList<Food> foodsFromDatabase = new ArrayList<>();

        if(todaysEntriesFromDatabase == null || todaysStatEntriesFromDatabase == null || todaysEntriesFromDatabase.getCount() == 0) {
            ListView list = (ListView) rootView.findViewById(R.id.today_chart_information);
            list.setVisibility(View.GONE);

            ImageView nothing_to_show = (ImageView) rootView.findViewById(R.id.nothing_to_show_diary);
            nothing_to_show.setVisibility(View.VISIBLE);
            TextView nothing_to_show_txt = (TextView) rootView.findViewById(R.id.nothing_to_show_text_diary);
            nothing_to_show_txt.setVisibility(View.VISIBLE);
        } else {
            while(todaysEntriesFromDatabase.moveToNext()) {
                Food f = new Food();
                f.setId(String.valueOf(todaysEntriesFromDatabase.getInt(0)));
                f.setDate(todaysEntriesFromDatabase.getString(1));
                f.setName(todaysEntriesFromDatabase.getString(2));
                f.setBarcode_number(todaysEntriesFromDatabase.getString(3));
                foodsFromDatabase.add(f);
            } // End while(todaysEntriesFromDatabase.moveToNext())

            int i = 0;
            while(todaysStatEntriesFromDatabase.moveToNext()){
                foodsFromDatabase.get(i).setCalories(todaysStatEntriesFromDatabase.getString(2));
                foodsFromDatabase.get(i).setFats(todaysStatEntriesFromDatabase.getString(3));
                foodsFromDatabase.get(i).setSaturated_fat(todaysStatEntriesFromDatabase.getString(4));
                foodsFromDatabase.get(i).setCarbohydrates(todaysStatEntriesFromDatabase.getString(5));
                foodsFromDatabase.get(i).setSugar(todaysStatEntriesFromDatabase.getString(6));
                foodsFromDatabase.get(i).setProtein(todaysStatEntriesFromDatabase.getString(7));
                foodsFromDatabase.get(i).setSalt(todaysStatEntriesFromDatabase.getString(8));
                foodsFromDatabase.get(i).setSodium(todaysStatEntriesFromDatabase.getString(9));

                i++;
            } // End while(todaysStatEntriesFromDatabase.moveToNext())
        }
        return foodsFromDatabase;
    }
    /**
     * Set the title of the page using the current date.
     * @return String title.
     */
    public String setDateTitle(){

        // Chop up the date and give back formatted date
        StringBuilder daysDate = new StringBuilder();
        StringBuffer date = new StringBuffer(getDate());

        // Day
        char char1 = date.charAt(3);
        char char2 = date.charAt(4);
        if(char1 == '0'){
            daysDate.append(char2);
            if(char2 == '1'){
                daysDate.append("st");
            } else if (char2 == '2') {
                daysDate.append("nd");
            } else if (char2 == '3') {
                daysDate.append("rd");
            } else {
                daysDate.append("th");
            }
            daysDate.append(" ");
        } else {
            daysDate.append(char1);
            daysDate.append(char2);
            if(char1 == '2'){
                if(char2 == '1'){
                    daysDate.append("st");
                } else if (char2 == '2') {
                    daysDate.append("nd");
                } else if (char2 == '3') {
                    daysDate.append("rd");
                } else {
                    daysDate.append("th");
                }
            } else if (char1 == '3'){
                if(char2 == '1'){
                    daysDate.append("st");
                } else {
                    daysDate.append("th");
                }
            } else {
                daysDate.append("th");
            }
            daysDate.append(" ");
        }

        // Month
        char char3 = date.charAt(5);
        char char4 = date.charAt(6);
        StringBuilder month = new StringBuilder().append(char3).append(char4);
        String mon = month.toString();
        if(mon.equals("01")) {
            daysDate.append(getString(R.string.january));
        } else if (mon.equals("02")){
            daysDate.append(getString(R.string.february));
        } else if (mon.equals("03")){
            daysDate.append(getString(R.string.march));
        } else if (mon.equals("04")){
            daysDate.append(getString(R.string.april));
        } else if (mon.equals("05")){
            daysDate.append(getString(R.string.may));
        } else if (mon.equals("06")){
            daysDate.append(getString(R.string.june));
        } else if (mon.equals("07")){
            daysDate.append(getString(R.string.july));
        } else if (mon.equals("08")){
            daysDate.append(getString(R.string.august));
        } else if (mon.equals("09")){
            daysDate.append(getString(R.string.september));
        } else if (mon.equals("10")){
            daysDate.append(getString(R.string.october));
        } else if (mon.equals("11")){
            daysDate.append(getString(R.string.november));
        } else if (mon.equals("12")){
            daysDate.append(getString(R.string.december));
        } else {
            daysDate.append(mon);
        }

        return daysDate.toString();
    }

    /**
     * Get the current date.
     * @return current date.
     */
    public String getDate() {
        Calendar c = Calendar.getInstance();
        String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(c.get(Calendar.MONTH) + 1);
        String year = Integer.toString(c.get(Calendar.YEAR));

        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        weekDay = dayFormat.format(c.getTime());

        if(day.length() < 2) {
            String temp = day;
            day = "0" + temp;
        }
        if(month.length() < 2) {
            String temp = month;
            month = "0" + temp;
        }
        return weekDay.substring(0, 3).toUpperCase() + day + month + year;
    }
}
