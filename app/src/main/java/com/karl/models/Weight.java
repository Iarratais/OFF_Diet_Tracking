package com.karl.models;

/**
 * Created by Karl on 04/03/2016.
 */
public class Weight {

    private String date;
    private String weight;
    private String change;

    public Weight(){
        // Empty constructor...
    }

    public Weight(String date, String weight, String change) {
        this.date = date;
        this.weight = weight;
        this.change = change;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    @Override
    public String toString() {
        return "Weight{" +
                "date='" + date + '\'' +
                ", weight='" + weight + '\'' +
                ", change='" + change + '\'' +
                '}';
    }
}
