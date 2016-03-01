package com.karl.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.karl.models.Food;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
            if(barcode != null) {
                Log.d(TAG, barcode);
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
                saveFood();
                return true;
            default:
                return false;
        }
    }

    /**
     * Save the food item into the database.
     */
    public void saveFood() {
        TextView food_name_text_view = (TextView) findViewById(R.id.new_food_name);
        TextView calories_text_view = (TextView) findViewById(R.id.new_calories);
        TextView fat_text_view = (TextView) findViewById(R.id.new_fat);
        TextView sat_fat_text_view = (TextView) findViewById(R.id.new_sat_fat);
        TextView carbs_text_view = (TextView) findViewById(R.id.new_carbohydrates);
        TextView sugar_text_view = (TextView) findViewById(R.id.new_sugar);
        TextView protein_text_view = (TextView) findViewById(R.id.new_protein);
        TextView salt_text_view = (TextView) findViewById(R.id.new_salt);
        TextView sodium_text_view = (TextView) findViewById(R.id.new_sodium);

        Food food = new Food(food_name_text_view.getText().toString(), "000000", calories_text_view.getText().toString(),
                fat_text_view.getText().toString(), sat_fat_text_view.getText().toString(), carbs_text_view.getText().toString(),
                sugar_text_view.getText().toString(), protein_text_view.getText().toString(), salt_text_view.getText().toString(),
                sodium_text_view.getText().toString());

        MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
        db.createNewEntryToday(food);
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
}
