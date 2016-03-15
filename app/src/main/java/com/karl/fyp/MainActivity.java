package com.karl.fyp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.karl.fragments.AddToDatabaseFragment;
import com.karl.fragments.AnalysisActivityFragment;
import com.karl.fragments.DiaryFragment;
import com.karl.fragments.GoalsFragment;
import com.karl.fragments.HistoryFragment;
import com.karl.fragments.LookupFragment;
import com.karl.alerts.MyListAlertDialogFragment;
import com.karl.fragments.ProfileFragment;
import com.karl.fragments.ProgressFragment;
import com.karl.fragments.RecipeKeepFragment;
import com.karl.fragments.TodayFragment;

/**
 * Copyright Karl jones 2016.
 * MainActivity
 *
 * This is the mainactivity that controls the whole application.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final double SMALL_DEVICE_THRESHOLD = 4.4;
    private static final String TAG = "MainActivity";

    FloatingActionButton floatingActionButton;

    MySQLiteHelper db;

    String usersName;

    TextView navigationBarNameSpaceTextView;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Floating action button
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentUserOptions();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        db = new MySQLiteHelper(this);

        getTheUsersNameFromDatabase();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                setNewEntryVisibility(false);
            }
        });
        navigationBarNameSpaceTextView = (TextView) header.findViewById(R.id.nav_bar_name);
        setNameNavigationHeader();

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new TodayFragment()).commit();

        // Check if Recipe Keep is installed on the device. If it is, hide the menu item.
        Boolean recipeKeepInstalled = appInstalledOrNot("com.karl.recipekeeper");
        if(recipeKeepInstalled){
            Menu menu = navigationView.getMenu();
            MenuItem target = menu.findItem(R.id.nav_recipe_keep);
            target.setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Check the screen size of the device
        if(getScreenSize() < SMALL_DEVICE_THRESHOLD) {
            hideFloatingActionButton();
            MenuItem addButton = menu.findItem(R.id.action_new_entry);
            addButton.setVisible(true);
        }
        if(getScreenSize() > SMALL_DEVICE_THRESHOLD) {
            MenuItem addButton = menu.findItem(R.id.action_new_entry);
            addButton.setVisible(false);
            showFloatingActionButton();
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_new_entry:
                presentUserOptions();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Get the fragment manager
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today) {

            // Switch to the Today tab
            fm.beginTransaction().replace(R.id.content_frame, new TodayFragment()).commit();

            setNewEntryVisibility(true);

            showFloatingActionButton();
        } else if (id == R.id.nav_history) {

            // Switch to the History tab
            fm.beginTransaction().replace(R.id.content_frame, new HistoryFragment()).commit();

            setNewEntryVisibility(true);

            hideFloatingActionButton();
        } else if (id == R.id.nav_lookup) {

            // Switch to the Lookup tab
            fm.beginTransaction().replace(R.id.content_frame, new LookupFragment()).commit();

            setNewEntryVisibility(false);

            hideFloatingActionButton();
        } else if (id == R.id.nav_addtodatabase){

            // Switch to the goals tab
            fm.beginTransaction().replace(R.id.content_frame, new AddToDatabaseFragment()).commit();

            setNewEntryVisibility(true);

            hideFloatingActionButton();
        } else if (id == R.id.nav_goals){

            // Switch to the goals tab
            fm.beginTransaction().replace(R.id.content_frame, new GoalsFragment()).commit();

            setNewEntryVisibility(true);

            hideFloatingActionButton();
        }else if (id == R.id.nav_about) {

            // Switch to the about section
            Intent i = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_progress){

            // Switch to the goals tab
            fm.beginTransaction().replace(R.id.content_frame, new ProgressFragment()).commit();

            setNewEntryVisibility(true);

            hideFloatingActionButton();
        } else if (id == R.id.nav_diary) {

            // Switch to the Profile tab
            fm.beginTransaction().replace(R.id.content_frame, new DiaryFragment()).commit();

            setNewEntryVisibility(true);

            hideFloatingActionButton();
        } else if (id == R.id.nav_analysis) {
            fm.beginTransaction().replace(R.id.content_frame, new AnalysisActivityFragment()).commit();
            hideFloatingActionButton();
            setNewEntryVisibility(false);
        } else if (id == R.id.nav_recipe_keep){
            fm.beginTransaction().replace(R.id.content_frame, new RecipeKeepFragment()).commit();
            setNewEntryVisibility(false);
            hideFloatingActionButton();
        }
//        else if (id == R.id.nav_search){
//
//            // Switch to the goals tab
//            fm.beginTransaction().replace(R.id.content_frame, new SearchFragment()).commit();
//
//            setNewEntryVisibility(false);
//
//            hideFloatingActionButton();
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Ask the user how they would like to add an entry.
     */
    public void presentUserOptions() {
        android.support.v4.app.DialogFragment newFrag = MyListAlertDialogFragment.newInstance(R.string.main_activity_new_entry_options_title);
        newFrag.show(getSupportFragmentManager(), "dialog");
    }

    /**
     * Set the title of the activity.
     * @param title the title of the page
     */
    public void setActionBarTitle(String title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException e){
            Log.d(TAG, "setActionBarTitle(): " + e);
        }
    }

    /**
     * Show the floating action button.
     */
    public void showFloatingActionButton() {
        Animation rollLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roll_from_right);
        floatingActionButton.setAnimation(rollLeft);
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the floating action button.
     */
    public void hideFloatingActionButton() {
        if(floatingActionButton.isShown()) {
            Animation rollRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roll_to_right);
            floatingActionButton.setAnimation(rollRight);
            floatingActionButton.setVisibility(View.GONE);
        }
    }

    /**
     * Set the menu item for a new entry to show or hide.
     * @param visibility visible or gone.
     */
    public void setNewEntryVisibility(boolean visibility) {
        menu.findItem(R.id.action_new_entry).setVisible(visibility);
    }

    /**
     * Get the users name from the database.
     */
    public void getTheUsersNameFromDatabase() {
        Cursor res = db.getUser();

        if(res == null) {
            startActivity(new Intent(getApplicationContext(), ProfileSetUp.class));
            finish();
        } else {
            while (res.moveToNext()) {
                usersName = res.getString(1);
            }
        }
    }

    /**
     * Set the user's name into the drawers header.
     */
    public void setNameNavigationHeader() {
        getTheUsersNameFromDatabase();
        navigationBarNameSpaceTextView.setText(usersName);
    }

    /**
     * Get the screen size.
     * @return screen size in inches.
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

    /**
     * Check if an application is installed on the device or not.
     * @param uri package name of application to check.
     * @return true if installed.
     */
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
