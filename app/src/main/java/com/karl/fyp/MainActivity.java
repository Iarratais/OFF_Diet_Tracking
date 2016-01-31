package com.karl.fyp;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import com.karl.fragments.AddToDatabaseFragment;
import com.karl.fragments.DiaryFragment;
import com.karl.fragments.GoalsFragment;
import com.karl.fragments.HistoryFragment;
import com.karl.fragments.LookupFragment;
import com.karl.fragments.MyListAlertDialogFragment;
import com.karl.fragments.ProfileFragment;
import com.karl.fragments.ProgressFragment;
import com.karl.fragments.SearchFragment;
import com.karl.fragments.TodayFragment;

import com.karl.fyp.R;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final double SMALL_DEVICE_THRESHOLD = 4.9;

    FloatingActionButton fab;

    MySQLiteHelper db;

    String name_user;

    TextView navigation_bar_name_space;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Floating action button
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEntryChoice();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        db = new MySQLiteHelper(this);

        getNameFromDatabase();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        navigation_bar_name_space = (TextView) header.findViewById(R.id.nav_bar_name);
        setNameHeader();

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new TodayFragment()).commit();

        Cursor res = db.returnTodaysEntries();
        System.out.println("Today entries: " + res.getCount());
        System.out.println("Res Column Count: " + res.getColumnCount());
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
            fab.setVisibility(View.GONE);
            MenuItem addButton = menu.findItem(R.id.action_new_entry);
            addButton.setVisible(true);
        } else {
            MenuItem addButton = menu.findItem(R.id.action_new_entry);
            addButton.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_new_entry:
                userEntryChoice();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Get the fragment manager
        FragmentManager fm = getFragmentManager();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today) {

            // Switch to the Today tab
            fm.beginTransaction().replace(R.id.content_frame, new TodayFragment()).commit();

            setNewEntryVisibility(true);

            showFAB();
        } else if (id == R.id.nav_history) {

            // Switch to the History tab
            fm.beginTransaction().replace(R.id.content_frame, new HistoryFragment()).commit();

            setNewEntryVisibility(true);

            hideFAB();
        } else if (id == R.id.nav_lookup) {

            // Switch to the Lookup tab
            fm.beginTransaction().replace(R.id.content_frame, new LookupFragment()).commit();

            setNewEntryVisibility(false);

            hideFAB();
        } else if (id == R.id.nav_addtodatabase){

            // Switch to the goals tab
            fm.beginTransaction().replace(R.id.content_frame, new AddToDatabaseFragment()).commit();

            setNewEntryVisibility(true);

            hideFAB();
        } else if (id == R.id.nav_goals){

            // Switch to the goals tab
            fm.beginTransaction().replace(R.id.content_frame, new GoalsFragment()).commit();

            setNewEntryVisibility(true);

            hideFAB();
        } else if (id == R.id.nav_settings) {

            // Switch to the settings
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_about) {

            // Switch to the about section
            Intent i = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_search){

            // Switch to the goals tab
            fm.beginTransaction().replace(R.id.content_frame, new SearchFragment()).commit();

            setNewEntryVisibility(false);

            hideFAB();
        } else if (id == R.id.nav_progress){

            // Switch to the goals tab
            fm.beginTransaction().replace(R.id.content_frame, new ProgressFragment()).commit();

            setNewEntryVisibility(true);

            hideFAB();
        } else if (id == R.id.nav_profile) {

            // Switch to the Profile tab
            fm.beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();

            setNewEntryVisibility(false);

            hideFAB();
        } else if (id == R.id.nav_diary) {

            // Switch to the Profile tab
            fm.beginTransaction().replace(R.id.content_frame, new DiaryFragment()).commit();

            setNewEntryVisibility(true);

            hideFAB();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void userEntryChoice() {
        DialogFragment newFrag = MyListAlertDialogFragment.newInstance(R.string.main_activity_new_entry_options_title);
        newFrag.show(getFragmentManager(), "dialog");
    }

    /**
     *
     * @param title the title of the page
     */
    public void setActionBarTitle(String title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException e){
            System.out.println("MainActivity - setActionBarTitle(): " + e);
        }
    }

    public void showFAB(){
        Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_up);
        fab.setAnimation(bottomUp);
        fab.setVisibility(View.VISIBLE);
    }

    public void hideFAB(){
        if(fab.isShown()){
            fab.setVisibility(View.GONE);
        }
    }

    public void setNewEntryVisibility(boolean visibility) {
        menu.findItem(R.id.action_new_entry).setVisible(visibility);
    }

    public void getNameFromDatabase() {
        Cursor res = db.getUser();

        while(res.moveToNext()) {
            name_user = res.getString(1);
        }
    }

    public void setNameHeader() {
        getNameFromDatabase();
        navigation_bar_name_space.setText(name_user);
    }

    /**
     * Get the screen size
     * @return screen size in inches
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
}
