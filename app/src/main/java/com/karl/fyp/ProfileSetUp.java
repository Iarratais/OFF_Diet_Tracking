package com.karl.fyp;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.karl.alerts.MyAlertDialogFragment;
import com.karl.fragments.ProfileSetupFive;
import com.karl.fragments.ProfileSetupFour;
import com.karl.fragments.ProfileSetupOne;
import com.karl.fragments.ProfileSetupThree;
import com.karl.fragments.ProfileSetupTwo;

import tyrantgit.explosionfield.ExplosionField;

public class ProfileSetUp extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private String user_name = null;
    private String user_gender = null;
    private String user_height = null;
    private String user_weight = null;
    private String user_desired = null;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set_up);

        DialogFragment dialog = MyAlertDialogFragment.newInstance("Beta", "Hi! Thanks for beta testing the application, please report any feedback that you may have. This is in very early stages");
        dialog.show(getFragmentManager(), "dialog");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new ProfileSetupOne();
                case 1:
                    return new ProfileSetupTwo();
                case 2:
                    return new ProfileSetupThree();
                case 3:
                    return new ProfileSetupFive();
                case 4:
                    return new ProfileSetupFour();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
            }
            return null;
        }
    }

    /**
     * This method controls the "save profile" button, the animation and the checking of
     * information that is entered into the system.
     * @param v view to explode.
     */
    public void createNewUser(View v){
        MySQLiteHelper db = new MySQLiteHelper(this);

        final ExplosionField explosionField = ExplosionField.attach2Window(this);

        if(getUser_name() == null || getUser_name().equals("")){
            Toast.makeText(getApplicationContext(), getString(R.string.profile_set_up_you_need_to_enter, getString(R.string.profile_fragment_name).toLowerCase()), Toast.LENGTH_SHORT).show();
            mViewPager.setCurrentItem(1);
        } else if (getUser_gender() == null || getUser_gender().equals("")) {
            Toast.makeText(getApplicationContext(), getString(R.string.profile_set_up_you_need_to_enter, getString(R.string.profile_fragment_gender).toLowerCase()), Toast.LENGTH_SHORT).show();
            mViewPager.setCurrentItem(1);
        } else if (getUser_height() == null || getUser_height().equals("")){
            Toast.makeText(getApplicationContext(), getString(R.string.profile_set_up_you_need_to_enter, getString(R.string.profile_fragment_height).toLowerCase()), Toast.LENGTH_SHORT).show();
            mViewPager.setCurrentItem(2);
        } else if (getUser_weight() == null || getUser_weight().equals("")){
            Toast.makeText(getApplicationContext(), getString(R.string.profile_set_up_you_need_to_enter, getString(R.string.profile_fragment_weight).toLowerCase()), Toast.LENGTH_SHORT).show();
            mViewPager.setCurrentItem(2);
        } else if (getUser_desired() == null || getUser_desired().equals("")) {
            Toast.makeText(getApplicationContext(), getString(R.string.profile_set_up_you_need_to_enter, getString(R.string.profile_set_up_desired_weight).toLowerCase()), Toast.LENGTH_SHORT).show();
            mViewPager.setCurrentItem(3);
        } else {
            db.createUser(getUser_name(), getUser_gender(), getUser_height(), getUser_weight());
            db.setDefaultGoals(getUser_desired());

            // Update the SharedPreferences so this screen is never shown again.
            SharedPreferences prefs = this.getSharedPreferences("com.karl.fyp", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirst", false);
            editor.apply();

            explosionField.explode(v);

            /**
             * Delay the changing of the activity for 1000 milliseconds.
             */
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }, 1000);
        }
    }


    public String getUser_weight() {
        return user_weight;
    }

    public void setUser_weight(String user_weight) {
        this.user_weight = user_weight;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_height() {
        return user_height;
    }

    public void setUser_height(String user_height) {
        this.user_height = user_height;
    }

    public String getUser_desired() {
        return user_desired;
    }

    public void setUser_desired(String user_desired) {
        this.user_desired = user_desired;
    }
}
