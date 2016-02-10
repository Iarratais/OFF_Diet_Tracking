package com.karl.models;


public class Goals {

    private String calories;
    private String fat;
    private String saturatedFat;
    private String sodium;
    private String carbohydrates;
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

    public String getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(String saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public String getSodium() {
        return sodium;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = carbohydrates;
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
                ", saturatedFat=" + saturatedFat +
                ", sodium=" + sodium +
                ", carbohydrates=" + carbohydrates +
                ", protein=" + protein +
                ", sugar=" + sugar +
                ", salt=" + salt +
                '}';
    }
}
