package com.karl.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.karl.fragments.MyInputAlertDialogFragment;
import com.karl.fragments.ResultPer100Fragment;
import com.karl.fragments.ResultServingFragment;
import com.karl.models.Food;

import java.text.DecimalFormat;

public class SearchResultActivity extends AppCompatActivity {

    private static final double SMALL_DEVICE_THRESHOLD = 4.4;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public String section_1 = "100g/100ml";
    public String section_2 = "Per Serving";

    private final static int NUM_PAGES = 2;

    // Food information
    Food food;

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

        food = new Food();

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
                showInputDialog();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save:
                showInputDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set the title of the actionbar.
     * @param title of the actionbar.
     */
    public void setTitle(String title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
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

    /**
     * Calculate the screen size.
     * @return the screen size in inches.
     */
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

    public Food incomingFood;

    public void setInformation(Food foods){
        incomingFood = new Food();
        incomingFood = foods;
    }

    public void showInputDialog() {
        android.support.v4.app.DialogFragment dialog = MyInputAlertDialogFragment.newInstance(getString(R.string.lookup_fragment_serving_size) + " " + incomingFood.getServing_size());
        dialog.show(getSupportFragmentManager().beginTransaction(), "dialog");
    }

    public void calculateInformation(String amt){
        Food gram = new Food();
        final DecimalFormat df = new DecimalFormat("#.###");

        gram.setName(incomingFood.getName());
        gram.setCalories(String.valueOf(df.format((Double.parseDouble(incomingFood.getCalories()) / 100.0) * Integer.valueOf(amt))));
        gram.setFats(String.valueOf(df.format((Double.parseDouble(incomingFood.getFats()) / 100) * Integer.valueOf(amt))));
        gram.setSaturated_fat(String.valueOf(df.format((Double.parseDouble(incomingFood.getSaturated_fat()) / 100) * Integer.valueOf(amt))));
        gram.setCarbohydrates(String.valueOf(df.format((Double.parseDouble(incomingFood.getCarbohydrates()) / 100) * Integer.valueOf(amt))));
        gram.setSugar(String.valueOf(df.format((Double.parseDouble(incomingFood.getSugar()) / 100) * Integer.valueOf(amt))));
        gram.setProtein(String.valueOf(df.format((Double.parseDouble(incomingFood.getProtein()) / 100) * Integer.valueOf(amt))));
        gram.setSalt(String.valueOf(df.format((Double.parseDouble(incomingFood.getSaturated_fat()) / 100) * Integer.valueOf(amt))));
        gram.setSodium(String.valueOf(df.format((Double.parseDouble(incomingFood.getSodium()) / 100) * Integer.valueOf(amt))));
        gram.setBarcode_number(incomingFood.getBarcode_number());

        System.out.println("CalculateInformation(): " + gram.toString());

        MySQLiteHelper db = new MySQLiteHelper(this);
        db.createNewEntryToday(gram);
        System.out.println(gram.toString());

        Toast.makeText(getApplicationContext(), getString(R.string.search_results_result_added, gram.getName()), Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}

