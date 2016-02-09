package com.karl.models;

import java.util.Calendar;

/**
 * This class represents the attributes of the Food DTO
 */
public class Food {

    private String date = null;
    private String id = null;
    private String name = null;
    private String barcode_number = null;
    private String calories = null;
    private String fats = null;
    private String saturated_fat = null;
    private String carbs = null;
    private String sugar = null;
    private String protein = null;
    private String salt = null;
    private String sodium = null;
    private String serving_size = null;

    public Food() {
    }

    public Food(String name, String barcode_number, String calories, String fats, String saturated_fat, String salt, String sodium, String carbs, String sugar, String protein) {
        this.date = getCurrentDate();
        this.name = name;
        this.barcode_number = barcode_number;
        this.calories = calories;
        this.fats = fats;
        this.saturated_fat = saturated_fat;
        this.carbs = carbs;
        this.sugar = sugar;
        this.protein = protein;
        this.salt = salt;
        this.sodium = sodium;
    }

    public Food(String id, String name, String barcode_number, String calories, String fats, String saturated_fat, String salt, String sodium, String carbs, String sugar, String protein) {
        this.date = getCurrentDate();
        this.id = id;
        this.name = name;
        this.barcode_number = barcode_number;
        this.calories = calories;
        this.fats = fats;
        this.saturated_fat = saturated_fat;
        this.carbs = carbs;
        this.sugar = sugar;
        this.protein = protein;
        this.salt = salt;
        this.sodium = sodium;
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

    public String getBarcode_number() {
        return barcode_number;
    }

    public void setBarcode_number(String barcode_number) {
        this.barcode_number = barcode_number;
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

    public String getServing_size() {
        return serving_size;
    }

    public void setServing_size(String serving_size) {
        this.serving_size = serving_size;
    }

    @Override
    public String toString() {
        return "Food{" +
                "date='" + date + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", barcode_number='" + barcode_number + '\'' +
                ", calories='" + calories + '\'' +
                ", fats='" + fats + '\'' +
                ", saturated_fat='" + saturated_fat + '\'' +
                ", carbs='" + carbs + '\'' +
                ", sugar='" + sugar + '\'' +
                ", protein='" + protein + '\'' +
                ", salt='" + salt + '\'' +
                ", sodium='" + sodium + '\'' +
                ", serving_size='" + serving_size + '\'' +
                '}';
    }

    private String getCurrentDate() {
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

        System.out.println("Date: " + day + "-" + month + "-" + year);
        return day + month + year;
    }
}
