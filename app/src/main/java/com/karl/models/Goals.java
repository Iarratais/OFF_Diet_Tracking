package com.karl.models;


public class Goals {

    private String calories;
    private String fat;
    private String sat_fat;
    private String sodium;
    private String carbs;
    private String protein;
    private String sugar;
    private String salt;

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getSat_fat() {
        return sat_fat;
    }

    public void setSat_fat(String sat_fat) {
        this.sat_fat = sat_fat;
    }

    public String getSodium() {
        return sodium;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void loadGoals() {
        // This needs to be filled in with the database connection and gathering information
        // about the goals set by the user
    }

    @Override
    public String toString() {
        return "Goals{" +
                "calories=" + calories +
                ", fat=" + fat +
                ", sat_fat=" + sat_fat +
                ", sodium=" + sodium +
                ", carbs=" + carbs +
                ", protein=" + protein +
                ", sugar=" + sugar +
                ", salt=" + salt +
                '}';
    }
}
