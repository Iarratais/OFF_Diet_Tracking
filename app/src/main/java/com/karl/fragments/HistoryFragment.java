package com.karl.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.karl.adapters.HistoryListAdapter;
import com.karl.fyp.MainActivity;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Day;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Copyright Karl jones 2016.
 *
 * Fragment containing a list of the totals for the entries that the user has input into the
 * system. These are pre-calculated and input into the history table of the database.
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

        return rootView;
    }

    /**
     * Get the information about the users history from the database
     */
    public void getInformationFromDatabase(){
        Cursor informationFromDatabase = db.getHistory();
        ArrayList<Day> informationByDay = new ArrayList<>();
        ArrayList<String> datesList = new ArrayList<>();

        if(informationFromDatabase.getCount() == 0) {
            ListView list = (ListView) rootView.findViewById(R.id.history_list);
            list.setVisibility(View.GONE);

            ImageView nothingToShowImageView = (ImageView) rootView.findViewById(R.id.nothing_to_show_history);
            nothingToShowImageView.setVisibility(View.VISIBLE);
            TextView nothingToShowTextView = (TextView) rootView.findViewById(R.id.nothing_to_show_text_history);
            nothingToShowTextView.setVisibility(View.VISIBLE);
        } else {
            while(informationFromDatabase.moveToNext()) {
                // history_id date calories fats sat_fat salt sodium carbohydrates sugar protein
                Day day = new Day();
                day.setId(informationFromDatabase.getString(0));
                datesList.add(informationFromDatabase.getString(1));
                day.setCalories(informationFromDatabase.getString(2));
                day.setFats(informationFromDatabase.getString(3));
                day.setSaturated_fat(informationFromDatabase.getString(4));
                day.setSalt(informationFromDatabase.getString(5));
                day.setSodium(informationFromDatabase.getString(6));
                day.setCarbs(informationFromDatabase.getString(7));
                day.setSugar(informationFromDatabase.getString(8));
                day.setProtein(informationFromDatabase.getString(9));

                informationByDay.add(day);
            }
        } // end if-else
        makeList(informationByDay, datesList);
    }

    /**
     * Create the list for the fragment.
     * @param informationByDay information from the database.
     * @param datesList list of dates.
     */
    public void makeList(ArrayList<Day> informationByDay, ArrayList<String> datesList) {
        String[] datestrings = new String[datesList.size()];
        datesList.toArray(datestrings);
        HistoryListAdapter adapter = new HistoryListAdapter(getActivity(), datestrings, informationByDay);
        ListView list = (ListView) rootView.findViewById(R.id.history_list);
        list.setAdapter(adapter);
    }

    /**
     * Get the current date.
     * @return string of current date. For example, WED02012016.
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
