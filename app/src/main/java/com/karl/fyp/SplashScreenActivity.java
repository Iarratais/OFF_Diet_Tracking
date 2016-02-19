package com.karl.fyp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.karl.models.Food;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SplashScreenActivity extends Activity {

    // Splash screen timer
    private static double timer = 0.2;                           // Put in seconds value here
    private static int SPLASH_TIME_OUT = (int)(timer * 1000);
    private static String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        transferData();

        checkIfFirstLaunch();
    }

    /**
     * Check if the application is being launched for the first time
     * @return true if first launch, false if not
     */
    public void checkIfFirstLaunch(){

        SharedPreferences prefs = this.getSharedPreferences("com.karl.fyp", Context.MODE_PRIVATE);
        boolean isFirst = prefs.getBoolean("isFirst", true);

        MySQLiteHelper db = new MySQLiteHelper(this);

        if(isFirst){
            db.clearAllUser();
            db.clearGoals();

            startActivity(new Intent(getApplicationContext(), ProfileSetUp.class));
            finish();
        } else {
            runSplash();
        }
    }

    /**
     * Run the splash screen.
     */
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

    /**
     * This is used to find and to transfer data from the today and the today stats tables
     * and transfer them into the history table while calculating the totals of each
     * nutrient.
     */
    public void transferData(){
        MySQLiteHelper db = new MySQLiteHelper(this);

        Cursor todayRes = db.returnAllTodayEntries();
        Cursor todayStatsRes = db.returnAllTodaysStats();

        ArrayList<Food> foods = new ArrayList<>();

        if(todayRes ==  null) {
            Log.d(TAG, "There is nothing in the today table");
        } else if (todayStatsRes == null) {
            Log.d(TAG, "There is nothing in the today stats table");
        } else {

            // 4 columns in todayRes
            while(todayRes.moveToNext()) {
                int today_id = todayRes.getInt(0);
                String today_date = todayRes.getString(1);
                String food_name = todayRes.getString(2);
                String barcode_number = todayRes.getString(3);

                Food f = new Food();
                f.setId(String.valueOf(today_id));
                f.setDate(today_date);
                f.setName(food_name);
                f.setBarcode_number(barcode_number);

                foods.add(f);
            } // End while(todayRes.moveToNext())

            int i = 0;

            // 10 columns in todayStatsRes
            while(todayStatsRes.moveToNext()){
                String calories = todayStatsRes.getString(2);
                String fat = todayStatsRes.getString(3);
                String sat_fat = todayStatsRes.getString(4);
                String carbs = todayStatsRes.getString(5);
                String sugar = todayStatsRes.getString(6);
                String protein = todayStatsRes.getString(7);
                String salt = todayStatsRes.getString(8);
                String sodium = todayStatsRes.getString(9);

                foods.get(i).setCalories(calories);
                foods.get(i).setFats(fat);
                foods.get(i).setSaturated_fat(sat_fat);
                foods.get(i).setCarbohydrates(carbs);
                foods.get(i).setSugar(sugar);
                foods.get(i).setProtein(protein);
                foods.get(i).setSalt(salt);
                foods.get(i).setSodium(sodium);

                i++;
            } // End while(todayStatsRes.moveToNext())
        }

        for(int x = 0; x < foods.size(); x++) {
            Log.d(TAG, "Transfer data: " + foods.get(x).toString());
        }

        if(foods.size() > 0) {
            calculateTotals(foods);
        } else {
            Log.d(TAG, "There is no food items loaded from the system - not proceeding to calculating totals");

        }
    } // End transferData()

    /**
     * Calculate the totals and pass them to the history database
     * @param foods from the database.
     */
    public void calculateTotals(ArrayList<Food> foods){
        ArrayList<String> dateRange = new ArrayList<>();
        ArrayList<Food> totals = new ArrayList<>();

        dateRange.add(foods.get(0).getDate());
        totals.add(foods.get(0));

        final DecimalFormat df = new DecimalFormat("#.###");

        if(foods.size() >= 1) {
            for (int i = 0; i < foods.size(); i++) {

                boolean makeNew = false;

                // Check if any of the values is null
                if(foods.get(i).getCalories() == null)
                    foods.get(i).setCalories("0");
                if(foods.get(i).getFats() == null)
                    foods.get(i).setFats("0");
                if(foods.get(i).getSaturated_fat() == null)
                    foods.get(i).setSaturated_fat("0");
                if(foods.get(i).getCarbohydrates() == null)
                    foods.get(i).setCarbohydrates("0");
                if(foods.get(i).getSugar() == null)
                    foods.get(i).setSugar("0");
                if(foods.get(i).getProtein() == null)
                    foods.get(i).setProtein("0");
                if(foods.get(i).getSalt() == null)
                    foods.get(i).setSalt("0");
                if(foods.get(i).getSodium() == null)
                    foods.get(i).setSodium("0");

                for (int j = 0; j < dateRange.size(); j++) {
                    if (foods.get(i).getDate().equals(dateRange.get(j))) {
                        totals.get(j).setDate(foods.get(i).getDate());
                        totals.get(j).setId(foods.get(i).getId());

                        // Set calories
                        if (totals.get(j).getCalories() == null)
                            totals.get(j).setCalories("0");
                        Double calories1 = Double.parseDouble(foods.get(i).getCalories());
                        Double calories2 = Double.parseDouble(totals.get(j).getCalories());
                        Double calories = calories1 + calories2;
                        totals.get(j).setCalories(String.valueOf(df.format(calories)));

                        // Set fats
                        if (totals.get(j).getFats() == null)
                            totals.get(j).setFats("0");
                        Double fats1 = Double.parseDouble(foods.get(i).getFats());
                        Double fats2 = Double.parseDouble(totals.get(j).getFats());
                        Double fats = fats1 + fats2;
                        totals.get(j).setFats(String.valueOf(df.format(fats)));

                        // Set saturated fats
                        if (totals.get(j).getSaturated_fat() == null)
                            totals.get(j).setSaturated_fat("0");
                        Double satfats1 = Double.parseDouble(foods.get(i).getSaturated_fat());
                        Double satfats2 = Double.parseDouble(totals.get(j).getSaturated_fat());
                        Double satfats = satfats1 + satfats2;
                        totals.get(j).setSaturated_fat(String.valueOf(satfats));

                        // Set carbohydrates
                        if (totals.get(j).getCarbohydrates() == null)
                            totals.get(j).setCarbohydrates("0");
                        Double carbs1 = Double.parseDouble(foods.get(i).getCarbohydrates());
                        Double carbs2 = Double.parseDouble(totals.get(j).getCarbohydrates());
                        Double carbs = carbs1 + carbs2;
                        totals.get(j).setCarbohydrates(String.valueOf(df.format(carbs)));

                        // Set sugars
                        if (totals.get(j).getSugar() == null)
                            totals.get(j).setSugar("0");
                        Double sugars1 = Double.parseDouble(foods.get(i).getSugar());
                        Double sugars2 = Double.parseDouble(totals.get(j).getSugar());
                        Double sugars = sugars1 + sugars2;
                        totals.get(j).setSugar(String.valueOf(df.format(sugars)));

                        // Set proteins
                        if (totals.get(j).getProtein() == null)
                            totals.get(j).setProtein("0");
                        Double proteins1 = Double.parseDouble(foods.get(i).getProtein());
                        Double proteins2 = Double.parseDouble(totals.get(j).getProtein());
                        Double proteins = proteins1 + proteins2;
                        totals.get(j).setProtein(String.valueOf(df.format(proteins)));

                        // Set salt
                        if (totals.get(j).getSalt() == null)
                            totals.get(j).setSalt("0");
                        Double salts1 = Double.parseDouble(foods.get(i).getSalt());
                        Double salts2 = Double.parseDouble(totals.get(j).getSalt());
                        Double salts = salts1 + salts2;
                        totals.get(j).setSalt(String.valueOf(df.format(salts)));

                        // Set sodium
                        if (totals.get(j).getSodium() == null)
                            totals.get(j).setSodium("0");
                        Double sodiums1 = Double.parseDouble(foods.get(i).getSodium());
                        Double sodiums2 = Double.parseDouble(totals.get(j).getSodium());
                        Double sodiums = sodiums1 + sodiums2;
                        totals.get(j).setSodium(String.valueOf(df.format(sodiums)));

                        makeNew = false;
                    } else {
                        makeNew = true;
                    }
                }

                if(makeNew) {
                    dateRange.add(foods.get(i).getDate());
                    Log.d(TAG, "Date added to dateRange, new size: " + dateRange.size());

                    totals.add(new Food());

                    int place = dateRange.size() - 1;

                    totals.get(place).setDate(foods.get(i).getDate());
                    totals.get(place).setId(foods.get(i).getId());

                    // Set calories
                    if (totals.get(place).getCalories() == null)
                        totals.get(place).setCalories("0");
                    Double calories1 = Double.parseDouble(foods.get(i).getCalories());
                    Double calories2 = Double.parseDouble(totals.get(place).getCalories());
                    Double calories = calories1 + calories2;
                    totals.get(place).setCalories(String.valueOf(df.format(calories)));

                    // Set fats
                    if (totals.get(place).getFats() == null)
                        totals.get(place).setFats("0");
                    Double fats1 = Double.parseDouble(foods.get(i).getFats());
                    Double fats2 = Double.parseDouble(totals.get(place).getFats());
                    Double fats = fats1 + fats2;
                    totals.get(place).setFats(String.valueOf(df.format(fats)));

                    // Set saturated fats
                    if (totals.get(place).getSaturated_fat() == null)
                        totals.get(place).setSaturated_fat("0");
                    Double satfats1 = Double.parseDouble(foods.get(i).getSaturated_fat());
                    Double satfats2 = Double.parseDouble(totals.get(place).getSaturated_fat());
                    Double satfats = satfats1 + satfats2;
                    totals.get(place).setSaturated_fat(String.valueOf(df.format(satfats)));

                    // Set carbohydrates
                    if (totals.get(place).getCarbohydrates() == null)
                        totals.get(place).setCarbohydrates("0");
                    Double carbs1 = Double.parseDouble(foods.get(i).getCarbohydrates());
                    Double carbs2 = Double.parseDouble(totals.get(place).getCarbohydrates());
                    Double carbs = carbs1 + carbs2;
                    totals.get(place).setCarbohydrates(String.valueOf(df.format(carbs)));

                    // Set sugars
                    if (totals.get(place).getSugar() == null)
                        totals.get(place).setSugar("0");
                    Double sugars1 = Double.parseDouble(foods.get(i).getSugar());
                    Double sugars2 = Double.parseDouble(totals.get(place).getSugar());
                    Double sugars = sugars1 + sugars2;
                    totals.get(place).setSugar(String.valueOf(df.format(sugars)));

                    // Set proteins
                    if (totals.get(place).getProtein() == null)
                        totals.get(place).setProtein("0");
                    Double proteins1 = Double.parseDouble(foods.get(i).getProtein());
                    Double proteins2 = Double.parseDouble(totals.get(place).getProtein());
                    Double proteins = proteins1 + proteins2;
                    totals.get(place).setProtein(String.valueOf(df.format(proteins)));

                    // Set salt
                    if (totals.get(place).getSalt() == null)
                        totals.get(place).setSalt("0");
                    Double salts1 = Double.parseDouble(foods.get(i).getSalt());
                    Double salts2 = Double.parseDouble(totals.get(place).getSalt());
                    Double salts = salts1 + salts2;
                    totals.get(place).setSalt(String.valueOf(df.format(salts)));

                    // Set sodium
                    if (totals.get(place).getSodium() == null)
                        totals.get(place).setSodium("0");
                    Double sodiums1 = Double.parseDouble(foods.get(i).getSodium());
                    Double sodiums2 = Double.parseDouble(totals.get(place).getSodium());
                    Double sodiums = sodiums1 + sodiums2;
                    totals.get(place).setSodium(String.valueOf(df.format(sodiums)));
                }
            }
        }

        // Create the entries in the history
        MySQLiteHelper db = new MySQLiteHelper(this);

        int amountMoved = 0;
        for(int i = 0; i < dateRange.size(); i++){
            if(!totals.get(i).getDate().equals(getDate())) {
                db.createHistoryEntry(totals.get(i));
                amountMoved++;
                db.clearToday(totals.get(i).getDate());
                db.clearTodayStats(totals.get(i).getDate());
            }
        }
        Log.d(TAG, amountMoved + " item(s) moved to history");
    }

    /**
     * Get the current date.
     * @return String with todays date.
     */
    public String getDate(){
        Calendar c = Calendar.getInstance();
        String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(c.get(Calendar.MONTH) + 1);
        String year = Integer.toString(c.get(Calendar.YEAR));

        if(day.length() < 2) {
            String temp = day;
            day = "0" + temp;
        }
        if(month.length() < 2) {
            String temp = month;
            month = "0" + temp;
        }

        Log.d(TAG, "Date: " + day + "-" + month + "-" + year);
        return day + month + year;
    }
}
