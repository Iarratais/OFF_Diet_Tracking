package com.karl.fragments;

import com.karl.fyp.MainActivity;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Goals;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Goals fragment
 *
 * Copyright Karl Jones 2016
 */
public class GoalsFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "GoalsFragment";

    MySQLiteHelper db;

    EditText calories_view;
    EditText fat_view;
    EditText sat_fat_view;
    EditText salt_view;
    EditText sodium_view;
    EditText carbs_view;
    EditText sugar_view;
    EditText protein_view;

    Button save_changes;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_goals, container, false);

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.goals_fragment_title));

        db = new MySQLiteHelper(getActivity());

        setUpViews();

        getInformationFromDB();

        return rootView;
    }

    /**
     * Set up the layout views
     */
    public void setUpViews(){
        calories_view = (EditText) rootView.findViewById(R.id.goals_calories);
        calories_view.addTextChangedListener(calories_view_listener);

        fat_view = (EditText) rootView.findViewById(R.id.goals_fat);
        fat_view.addTextChangedListener(fat_view_listener);

        sat_fat_view = (EditText) rootView.findViewById(R.id.goals_sat_fat);
        sat_fat_view.addTextChangedListener(sat_fat_view_listener);

        salt_view = (EditText) rootView.findViewById(R.id.goals_salt);
        salt_view.addTextChangedListener(salt_view_listener);

        sodium_view = (EditText) rootView.findViewById(R.id.goals_sodium);
        sodium_view.addTextChangedListener(sodium_view_listener);

        carbs_view = (EditText) rootView.findViewById(R.id.goals_carbohydrates);
        carbs_view.addTextChangedListener(carbs_view_listener);

        sugar_view = (EditText) rootView.findViewById(R.id.goals_sugar);
        sugar_view.addTextChangedListener(sugar_view_listener);

        protein_view = (EditText) rootView.findViewById(R.id.goals_protein);
        protein_view.addTextChangedListener(protein_view_listener);

        save_changes = (Button) rootView.findViewById(R.id.save_changes_goals);
        save_changes.setVisibility(View.GONE);
        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_changes.setVisibility(View.GONE);
                if(updateDatabase() == 1){
                    showToast(getString(R.string.goals_fragment_update_successful));
                } else {
                    showToast(getString(R.string.goals_fragment_update_failure));
                }
            }
        });
    }

    /**
     * Get the information of the goals from the database.
     */
    public void getInformationFromDB(){
        Cursor res = db.getGoals();

        if(res.getCount() == 0) {
            Log.d(TAG, "Nothing in goals table");
        } else {
            while(res.moveToNext()) {
                calories_view.setText(res.getString(1));
                fat_view.setText(res.getString(2));
                sat_fat_view.setText(res.getString(3));
                salt_view.setText(res.getString(4));
                sodium_view.setText(res.getString(5));
                carbs_view.setText(res.getString(6));
                sugar_view.setText(res.getString(7));
                protein_view.setText(res.getString(8));
            }
        }
    }

    /**
     * Update the database with new information.
     */
    public int updateDatabase() {
        return db.updateGoals(getInformationFromViews());
    }

    /**
     * This method gets the information from the views and puts in into a Goals object.
     * @return Goals object.
     */
    public Goals getInformationFromViews(){
        Goals goal = new Goals();

        goal.setCalories(calories_view.getText().toString());
        goal.setFat(fat_view.getText().toString());
        goal.setSaturatedFat(sat_fat_view.getText().toString());
        goal.setSalt(salt_view.getText().toString());
        goal.setSodium(sodium_view.getText().toString());
        goal.setCarbohydrates(carbs_view.getText().toString());
        goal.setSugar(sugar_view.getText().toString());
        goal.setProtein(protein_view.getText().toString());

        return goal;
    }

    public void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    // Text changed listeners
    private final TextWatcher calories_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            save_changes.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher fat_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            save_changes.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher sat_fat_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            save_changes.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher salt_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            save_changes.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher sodium_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            save_changes.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher carbs_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            save_changes.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher sugar_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            save_changes.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher protein_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            save_changes.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}
