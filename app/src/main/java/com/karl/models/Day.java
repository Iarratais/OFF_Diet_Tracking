package com.karl.models;

/**
 * Created by Karl on 05/02/2016.
 */
public class Day {

    private String date = null;
    private String id = null;
    private String name = null;
    private String calories = null;
    private String fats = null;
    private String saturated_fat = null;
    private String carbs = null;
    private String sugar = null;
    private String protein = null;
    private String salt = null;
    private String sodium = null;

    public Day() {
    }

    public Day(String date, String name, String calories, String fats, String saturated_fat, String carbs, String sugar, String protein, String sodium, String salt, String id) {
        this.date = date;
        this.name = name;
        this.calories = calories;
        this.fats = fats;
        this.saturated_fat = saturated_fat;
        this.carbs = carbs;
        this.sugar = sugar;
        this.protein = protein;
        this.sodium = sodium;
        this.salt = salt;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getSaturated_fat() {
        return saturated_fat;
    }

    public void setSaturated_fat(String saturated_fat) {
        this.saturated_fat = saturated_fat;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSodium() {
        return sodium;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }

    @Override
    public String toString() {
        return "Day{" +
                "date='" + date + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", calories='" + calories + '\'' +
                ", fats='" + fats + '\'' +
                ", saturated_fat='" + saturated_fat + '\'' +
                ", carbs='" + carbs + '\'' +
                ", sugar='" + sugar + '\'' +
                ", protein='" + protein + '\'' +
                ", salt='" + salt + '\'' +
                ", sodium='" + sodium + '\'' +
                '}';
    }
}
