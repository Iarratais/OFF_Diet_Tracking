package com.karl.fyp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set the activity title
        try {
            getSupportActionBar().setTitle(getString(R.string.settings_fragment_title));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        final SharedPreferences prefs = this.getSharedPreferences("com.karl.fyp", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        Switch show_days = (Switch) findViewById(R.id.show_days);
        boolean show_day = prefs.getBoolean("showDays", false);
        show_days.setChecked(show_day);
        System.out.println("Show day "+ show_day);
        show_days.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("showDays", true);
                    editor.apply();
                } else {
                    editor.putBoolean("showDays", false);
                    editor.apply();
                }

            }
        });
    }

    public void onBackPressed() {
        finish();
    }
}
