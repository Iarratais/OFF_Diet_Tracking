package com.karl.analysis;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.karl.fyp.MySQLiteHelper;
import com.karl.models.Day;
import com.karl.models.Goals;

import java.util.ArrayList;

/**
 * GoalAnalysis
 *
 * Created by Karl on 08/04/2016.
 *
 * This class compares a set of information against the users personalised targets.
 */
public class GoalAnalysis {
    private int calories_broken = 0;
    private int fats_broken = 0;
    private int saturated_fats_broken = 0;
    private int salt_broken = 0;
    private int sodium_broken = 0;
    private int carbohydrates_broken = 0;
    private int sugar_broken = 0;
    private int protein_broken = 0;

    private final static String TAG = "GoalsAnalysis";

    ArrayList<Day> day_history;
    Context context;

    public GoalAnalysis(ArrayList<Day> day_history, Context context) {
        this.day_history = day_history;
        this.context = context;

        testValues(getGoals());
    }

    /**
     * Retrieve the goals from the database.
     * @return Goals    the users goals.
     */
    public Goals getGoals(){
        MySQLiteHelper db = new MySQLiteHelper(context);
        Goals resGoals = new Goals();
        Cursor res = db.getGoals();
        if(res.getCount() != 0){
            while(res.moveToNext()){
                for(int i = 0; i < res.getColumnCount(); i++){
                    resGoals.setCalories(res.getString(1));
                    resGoals.setFat(res.getString(2));
                    resGoals.setSaturatedFat(res.getString(3));
                    resGoals.setSalt(res.getString(4));
                    resGoals.setSodium(res.getString(5));
                    resGoals.setCarbohydrates(res.getString(6));
                    resGoals.setSugar(res.getString(7));
                    resGoals.setProtein(res.getString(8));
                }
            }
        }
        return resGoals;
    }

    public void testValues(Goals goals){
        for(int i = 0; i < day_history.size(); i++) {

            // Check the calories.
            if(Double.parseDouble(day_history.get(i).getCalories()) > Double.parseDouble(goals.getCalories())){
                calories_broken++;
            }

            // Check the fats.
            if(Double.parseDouble(day_history.get(i).getFats()) > Double.parseDouble(goals.getFat())){
                fats_broken++;
            }

            // Check the saturated fats.
            if(Double.parseDouble(day_history.get(i).getSaturated_fat()) > Double.parseDouble(goals.getSaturatedFat())){
                saturated_fats_broken++;
            }

            // Check the salts.
            if(Double.parseDouble(day_history.get(i).getSalt()) > Double.parseDouble(goals.getSalt())){
                salt_broken++;
            }

            // Check the sodium.
            if(Double.parseDouble(day_history.get(i).getSodium()) > Double.parseDouble(goals.getSodium())){
                sodium_broken++;
            }

            // Check the carbohydrates.
            if(Double.parseDouble(day_history.get(i).getCarbs()) > Double.parseDouble(goals.getCarbohydrates())){
                carbohydrates_broken++;
            }

            // Check the sugars.
            if(Double.parseDouble(day_history.get(i).getSugar()) > Double.parseDouble(goals.getSugar())){
                sugar_broken++;
            }

            // Check the sodium.
            if(Double.parseDouble(day_history.get(i).getSodium()) > Double.parseDouble(goals.getSodium())) {
                sodium_broken++;
            }
        }
    }

    @Override
    public String toString() {
        return "GoalAnalysis{" +
                "calories_broken=" + calories_broken +
                ", fats_broken=" + fats_broken +
                ", saturated_fats_broken=" + saturated_fats_broken +
                ", salt_broken=" + salt_broken +
                ", sodium_broken=" + sodium_broken +
                ", carbohydrates_broken=" + carbohydrates_broken +
                ", sugar_broken=" + sugar_broken +
                ", protein_broken=" + protein_broken +
                '}';
    }

    public int getCalories_broken() {
        return calories_broken;
    }

    public void setCalories_broken(int calories_broken) {
        this.calories_broken = calories_broken;
    }

    public int getFats_broken() {
        return fats_broken;
    }

    public void setFats_broken(int fats_broken) {
        this.fats_broken = fats_broken;
    }

    public int getSaturated_fats_broken() {
        return saturated_fats_broken;
    }

    public void setSaturated_fats_broken(int saturated_fats_broken) {
        this.saturated_fats_broken = saturated_fats_broken;
    }

    public int getSalt_broken() {
        return salt_broken;
    }

    public void setSalt_broken(int salt_broken) {
        this.salt_broken = salt_broken;
    }

    public int getSodium_broken() {
        return sodium_broken;
    }

    public void setSodium_broken(int sodium_broken) {
        this.sodium_broken = sodium_broken;
    }

    public int getCarbohydrates_broken() {
        return carbohydrates_broken;
    }

    public void setCarbohydrates_broken(int carbohydrates_broken) {
        this.carbohydrates_broken = carbohydrates_broken;
    }

    public int getSugar_broken() {
        return sugar_broken;
    }

    public void setSugar_broken(int sugar_broken) {
        this.sugar_broken = sugar_broken;
    }

    public int getProtein_broken() {
        return protein_broken;
    }

    public void setProtein_broken(int protein_broken) {
        this.protein_broken = protein_broken;
    }
}
