package android.karl.fyp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set the activity title
        try {
            getSupportActionBar().setTitle(getString(R.string.settings_fragment_title));
        } catch (NullPointerException e) {
            System.out.println("SettingsActivity - onCreate(): " + e);
        }
    }

    public void onBackPressed() {
        finish();
    }
}
