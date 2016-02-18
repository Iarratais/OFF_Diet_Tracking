package com.karl.examples;

import com.karl.fyp.MySQLiteHelper;
import com.karl.models.Food;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * This is just to populate the history database with dummy data
 */
public class HistorySamples {

    private static final String START_DATE = "1012016";
    private static final String START_DATE_FEB = "01022016";
    private static final int INCREMENT = 1000000;

    // Max
    private static final double FINAL_CALORIES = 2000f;
    private static final double FINAL_FAT = 65f;
    private static final double FINAL_SAT_FAT = 20f;
    private static final double FINAL_SODIUM = 2.4f;
    private static final double FINAL_CARBS = 300f;
    private static final double FINAL_PROTEIN = 50f;
    private static final double FINAL_SUGAR = 160f;
    private static final double FINAL_SALT = 5f;

    private static Food food;
    private static MySQLiteHelper db;

    public HistorySamples(MySQLiteHelper db){
        this.db = db;
    }

    public void setUpStatsJan(){

        final DecimalFormat df = new DecimalFormat("#.###");

        System.out.println("START DATE " + Integer.parseInt(START_DATE));
        System.out.println("INCREMENT " + INCREMENT);

        Random r = new Random();
        /**
         * Loop through 30 days
         */
        for(int i = Integer.parseInt(START_DATE); i <= 31012016; i += INCREMENT){

            food = new Food();

            // Set the date
            if(Integer.toString(i).length() < 8) {
                food.setDate("0" + Integer.toString(i));
            } else {
                food.setDate(Integer.toString(i));
            }

            // Set up calories
            double cals = (Math.random() * (int)FINAL_CALORIES);
            food.setCalories(Integer.toString((int) cals));

            // Set fats
            food.setFats(df.format(Math.random() * FINAL_FAT));

            // Set sat fats
            food.setSaturated_fat(df.format(Math.random() * FINAL_SAT_FAT));

            // Set sodium
            food.setSodium(df.format(Math.random() * FINAL_SODIUM));

            // Set carbs
            food.setCarbohydrates(df.format(Math.random() * FINAL_CARBS));

            // Set salt
            food.setSalt(df.format(Math.random() * FINAL_SALT));

            // Set sugar
            food.setSugar(df.format(Math.random() * FINAL_SUGAR));

            // Set protein
            food.setProtein(df.format(Math.random() * FINAL_PROTEIN));

            db.createHistoryEntry(food);
        }
    }

    public void setUpStatsFeb(){

        final DecimalFormat df = new DecimalFormat("#.###");

        System.out.println("START DATE " + Integer.parseInt(START_DATE));
        System.out.println("INCREMENT " + INCREMENT);

        Random r = new Random();
        /**
         * Loop through 28 days
         */
        for(int i = Integer.parseInt(START_DATE_FEB); i <= 28022016; i += INCREMENT){

            food = new Food();

            // Set the date
            if(Integer.toString(i).length() < 8) {
                food.setDate("0" + Integer.toString(i));
            } else {
                food.setDate(Integer.toString(i));
            }

            // Set up calories
            double cals = (Math.random() * (int)FINAL_CALORIES);
            food.setCalories(Integer.toString((int) cals));

            // Set fats
            food.setFats(df.format(Math.random() * FINAL_FAT));

            // Set sat fats
            food.setSaturated_fat(df.format(Math.random() * FINAL_SAT_FAT));

            // Set sodium
            food.setSodium(df.format(Math.random() * FINAL_SODIUM));

            // Set carbs
            food.setCarbohydrates(df.format(Math.random() * FINAL_CARBS));

            // Set salt
            food.setSalt(df.format(Math.random() * FINAL_SALT));

            // Set sugar
            food.setSugar(df.format(Math.random() * FINAL_SUGAR));

            // Set protein
            food.setProtein(df.format(Math.random() * FINAL_PROTEIN));

            db.createHistoryEntry(food);
        }
    }
}
