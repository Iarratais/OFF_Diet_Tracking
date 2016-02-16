package com.karl.fyp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.karl.models.Food;

import java.util.ArrayList;

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

        runSplash();
    }

    /**
     * Check if the application is being launched for the first time
     * @return true if first launch, false if not
     */
    public boolean checkIfFirstLaunch(){

        // Get user to create profile
        // Set the default goals for the user
        return false;
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

    public void transferData(){
        MySQLiteHelper db = new MySQLiteHelper(this);

        Cursor todayRes = db.returnTodaysEntries();
        Cursor todayStatsRes = db.returnTodayStatsEntries();

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
                /**
                 * Check if the ids match so that the food gets the right information put into the object
                 */
                if(String.valueOf(todayStatsRes.getInt(0)).equals(foods.get(i).getId())){
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
                } // End if(String.valueOf(todayStatsRes.getInt(0)).equals(foods.get(i).getId()))
            } // End while(todayStatsRes.moveToNext())
        }

        calculateTotals(foods);
    } // End transferData()

    public void calculateTotals(ArrayList<Food> foods){
        ArrayList<String> dateRange = new ArrayList<>();
        ArrayList<Food> totals = new ArrayList<>();

        dateRange.add(foods.get(0).getDate());

        if(foods.size() >= 1) {
            for (int i = 0; i < foods.size(); i++) {

                totals.add(new Food());

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

                        // Set calories
                        if (totals.get(j).getCalories() == null)
                            totals.get(j).setCalories("0");
                        Double calories1 = Double.parseDouble(foods.get(i).getCalories());
                        Double calories2 = Double.parseDouble(totals.get(j).getCalories());
                        Double calories = calories1 + calories2;
                        totals.get(j).setCalories(String.valueOf(calories));

                        // Set fats
                        if (totals.get(j).getFats() == null)
                            totals.get(j).setFats("0");
                        Double fats1 = Double.parseDouble(foods.get(i).getFats());
                        Double fats2 = Double.parseDouble(totals.get(j).getFats());
                        Double fats = fats1 + fats2;
                        totals.get(j).setFats(String.valueOf(fats));

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
                        totals.get(j).setCarbohydrates(String.valueOf(carbs));

                        // Set sugars
                        if (totals.get(j).getSugar() == null)
                            totals.get(j).setSugar("0");
                        Double sugars1 = Double.parseDouble(foods.get(i).getSugar());
                        Double sugars2 = Double.parseDouble(totals.get(j).getSugar());
                        Double sugars = sugars1 + sugars2;
                        totals.get(j).setSugar(String.valueOf(sugars));

                        // Set proteins
                        if (totals.get(j).getProtein() == null)
                            totals.get(j).setProtein("0");
                        Double proteins1 = Double.parseDouble(foods.get(i).getProtein());
                        Double proteins2 = Double.parseDouble(totals.get(j).getProtein());
                        Double proteins = proteins1 + proteins2;
                        totals.get(j).setProtein(String.valueOf(proteins));

                        // Set salt
                        if (totals.get(j).getSalt() == null)
                            totals.get(j).setSalt("0");
                        Double salts1 = Double.parseDouble(foods.get(i).getSalt());
                        Double salts2 = Double.parseDouble(totals.get(j).getSalt());
                        Double salts = salts1 + salts2;
                        totals.get(j).setSalt(String.valueOf(salts));

                        // Set sodium
                        if (totals.get(j).getSodium() == null)
                            totals.get(j).setSodium("0");
                        Double sodiums1 = Double.parseDouble(foods.get(i).getSodium());
                        Double sodiums2 = Double.parseDouble(totals.get(j).getSodium());
                        Double sodiums = sodiums1 + sodiums2;
                        totals.get(j).setSodium(String.valueOf(sodiums));

                        Log.d(TAG, totals.get(j).toString());
                    } else {
                        dateRange.add(foods.get(i).getDate());
                        totals.add(foods.get(i));
                        Log.d(TAG, totals.get(j).toString());
                    }
                }
            }
        }
    }
}
