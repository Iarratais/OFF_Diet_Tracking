package com.karl.fyp;

import android.app.Activity;
import android.content.Intent;
import com.karl.fyp.R;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {

    // Splash screen timer
    private static double timer = 2.5;                           // Put in seconds value here
    private static int SPLASH_TIME_OUT = (int)(timer * 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        runSplash();
    }

    public void runSplash() {
        new Handler().postDelayed(new Runnable() {

            // This method is run after the timer has expired
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
