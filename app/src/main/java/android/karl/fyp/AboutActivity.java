package android.karl.fyp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Set the activity title
        try {
            getSupportActionBar().setTitle(getString(R.string.about_fragment_title));
        } catch (NullPointerException e) {
            System.out.println("AboutActivity - onCreate(): " + e);
        }
    }

    public void onBackPressed() {
        finish();
    }
}
