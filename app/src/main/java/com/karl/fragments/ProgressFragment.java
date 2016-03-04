package com.karl.fragments;

import com.karl.adapters.ProgressListAdapter;
import com.karl.fyp.MainActivity;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Weight;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


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

    public ArrayList<Weight> getProgressInformation(){
        Cursor res = db.getWeightInformation();

        ArrayList<Weight> weights = new ArrayList<>();

        if(res.getCount() == 0){
            Log.d(TAG, "There is nothing in the weight table");
        } else {
            while(res.moveToNext()){
                Weight weight = new Weight();
                weight.setDate(res.getString(1));
                weight.setWeight(res.getString(2));
                weight.setChange(res.getString(3));
                weights.add(weight);
            }
            return weights;
        }
        return null;
    }

    public void makeList(){
        final SharedPreferences prefs = getContext().getSharedPreferences("com.karl.fyp", Context.MODE_PRIVATE);
        boolean showDays = prefs.getBoolean("showDays", false);

        ProgressListAdapter adapter = new ProgressListAdapter(getActivity(), getProgressInformation(), showDays);
        ListView list = (ListView) rootView.findViewById(R.id.weight_progress_list);
        list.setAdapter(adapter);
    }
}
