package com.karl.fyp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karl.models.Goals;

/**
 * Copyright Karl jones 2016.
 * EditGoalsActivity
 *
 * This activity is used to allow the user to edit their goals that they set within the application.
 */

public class EditGoalsActivity extends AppCompatActivity {

    private static final String TAG = "GoalsFragment";

    MySQLiteHelper db;

    EditText caloriesEditText;
    EditText fatEditText;
    EditText saturatedFatsEditText;
    EditText saltEditText;
    EditText sodiumEditText;
    EditText carbohydratesEditText;
    EditText sugarEditText;
    EditText proteinEditText;
    EditText weightEditText;

    Button saveChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        addTextChangedListenersToViews();

        db = new MySQLiteHelper(getApplicationContext());

        getInformationFromDatabase();
    }

    /**
     * Set up the layout views
     */
    public void addTextChangedListenersToViews(){
        caloriesEditText = (EditText) findViewById(R.id.goals_calories);
        caloriesEditText.addTextChangedListener(calories_view_listener);

        fatEditText = (EditText) findViewById(R.id.goals_fat);
        fatEditText.addTextChangedListener(fat_view_listener);

        saturatedFatsEditText = (EditText) findViewById(R.id.goals_sat_fat);
        saturatedFatsEditText.addTextChangedListener(sat_fat_view_listener);

        saltEditText = (EditText) findViewById(R.id.goals_salt);
        saltEditText.addTextChangedListener(salt_view_listener);

        sodiumEditText = (EditText) findViewById(R.id.goals_sodium);
        sodiumEditText.addTextChangedListener(sodium_view_listener);

        carbohydratesEditText = (EditText) findViewById(R.id.goals_carbohydrates);
        carbohydratesEditText.addTextChangedListener(carbs_view_listener);

        sugarEditText = (EditText) findViewById(R.id.goals_sugar);
        sugarEditText.addTextChangedListener(sugar_view_listener);

        proteinEditText = (EditText) findViewById(R.id.goals_protein);
        proteinEditText.addTextChangedListener(protein_view_listener);

        weightEditText = (EditText) findViewById(R.id.goals_weight);
        weightEditText.addTextChangedListener(weight_view_listener);

        saveChangesButton = (Button) findViewById(R.id.save_changes_goals);
        saveChangesButton.setVisibility(View.GONE);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChangesButton.setVisibility(View.GONE);
                if (updateUserGoals() == 1) {
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
    public void getInformationFromDatabase(){
        Cursor goalsInformation = db.getGoals();

        if(goalsInformation.getCount() == 0) {
            Log.d(TAG, "Nothing in goals table");
        } else {
            while(goalsInformation.moveToNext()) {
                caloriesEditText.setText(goalsInformation.getString(1));
                fatEditText.setText(goalsInformation.getString(2));
                saturatedFatsEditText.setText(goalsInformation.getString(3));
                saltEditText.setText(goalsInformation.getString(4));
                sodiumEditText.setText(goalsInformation.getString(5));
                carbohydratesEditText.setText(goalsInformation.getString(6));
                sugarEditText.setText(goalsInformation.getString(7));
                proteinEditText.setText(goalsInformation.getString(8));
                weightEditText.setText(goalsInformation.getString(9));
            }
        }
    }

    /**
     * Update the database with new information.
     */
    public int updateUserGoals() {
        return db.updateGoals(getInformationFromViews());
    }

    /**
     * This method gets the information from the views and puts in into a Goals object.
     * @return Goal object.
     */
    public Goals getInformationFromViews(){
        Goals goal = new Goals();

        goal.setCalories(caloriesEditText.getText().toString());
        goal.setFat(fatEditText.getText().toString());
        goal.setSaturatedFat(saturatedFatsEditText.getText().toString());
        goal.setSalt(saltEditText.getText().toString());
        goal.setSodium(sodiumEditText.getText().toString());
        goal.setCarbohydrates(carbohydratesEditText.getText().toString());
        goal.setSugar(sugarEditText.getText().toString());
        goal.setProtein(proteinEditText.getText().toString());
        goal.setWeight(weightEditText.getText().toString());

        return goal;
    }

    /**
     * Show a toast to the user.
     * @param message to show to the user.
     */
    public void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Text changed listeners
    private final TextWatcher calories_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveChangesButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher fat_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveChangesButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher sat_fat_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveChangesButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher salt_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveChangesButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher sodium_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveChangesButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher carbs_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveChangesButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher sugar_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveChangesButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher protein_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveChangesButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
    private final TextWatcher weight_view_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveChangesButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}