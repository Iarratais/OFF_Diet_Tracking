package com.karl.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.karl.adapters.ProgressListAdapter;
import com.karl.fyp.MainActivity;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Weight;

import java.util.ArrayList;

/**
 * Copyright Karl jones 2016.
 * ProgressFragment
 *
 * This class controls the progress fragment. This fragment contains a list with information
 * about the weight that the users logged into the application, it shows the history of this.
 */

public class ProgressFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "ProgressFragment";

    MySQLiteHelper db;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_progress, container, false);

        db = new MySQLiteHelper(getContext());

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.progress_fragment_title));

        makeList();

        return rootView;
    }

    /**
     * Get the information from the database table - weighthistory.
     * @return arraylist<weight> history.
     */
    public ArrayList<Weight> getProgressInformation(){
        Cursor weightHistoryFromDatabase = db.getWeightInformation();

        ArrayList<Weight> weightHistory = new ArrayList<>();

        if(weightHistoryFromDatabase.getCount() == 0){
            Log.d(TAG, "There is nothing in the weight table");
        } else {
            while(weightHistoryFromDatabase.moveToNext()){
                Weight weight = new Weight();
                weight.setDate(weightHistoryFromDatabase.getString(1));
                weight.setWeight(weightHistoryFromDatabase.getString(2));
                weight.setChange(weightHistoryFromDatabase.getString(3));
                weightHistory.add(weight);
            }
            return weightHistory;
        }
        return null;
    }

    /**
     * Input the data into the list adapter.
     */
    public void makeList(){
        if(getProgressInformation() != null) {
            ProgressListAdapter adapter = new ProgressListAdapter(getActivity(), getProgressInformation());
            ListView list = (ListView) rootView.findViewById(R.id.weight_progress_list);
            list.setAdapter(adapter);
        }
    }
}
