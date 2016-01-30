package com.karl.fyp;

import android.content.Intent;
import com.karl.fragments.ResultPer100Fragment;
import com.karl.fragments.ResultServingFragment;

import com.karl.fyp.R;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class SearchResultActivity extends AppCompatActivity {

    private static final double SMALL_DEVICE_THRESHOLD = 4.4;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public String section_1 = "100g/100ml";
    public String section_2 = "Per Serving";

    private final static int NUM_PAGES = 2;

    public String barcode_number = "4260427290019";

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        barcode_number = i.getStringExtra("barcode");
        if(barcode_number.equals("")) {
            barcode_number = "4260427290019";
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_result, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Check the screen size of the device
        if(getScreenSize() < SMALL_DEVICE_THRESHOLD) {
            fab.setVisibility(View.GONE);
            MenuItem addButton = menu.findItem(R.id.action_save);
            addButton.setVisible(true);
        } else {
            MenuItem addButton = menu.findItem(R.id.action_save);
            addButton.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
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
            switch(position) {
                case 0:
                    return new ResultPer100Fragment(barcode_number);
                case 1:
                    return new ResultServingFragment(barcode_number);
            }
            return new ResultPer100Fragment(barcode_number);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        /**
         *
         * @param position that the page is at
         * @return the title of the page
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return section_1;
                case 1:
                    return section_2;
            }
            return null;
        }
    }

    public double getScreenSize(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        int density = displayMetrics.densityDpi;
        double wi = (double)width / (double)density;
        double hi = (double)height / (double) density;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        return Math.sqrt(x+y);
    }
}

