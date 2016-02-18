package com.karl.fyp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.karl.fragments.ProfileSetupFour;
import com.karl.fragments.ProfileSetupOne;
import com.karl.fragments.ProfileSetupThree;
import com.karl.fragments.ProfileSetupTwo;

public class ProfileSetUp extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private String user_name = null;
    private String user_gender = null;
    private String user_height = null;
    private String user_weight = null;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set_up);

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
                    return new ProfileSetupFour();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
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
            }
            return null;
        }
    }

    public void createNewUser(){
        MySQLiteHelper db = new MySQLiteHelper(this);

        db.createUser(getUser_name(), getUser_gender(), getUser_height(), getUser_weight());

        Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT).show();
    }


    public String getUser_weight() {
        return user_weight;
    }

    public void setUser_weight(String user_weight) {
        user_weight = user_weight;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        user_name = user_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        user_gender = user_gender;
    }

    public String getUser_height() {
        return user_height;
    }

    public void setUser_height(String user_height) {
        user_height = user_height;
    }
}
