package android.karl.fyp;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.karl.fragments.AddToDatabaseFragment;
import android.karl.fragments.DiaryFragment;
import android.karl.fragments.GoalsFragment;
import android.karl.fragments.HistoryFragment;
import android.karl.fragments.LookupFragment;
import android.karl.fragments.ProfileFragment;
import android.karl.fragments.ProgressFragment;
import android.karl.fragments.SearchFragment;
import android.karl.fragments.TodayFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Floating action button
    FloatingActionButton fab;

    MySQLiteHelper db;

    String name_user;

    TextView navigation_bar_name_space;

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
                Intent i = new Intent(getApplicationContext(), NewManualEntryActivity.class);
                startActivity(i);
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

        // Fragments
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new TodayFragment()).commit();

//        db.createNewEntryToday(new Food("Dairygold", "000000", "57", "6.3", "2.3", "0", "0.1", "0", "0.199", "0.0787"));
//        db.createNewEntryToday(new Food("Nutella", "000000", "83", "4.75", "1.64", "8.59", "8.51", "0.9", "0.0141", "0.00555"));
//        db.createNewEntryToday(new Food("Royal Bacon", "000000", "504", "25.6", "12", "33.5", "8.08", "33.5", "1.6", "0.628"));
//        db.createNewEntryToday(new Food("Power Crunch", "000000", "375", "11.9", "0", "33.8", "0", "34.7", "0", "0"));
        Cursor res = db.returnTodaysEntries();
        System.out.println("Today entries: " + res.getCount());
        System.out.println("Res Column Count: " + res.getColumnCount());

        //db.createUser("Karl", "Male", "180", "190");
        //db.clearAllUser();
//        // Hide the history - needs to only be done when there is no history to show
//        MenuItem item = navigationView.getMenu().getItem(1);
//        item.setVisible(false);

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

            // Show the FAB
            Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_up);
            fab.setAnimation(bottomUp);
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_history) {

            // Switch to the History tab
            fm.beginTransaction().replace(R.id.content_frame, new HistoryFragment()).commit();

            // Hide the FAB
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_lookup) {

            // Switch to the Lookup tab
            fm.beginTransaction().replace(R.id.content_frame, new LookupFragment()).commit();

            // Hide the FAB
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_addtodatabase){

            // Switch to the goals tab
            fm.beginTransaction().replace(R.id.content_frame, new AddToDatabaseFragment()).commit();

            // Hide the FAB
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_goals){

            // Switch to the goals tab
            fm.beginTransaction().replace(R.id.content_frame, new GoalsFragment()).commit();

            // Hide the FAB
            fab.setVisibility(View.GONE);
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

            // Hide the FAB
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_progress){

            // Switch to the goals tab
            fm.beginTransaction().replace(R.id.content_frame, new ProgressFragment()).commit();

            // Hide the FAB
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_profile) {

            // Switch to the Profile tab
            fm.beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();

            // Hide the FAB
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_diary) {

            // Switch to the Profile tab
            fm.beginTransaction().replace(R.id.content_frame, new DiaryFragment()).commit();

            // Hide the FAB
            fab.setVisibility(View.GONE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    This allows the fragments to set their titles
    @param String : title
     */
    public void setActionBarTitle(String title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException e){
            System.out.println("MainActivity - setActionBarTitle(): " + e);
        }
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
}
