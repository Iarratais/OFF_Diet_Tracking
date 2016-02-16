package com.karl.fyp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.karl.models.Food;

public class NewManualEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_manual_entry);
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
}
