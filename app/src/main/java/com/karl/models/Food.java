package com.karl.models;

/**
 * This class represents the attributes of the Food DTO
 */
public class Food {

    private String id = null;
    private String name = null;
    private String barcode_number = null;
    private String calories = null;
    private String fats = null;
    private String sat_fats = null;
    private String carbs = null;
    private String sugar = null;
    private String protein = null;
    private String salt = null;
    private String sodium = null;

    public Food() {
    }

    public Food(String name, String barcode_number, String calories, String fats, String sat_fats, String salt, String sodium, String carbs, String sugar, String protein) {
        this.name = name;
        this.barcode_number = barcode_number;
        this.calories = calories;
        this.fats = fats;
        this.sat_fats = sat_fats;
        this.carbs = carbs;
        this.sugar = sugar;
        this.protein = protein;
        this.salt = salt;
        this.sodium = sodium;
    }

    public Food(String id, String name, String barcode_number, String calories, String fats, String sat_fats, String salt, String sodium, String carbs, String sugar, String protein) {
        this.id = id;
        this.name = name;
        this.barcode_number = barcode_number;
        this.calories = calories;
        this.fats = fats;
        this.sat_fats = sat_fats;
        this.carbs = carbs;
        this.sugar = sugar;
        this.protein = protein;
        this.salt = salt;
        this.sodium = sodium;
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

    public String getSat_fats() {
        return sat_fats;
    }

    public void setSat_fats(String sat_fats) {
        this.sat_fats = sat_fats;
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
        return "Food{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", barcode_number='" + barcode_number + '\'' +
                ", calories='" + calories + '\'' +
                ", fats='" + fats + '\'' +
                ", sat_fats='" + sat_fats + '\'' +
                ", carbs='" + carbs + '\'' +
                ", sugar='" + sugar + '\'' +
                ", protein='" + protein + '\'' +
                ", salt='" + salt + '\'' +
                ", sodium='" + sodium + '\'' +
                '}';
    }
}
