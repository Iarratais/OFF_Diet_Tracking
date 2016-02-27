package com.karl.fragments;

import android.app.Fragment;

import com.karl.adapters.HistoryListAdapter;
import com.karl.fyp.MainActivity;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Day;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * History fragment
 *
 * Copyright Karl Jones 2016
 */
public class HistoryFragment extends android.support.v4.app.Fragment {

    MySQLiteHelper db;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_history, container, false);

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.history_fragment_title));

        db = new MySQLiteHelper(getActivity());

        getInformationFromDatabase();

        makeList();

        return rootView;

    }

    ArrayList<Day> days;
    ArrayList<String> dates;
    /**
     * Get the information about the users history from the database
     */
    public void getInformationFromDatabase(){
        Cursor res = db.getHistory();
        days = new ArrayList<>();
        dates = new ArrayList<>();

        if(res.getCount() == 0) {
            ListView list = (ListView) rootView.findViewById(R.id.history_list);
            list.setVisibility(View.GONE);

            ImageView nothingtoshow = (ImageView) rootView.findViewById(R.id.nothing_to_show_history);
            nothingtoshow.setVisibility(View.VISIBLE);
            TextView nothingtoshowtext = (TextView) rootView.findViewById(R.id.nothing_to_show_text_history);
            nothingtoshowtext.setVisibility(View.VISIBLE);
        } else {
            while(res.moveToNext()) {
                // Column count = 10
                // history_id date calories fats sat_fat salt sodium carbohydrates sugar protein
                Day day = new Day();
                day.setId(res.getString(0));
                dates.add(res.getString(1));
                day.setCalories(res.getString(2));
                day.setFats(res.getString(3));
                day.setSaturated_fat(res.getString(4));
                day.setSalt(res.getString(5));
                day.setSodium(res.getString(6));
                day.setCarbs(res.getString(7));
                day.setSugar(res.getString(8));
                day.setProtein(res.getString(9));

                days.add(day);
            }
        }
    }

    /**
     * Add the information to the list in the fragment
     */
    public void makeList() {
        String[] datestrings = new String[dates.size()];
        dates.toArray(datestrings);
        HistoryListAdapter adapter = new HistoryListAdapter(getActivity(), datestrings, days);
        ListView list = (ListView) rootView.findViewById(R.id.history_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Set the title of the page using the current date.
     * @return String title/
     */
    public String setDateTitle(String dates){

        // Chop up the date and give back formatted date
        StringBuilder daysDate = new StringBuilder();
        StringBuffer date = new StringBuffer(dates);

        // Day
        char char1 = date.charAt(0);
        char char2 = date.charAt(1);
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
        char char3 = date.charAt(2);
        char char4 = date.charAt(3);
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
     * @return string of current date. For example, 02012016.
     */
    public String getDate(){
        Calendar c = Calendar.getInstance();
        String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(c.get(Calendar.MONTH) + 1);
        String year = Integer.toString(c.get(Calendar.YEAR));

        if(day.length() < 2) {
            String temp = day;
            day = "0" + temp;
        }
        if(month.length() < 2) {
            String temp = month;
            month = "0" + temp;
        }

        return day + month + year;
    }
}
