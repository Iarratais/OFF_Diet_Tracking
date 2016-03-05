package com.karl.fragments;

import com.karl.fyp.EditGoalsActivity;
import com.karl.fyp.MainActivity;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Goals;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;

/**
 * Goals fragment
 *
 * Copyright Karl Jones 2016
 */
public class GoalsFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "GoalsFragment";

    TextView calories_view;
    TextView fat_view;
    TextView sat_fat_view;
    TextView salt_view;
    TextView sodium_view;
    TextView carbs_view;
    TextView sugar_view;
    TextView protein_view;
    TextView weight_view;

    MySQLiteHelper db;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_goals, container, false);

        db = new MySQLiteHelper(getActivity());

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.goals_fragment_title));

        Button edit_button = (Button) rootView.findViewById(R.id.edit_goals_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditGoalsActivity.class));
            }
        });

        setupViews();

        getInformationFromDB();

        return rootView;
    }

    /**
     * Set up the views.
     */
    public void setupViews(){
        calories_view = (TextView) rootView.findViewById(R.id.calorie_goal);

        fat_view = (TextView) rootView.findViewById(R.id.fat_goal);

        sat_fat_view = (TextView) rootView.findViewById(R.id.sat_fat_goal);

        salt_view = (TextView) rootView.findViewById(R.id.salt_goal);

        sodium_view = (TextView) rootView.findViewById(R.id.sodium_goal);

        carbs_view = (TextView) rootView.findViewById(R.id.carbohydrates_goal);

        sugar_view = (TextView) rootView.findViewById(R.id.sugar_goal);

        protein_view = (TextView) rootView.findViewById(R.id.protein_goal);

        weight_view = (TextView) rootView.findViewById(R.id.weight_goal);
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
                Typeface normalTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/New_Cicle_Gordita.ttf");

                // Calories
                String calories = getString(R.string.calories) + getString(R.string.colon) + " " + res.getString(1);
                calories_view.setText(calories);
                calories_view.setTypeface(normalTypeface);

                // Fat
                String fat = getString(R.string.fat) + getString(R.string.colon) + " " + res.getString(2) + getString(R.string.grams_abbv);
                fat_view.setText(fat);
                fat_view.setTypeface(normalTypeface);

                // Sat fat
                String sat_fat = getString(R.string.saturated_fat) + getString(R.string.colon) + " " + res.getString(3) + getString(R.string.grams_abbv);
                sat_fat_view.setText(sat_fat);
                sat_fat_view.setTypeface(normalTypeface);

                // Salt
                String salt = getString(R.string.salt) + getString(R.string.colon) + " " + res.getString(4) + getString(R.string.grams_abbv);
                salt_view.setText(salt);
                salt_view.setTypeface(normalTypeface);

                // Sodium
                String sodium = getString(R.string.sodium) + getString(R.string.colon) + " " + res.getString(5) + getString(R.string.grams_abbv);
                sodium_view.setText(sodium);
                sodium_view.setTypeface(normalTypeface);

                // Carbohydrates
                String carbohydrates = getString(R.string.carbohydrate) + getString(R.string.colon) + " " + res.getString(6) + getString(R.string.grams_abbv);
                carbs_view.setText(carbohydrates);
                carbs_view.setTypeface(normalTypeface);

                // Sugar
                String sugar = getString(R.string.sugar) + getString(R.string.colon) + " " + res.getString(7) + getString(R.string.grams_abbv);
                sugar_view.setText(sugar);
                sugar_view.setTypeface(normalTypeface);

                // Protein
                String protein = getString(R.string.protein) + getString(R.string.colon) + " " + res.getString(8) + getString(R.string.grams_abbv);
                protein_view.setText(protein);
                protein_view.setTypeface(normalTypeface);

                // Weight
                String weight = getString(R.string.weigh) + getString(R.string.colon) + " " + res.getString(9) + getString(R.string.kg);
                weight_view.setText(weight);
                weight_view.setTypeface(normalTypeface);
            }
        }
    }
}
