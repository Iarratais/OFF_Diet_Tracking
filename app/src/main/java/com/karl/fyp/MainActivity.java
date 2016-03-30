package com.karl.fyp;

import android.content.Intent;
import android.content.SharedPreferences;
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
                presentActionsDialog();
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
                hideAddButton();
            }
        });
        navigationBarNameSpaceTextView = (TextView) header.findViewById(R.id.nav_bar_name);
        setNameNavigationHeader();

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new TodayFragment()).commit();

        // Check if Recipe Keep is installed on the device. If it is, hide the menu item.
        Boolean recipeKeepInstalled = isApplicationInstalled("com.karl.recipekeeper");
        if(recipeKeepInstalled){
            Menu menu = navigationView.getMenu();
            MenuItem target = menu.findItem(R.id.nav_recipe_keep);
            target.setVisible(false);
        }
    }

    /**
     * This method is used for testing purposes and clears all relevant information.
     */
    public void freshStart(){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("com.karl.fyp",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();

        db.wipeUserTable();
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

    private Menu mainMenu;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mainMenu = menu;

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

    public void hideAddButton(){
        MenuItem addButton = mainMenu.findItem(R.id.action_new_entry);
        addButton.setVisible(false);
        hideFloatingActionButton();
    }

    public void showAddButtons(){
        MenuItem addButton = mainMenu.findItem(R.id.action_new_entry);
        addButton.setVisible(true);
        showFloatingActionButton();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_new_entry:
                presentActionsDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        invalidateOptionsMenu();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today) {
            fm.beginTransaction().replace(R.id.content_frame, new TodayFragment()).commit();
        } else if (id == R.id.nav_history) {
            fm.beginTransaction().replace(R.id.content_frame, new HistoryFragment()).commit();
        } else if (id == R.id.nav_lookup) {
            fm.beginTransaction().replace(R.id.content_frame, new LookupFragment()).commit();
            hideFloatingActionButton();
        } else if (id == R.id.nav_addtodatabase){
            fm.beginTransaction().replace(R.id.content_frame, new AddToDatabaseFragment()).commit();
            hideFloatingActionButton();
        } else if (id == R.id.nav_goals){
            fm.beginTransaction().replace(R.id.content_frame, new GoalsFragment()).commit();
            hideFloatingActionButton();
        }else if (id == R.id.nav_about) {
            fm.beginTransaction().replace(R.id.content_frame, new AboutActivity()).commit();
            hideFloatingActionButton();
        } else if (id == R.id.nav_progress){
            fm.beginTransaction().replace(R.id.content_frame, new ProgressFragment()).commit();
        } else if (id == R.id.nav_diary) {
            fm.beginTransaction().replace(R.id.content_frame, new DiaryFragment()).commit();
        } else if (id == R.id.nav_analysis) {
            fm.beginTransaction().replace(R.id.content_frame, new AnalysisActivityFragment()).commit();
            hideFloatingActionButton();
        } else if (id == R.id.nav_recipe_keep){
            fm.beginTransaction().replace(R.id.content_frame, new RecipeKeepFragment()).commit();
            hideFloatingActionButton();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Present the user with the option of actions on what they can do.
     * Users can do the following actions: scan barcode, manual entry, and log their weight.
     */
    public void presentActionsDialog() {
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
     * Get the users name from the database to be displayed in the navigation drawer.
     */
    public void getTheUsersNameFromDatabase() {
        Cursor res = db.getUser();

        // If there is no information in the user table, ask the user to set up a new profile.
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
    private boolean isApplicationInstalled(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
