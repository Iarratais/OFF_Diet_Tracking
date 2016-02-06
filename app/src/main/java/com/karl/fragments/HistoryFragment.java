package com.karl.fragments;

import android.app.Fragment;

import com.karl.adapters.HistoryListAdapter;
import com.karl.fyp.MainActivity;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Day;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * History fragment
 *
 * Copyright Karl Jones 2016
 */
public class HistoryFragment extends Fragment {

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
            Toast.makeText(getActivity(), "No history", Toast.LENGTH_SHORT).show();
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
}
