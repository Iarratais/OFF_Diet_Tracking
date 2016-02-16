package com.karl.fyp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Set the activity title
        try {
            getSupportActionBar().setTitle(getString(R.string.about_fragment_title));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        TextView versionNumber = (TextView) findViewById(R.id.version_number_textview);
        versionNumber.setText("Version " + BuildConfig.VERSION_NAME);
    }

    public void onBackPressed() {
        finish();
    }
}
