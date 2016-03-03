package com.karl.analysis;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.karl.fyp.R;
import com.karl.models.Day;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Analysis model.
 * Created by Karl on 03/03/2016.
 */
public class Analyse {

    private static final String TAG = "Analyse";

    private String[] days;

    private ArrayList<Day> day_information;

    private Context context;

    public Analyse(Context context, ArrayList<Day> day_information){
        this.context = context;
        this.day_information = day_information;
        days = makeDaysArray();
    }

    /**
     * Get the day with the most calories.
     * @param day_information arraylist: information from database.
     * @return String: day with the most calories.
     */
    public String getMostCalories(){
        int max_calories = 0;
        String date = "";
        for(Day day : day_information){
            String calories = day.getCalories();
            if(Integer.valueOf(calories) > max_calories){
                max_calories = Integer.valueOf(calories);
                date = day.getDate();
            }
        }
        return String.valueOf(max_calories) + "|" + date;
    }

    /**
     * Get the day with the most calories from the previous month.
     * @param day_information arraylist: information from database.
     * @return string: day that has the most calories.
     */
    public String getDayMostCalories() {

        // One total for each day of the week
        Double[] totals = new Double[7];

        for(int i = 0; i < 7; i++){
            totals[i] = 0.0;
        }

        for(Day day : day_information){
            String date = day.getDate();
            if(date.substring(0,3).equals(days[0].substring(0,3))){
                totals[0] += Double.valueOf(day.getCalories());
            } else if(date.substring(0,3).equals(days[1].substring(0,3))){
                totals[1] += Double.valueOf(day.getCalories());
            } else if(date.substring(0,3).equals(days[2].substring(0,3))){
                totals[2] += Double.valueOf(day.getCalories());
            } else if(date.substring(0,3).equals(days[3].substring(0,3))){
                totals[3] += Double.valueOf(day.getCalories());
            } else if(date.substring(0,3).equals(days[4].substring(0,3))){
                totals[4] += Double.valueOf(day.getCalories());
            } else if(date.substring(0,3).equals(days[5].substring(0,3))){
                totals[5] += Double.valueOf(day.getCalories());
            } else if(date.substring(0,3).equals(days[6].substring(0,3))){
                totals[6] += Double.valueOf(day.getCalories());
            }
        }

        Double max_total = 0.0;
        int day = 0;

        for(int i = 0; i < 7; i++){
            if(totals[i] > max_total){
                max_total = totals[i];
                day = i;
            }
        }

        return getFullDay(days[day]);
    }

    public String getMostFats(){
        double max_fats = 0.0;
        String date = "";
        for(Day day : day_information){
            String fats = day.getFats();
            if(Double.valueOf(fats) > max_fats){
                max_fats = Double.valueOf(fats);
                date = day.getDate();
            }
        }
        return String.valueOf(max_fats) + "|" + date;
    }

    public String getDayMostFats(){
        // One total for each day of the week
        Double[] totals = new Double[7];

        for(int i = 0; i < 7; i++){
            totals[i] = 0.0;
        }

        for(Day day : day_information){
            String date = day.getDate();
            if(date.substring(0,3).equals(days[0].substring(0,3))){
                totals[0] += Double.valueOf(day.getFats());
            } else if(date.substring(0,3).equals(days[1].substring(0,3))){
                totals[1] += Double.valueOf(day.getFats());
            } else if(date.substring(0,3).equals(days[2].substring(0,3))){
                totals[2] += Double.valueOf(day.getFats());
            } else if(date.substring(0,3).equals(days[3].substring(0,3))){
                totals[3] += Double.valueOf(day.getFats());
            } else if(date.substring(0,3).equals(days[4].substring(0,3))){
                totals[4] += Double.valueOf(day.getFats());
            } else if(date.substring(0,3).equals(days[5].substring(0,3))){
                totals[5] += Double.valueOf(day.getFats());
            } else if(date.substring(0,3).equals(days[6].substring(0,3))){
                totals[6] += Double.valueOf(day.getFats());
            }
        }

        Double max_total = 0.0;
        int day = 0;

        for(int i = 0; i < 7; i++){
            if(totals[i] > max_total){
                max_total = totals[i];
                day = i;
            }
        }

        return getFullDay(days[day]);
    }

    public String getMostSatFat(){
        double max_satfat = 0.0;
        String date = "";
        for(Day day : day_information){
            String satfat = day.getSaturated_fat();
            if(Double.valueOf(satfat) > max_satfat){
                max_satfat = Double.valueOf(satfat);
                date = day.getDate();
            }
        }
        return String.valueOf(max_satfat) + "|" + date;
    }

    public String getDayMostSatFat(){
        // One total for each day of the week
        Double[] totals = new Double[7];

        for(int i = 0; i < 7; i++){
            totals[i] = 0.0;
        }

        for(Day day : day_information){
            String date = day.getDate();
            if(date.substring(0,3).equals(days[0].substring(0,3))){
                totals[0] += Double.valueOf(day.getSaturated_fat());
            } else if(date.substring(0,3).equals(days[1].substring(0,3))){
                totals[1] += Double.valueOf(day.getSaturated_fat());
            } else if(date.substring(0,3).equals(days[2].substring(0,3))){
                totals[2] += Double.valueOf(day.getSaturated_fat());
            } else if(date.substring(0,3).equals(days[3].substring(0,3))){
                totals[3] += Double.valueOf(day.getSaturated_fat());
            } else if(date.substring(0,3).equals(days[4].substring(0,3))){
                totals[4] += Double.valueOf(day.getSaturated_fat());
            } else if(date.substring(0,3).equals(days[5].substring(0,3))){
                totals[5] += Double.valueOf(day.getSaturated_fat());
            } else if(date.substring(0,3).equals(days[6].substring(0,3))){
                totals[6] += Double.valueOf(day.getSaturated_fat());
            }
        }

        Double max_total = 0.0;
        int day = 0;

        for(int i = 0; i < 7; i++){
            if(totals[i] > max_total){
                max_total = totals[i];
                day = i;
            }
        }

        return getFullDay(days[day]);
    }

    public String getMostSalt(){
        double max_salt = 0.0;
        String date = "";
        for(Day day : day_information){
            String salt = day.getSalt();
            if(Double.valueOf(salt) > max_salt){
                max_salt = Double.valueOf(salt);
                date = day.getDate();
            }
        }
        return String.valueOf(max_salt) + "|" + date;
    }

    public String getDayMostSalt(){
        // One total for each day of the week
        Double[] totals = new Double[7];

        for(int i = 0; i < 7; i++){
            totals[i] = 0.0;
        }

        for(Day day : day_information){
            String date = day.getDate();
            if(date.substring(0,3).equals(days[0].substring(0,3))){
                totals[0] += Double.valueOf(day.getSalt());
            } else if(date.substring(0,3).equals(days[1].substring(0,3))){
                totals[1] += Double.valueOf(day.getSalt());
            } else if(date.substring(0,3).equals(days[2].substring(0,3))){
                totals[2] += Double.valueOf(day.getSalt());
            } else if(date.substring(0,3).equals(days[3].substring(0,3))){
                totals[3] += Double.valueOf(day.getSalt());
            } else if(date.substring(0,3).equals(days[4].substring(0,3))){
                totals[4] += Double.valueOf(day.getSalt());
            } else if(date.substring(0,3).equals(days[5].substring(0,3))){
                totals[5] += Double.valueOf(day.getSalt());
            } else if(date.substring(0,3).equals(days[6].substring(0,3))){
                totals[6] += Double.valueOf(day.getSalt());
            }
        }

        Double max_total = 0.0;
        int day = 0;

        for(int i = 0; i < 7; i++){
            if(totals[i] > max_total){
                max_total = totals[i];
                day = i;
            }
        }

        return getFullDay(days[day]);
    }

    public String getMostSodium(){
        double max_sodium = 0.0;
        String date = "";
        for(Day day : day_information){
            String sodium = day.getSodium();
            if(Double.valueOf(sodium) > max_sodium){
                max_sodium = Double.valueOf(sodium);
                date = day.getDate();
            }
        }
        return String.valueOf(max_sodium) + "|" + date;
    }

    public String getDayMostSodium(){
        // One total for each day of the week
        Double[] totals = new Double[7];

        for(int i = 0; i < 7; i++){
            totals[i] = 0.0;
        }

        for(Day day : day_information){
            String date = day.getDate();
            if(date.substring(0,3).equals(days[0].substring(0,3))){
                totals[0] += Double.valueOf(day.getSodium());
            } else if(date.substring(0,3).equals(days[1].substring(0,3))){
                totals[1] += Double.valueOf(day.getSodium());
            } else if(date.substring(0,3).equals(days[2].substring(0,3))){
                totals[2] += Double.valueOf(day.getSodium());
            } else if(date.substring(0,3).equals(days[3].substring(0,3))){
                totals[3] += Double.valueOf(day.getSodium());
            } else if(date.substring(0,3).equals(days[4].substring(0,3))){
                totals[4] += Double.valueOf(day.getSodium());
            } else if(date.substring(0,3).equals(days[5].substring(0,3))){
                totals[5] += Double.valueOf(day.getSodium());
            } else if(date.substring(0,3).equals(days[6].substring(0,3))){
                totals[6] += Double.valueOf(day.getSodium());
            }
        }

        Double max_total = 0.0;
        int day = 0;

        for(int i = 0; i < 7; i++){
            if(totals[i] > max_total){
                max_total = totals[i];
                day = i;
            }
        }

        return getFullDay(days[day]);
    }

    public String getMostCarbohydrates(){
        double max_carbohydrates = 0.0;
        String date = "";
        for(Day day : day_information){
            String carbohydrates = day.getCarbs();
            if(Double.valueOf(carbohydrates) > max_carbohydrates){
                max_carbohydrates = Double.valueOf(carbohydrates);
                date = day.getDate();
            }
        }
        return String.valueOf(max_carbohydrates) + "|" + date;
    }

    public String getDayMostCarbohydrates(){
        // One total for each day of the week
        Double[] totals = new Double[7];

        for(int i = 0; i < 7; i++){
            totals[i] = 0.0;
        }

        for(Day day : day_information){
            String date = day.getDate();
            if(date.substring(0,3).equals(days[0].substring(0,3))){
                totals[0] += Double.valueOf(day.getCarbs());
            } else if(date.substring(0,3).equals(days[1].substring(0,3))){
                totals[1] += Double.valueOf(day.getCarbs());
            } else if(date.substring(0,3).equals(days[2].substring(0,3))){
                totals[2] += Double.valueOf(day.getCarbs());
            } else if(date.substring(0,3).equals(days[3].substring(0,3))){
                totals[3] += Double.valueOf(day.getCarbs());
            } else if(date.substring(0,3).equals(days[4].substring(0,3))){
                totals[4] += Double.valueOf(day.getCarbs());
            } else if(date.substring(0,3).equals(days[5].substring(0,3))){
                totals[5] += Double.valueOf(day.getCarbs());
            } else if(date.substring(0,3).equals(days[6].substring(0,3))){
                totals[6] += Double.valueOf(day.getCarbs());
            }
        }

        Double max_total = 0.0;
        int day = 0;

        for(int i = 0; i < 7; i++){
            if(totals[i] > max_total){
                max_total = totals[i];
                day = i;
            }
        }

        return getFullDay(days[day]);
    }

    public String getMostSugar(){
        double max_sugar = 0.0;
        String date = "";
        for(Day day : day_information){
            String sugar = day.getSugar();
            if(Double.valueOf(sugar) > max_sugar){
                max_sugar = Double.valueOf(sugar);
                date = day.getDate();
            }
        }
        return String.valueOf(max_sugar) + "|" + date;
    }

    public String getDayMostSugar(){
        // One total for each day of the week
        Double[] totals = new Double[7];

        for(int i = 0; i < 7; i++){
            totals[i] = 0.0;
        }

        for(Day day : day_information){
            String date = day.getDate();
            if(date.substring(0,3).equals(days[0].substring(0,3))){
                totals[0] += Double.valueOf(day.getSugar());
            } else if(date.substring(0,3).equals(days[1].substring(0,3))){
                totals[1] += Double.valueOf(day.getSugar());
            } else if(date.substring(0,3).equals(days[2].substring(0,3))){
                totals[2] += Double.valueOf(day.getSugar());
            } else if(date.substring(0,3).equals(days[3].substring(0,3))){
                totals[3] += Double.valueOf(day.getSugar());
            } else if(date.substring(0,3).equals(days[4].substring(0,3))){
                totals[4] += Double.valueOf(day.getSugar());
            } else if(date.substring(0,3).equals(days[5].substring(0,3))){
                totals[5] += Double.valueOf(day.getSugar());
            } else if(date.substring(0,3).equals(days[6].substring(0,3))){
                totals[6] += Double.valueOf(day.getSugar());
            }
        }

        Double max_total = 0.0;
        int day = 0;

        for(int i = 0; i < 7; i++){
            if(totals[i] > max_total){
                max_total = totals[i];
                day = i;
            }
        }

        return getFullDay(days[day]);
    }

    public String getMostProtein(){
        double max_protein = 0.0;
        String date = "";
        for(Day day : day_information){
            String protein = day.getProtein();
            if(Double.valueOf(protein) > max_protein){
                max_protein = Double.valueOf(protein);
                date = day.getDate();
            }
        }
        return String.valueOf(max_protein) + "|" + date;
    }

    public String getDayMostProtein(){
        // One total for each day of the week
        Double[] totals = new Double[7];

        for(int i = 0; i < 7; i++){
            totals[i] = 0.0;
        }

        for(Day day : day_information){
            String date = day.getDate();
            if(date.substring(0,3).equals(days[0].substring(0,3))){
                totals[0] += Double.valueOf(day.getProtein());
            } else if(date.substring(0,3).equals(days[1].substring(0,3))){
                totals[1] += Double.valueOf(day.getProtein());
            } else if(date.substring(0,3).equals(days[2].substring(0,3))){
                totals[2] += Double.valueOf(day.getProtein());
            } else if(date.substring(0,3).equals(days[3].substring(0,3))){
                totals[3] += Double.valueOf(day.getProtein());
            } else if(date.substring(0,3).equals(days[4].substring(0,3))){
                totals[4] += Double.valueOf(day.getProtein());
            } else if(date.substring(0,3).equals(days[5].substring(0,3))){
                totals[5] += Double.valueOf(day.getProtein());
            } else if(date.substring(0,3).equals(days[6].substring(0,3))){
                totals[6] += Double.valueOf(day.getProtein());
            }
        }

        Double max_total = 0.0;
        int day = 0;

        for(int i = 0; i < 7; i++){
            if(totals[i] > max_total){
                max_total = totals[i];
                day = i;
            }
        }

        return getFullDay(days[day]);
    }

    /**
     * Returns the full spelling of the day when given a three letter code.
     * @param day string: three letter day code.
     * @return String: day of the week.
     */
    public String getFullDay(String day){
        if(day.equals(days[0])){
            return context.getString(R.string.monday);
        } else if(day.equals(days[1])){
            return context.getString(R.string.tuesday);
        } else if(day.equals(days[2])){
            return context.getString(R.string.wednesday);
        } else if(day.equals(days[3])){
            return context.getString(R.string.thursday);
        } else if(day.equals(days[4])){
            return context.getString(R.string.friday);
        } else if(day.equals(days[5])){
            return context.getString(R.string.saturday);
        } else if(day.equals(days[6])){
            return context.getString(R.string.sunday);
        }

        return null;
    }

    /**
     * Create the array holding the first three letters of each day.
     * @return String[]: days of the week.
     */
    public String[] makeDaysArray(){
        return new String[]{context.getString(R.string.monday).substring(0, 3).toUpperCase(), context.getString(R.string.tuesday).substring(0, 3).toUpperCase(),
                context.getString(R.string.wednesday).substring(0, 3).toUpperCase(), context.getString(R.string.thursday).substring(0, 3).toUpperCase(),
                context.getString(R.string.friday).substring(0, 3).toUpperCase(), context.getString(R.string.saturday).substring(0, 3).toUpperCase(),
                context.getString(R.string.sunday).substring(0, 3).toUpperCase()};
    }
}
