package com.karl.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

        Button faq = (Button) findViewById(R.id.faq_button);
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FAQActivity.class));
            }
        });
    }

    public void onBackPressed() {
        finish();
    }
}
