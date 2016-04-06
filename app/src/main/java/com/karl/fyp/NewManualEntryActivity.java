package com.karl.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karl.models.Food;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Copyright Karl jones 2016.
 * NewManualEntryActivity
 *
 * This activity allows a user to input a food type manually.
 */

public class NewManualEntryActivity extends AppCompatActivity {

    private static final String TAG = "NewManualEntryActivity";

    String barcode = null;
    Boolean scan_success = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_manual_entry);

        Intent from_barcode = getIntent();
        if(from_barcode != null){
            scan_success = from_barcode.getBooleanExtra("scan_success", false);
            barcode = from_barcode.getStringExtra("barcode");
            if(barcode != null && !scan_success) {
                TextView barcodeTextView = (TextView) findViewById(R.id.new_barcode_number);
                barcodeTextView.setText(barcode);
                Toast.makeText(getApplicationContext(), getString(R.string.error_item_does_not_exist), Toast.LENGTH_SHORT).show();
            }
        }

        try {
            getSupportActionBar().setTitle(getString(R.string.new_entry_title));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_new_entry_manual_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_save:
                if(validateSaveEntry()){
                    saveFood();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    return true;
                }
            default:
                return false;
        }
    }

    /**
     * Save the food item into the database.
     */
    public void saveFood() {
        TextView foodNameTextView = (TextView) findViewById(R.id.new_food_name);
        TextView caloriesTextView = (TextView) findViewById(R.id.new_calories);
        TextView fatTextView = (TextView) findViewById(R.id.new_fat);
        TextView saturatedFatTextView = (TextView) findViewById(R.id.new_sat_fat);
        TextView carbohydratesTextView = (TextView) findViewById(R.id.new_carbohydrates);
        TextView sugarTextView = (TextView) findViewById(R.id.new_sugar);
        TextView proteinTextView = (TextView) findViewById(R.id.new_protein);
        TextView saltTextView = (TextView) findViewById(R.id.new_salt);
        TextView sodiumTextView = (TextView) findViewById(R.id.new_sodium);
        TextView barcodeNumberTextView = (TextView) findViewById(R.id.new_barcode_number);

        Food food = new Food(foodNameTextView.getText().toString(), barcodeNumberTextView.getText().toString(), caloriesTextView.getText().toString(),
                fatTextView.getText().toString(), saturatedFatTextView.getText().toString(), carbohydratesTextView.getText().toString(),
                sugarTextView.getText().toString(), proteinTextView.getText().toString(), saltTextView.getText().toString(),
                sodiumTextView.getText().toString());

        MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
        db.createNewTodayEntry(food);
    }

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

        return weekDay.substring(0, 3) + day + month + year;
    }

    /**
     * Validate the entries.
     * @return true is ok.
     */
    public boolean validateSaveEntry(){
        TextView food_name_text_view = (TextView) findViewById(R.id.new_food_name);
        if(!checkIfViewIsFilled(food_name_text_view)){
            setViewFocus(food_name_text_view);
            makeToast(getString(R.string.new_entry_food_name));
            return false;
        }

        TextView calories_text_view = (TextView) findViewById(R.id.new_calories);
        if(!checkIfViewIsFilled(calories_text_view)){
            setViewFocus(calories_text_view);
            makeToast(getString(R.string.calories));
            return false;
        }

        TextView fat_text_view = (TextView) findViewById(R.id.new_fat);
        if(!checkIfViewIsFilled(fat_text_view)){
            setViewFocus(fat_text_view);
            makeToast(getString(R.string.fat));
            return false;
        }

        TextView sat_fat_text_view = (TextView) findViewById(R.id.new_sat_fat);
        if(!checkIfViewIsFilled(sat_fat_text_view)){
            setViewFocus(sat_fat_text_view);
            makeToast(getString(R.string.saturated_fat));
            return false;
        }

        TextView carbs_text_view = (TextView) findViewById(R.id.new_carbohydrates);
        if(!checkIfViewIsFilled(carbs_text_view)){
            setViewFocus(carbs_text_view);
            makeToast(getString(R.string.carbohydrate));
            return false;
        }

        TextView sugar_text_view = (TextView) findViewById(R.id.new_sugar);
        if(!checkIfViewIsFilled(sugar_text_view)){
            setViewFocus(sugar_text_view);
            makeToast(getString(R.string.sugar));
            return false;
        }

        TextView protein_text_view = (TextView) findViewById(R.id.new_protein);
        if(!checkIfViewIsFilled(protein_text_view)){
            setViewFocus(protein_text_view);
            makeToast(getString(R.string.protein));
            return false;
        }

        TextView salt_text_view = (TextView) findViewById(R.id.new_salt);
        if(!checkIfViewIsFilled(salt_text_view)){
            setViewFocus(salt_text_view);
            makeToast(getString(R.string.salt));
            return false;
        }

        TextView sodium_text_view = (TextView) findViewById(R.id.new_sodium);
        if(!checkIfViewIsFilled(sodium_text_view)){
            setViewFocus(sodium_text_view);
            makeToast(getString(R.string.sodium));
            return false;
        }

        TextView barcode_number_view = (TextView) findViewById(R.id.new_barcode_number);
        if(barcode_number_view.getText().toString().equals("")){
            barcode_number_view.setText("0000000000000");
        }
        return true;
    }

    public void setViewFocus(View view){
        view.requestFocus();
    }

    public void makeToast(String attribute){
        Toast.makeText(getApplicationContext(), getString(R.string.new_entry_attribute_missing, attribute.toLowerCase()), Toast.LENGTH_SHORT).show();
    }

    /**
     * Check if the view has text in it.
     * @param view: view to check.
     * @return false if it equals "".
     */
    public boolean checkIfViewIsFilled(TextView view){
        return !view.getText().toString().equals("");
    }
}
