package com.karl.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.karl.fyp.R;
import com.karl.models.Weight;

import java.util.ArrayList;

/**
 * Copyright Karl jones 2016.
 *
 * This adapter deals with the list in the progress fragment.
 */

public class ProgressListAdapter extends ArrayAdapter<Weight> {
    public LayoutInflater inflater;

    private Context context;
    private ArrayList<Weight> weights;

    public ProgressListAdapter(Context context, ArrayList<Weight> weights){
        super(context, R.layout.weight_list_item, weights);

        this.context = context;
        this.weights = weights;

        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        if(view == null){
            view = inflater.inflate(R.layout.weight_list_item, null);
        }

        // Chop up the date and give back formatted date
        StringBuilder daysDate = new StringBuilder();
        StringBuffer date = new StringBuffer(weights.get(position).getDate());

        char char5 = date.charAt(0);
        char char6 = date.charAt(1);
        char char7 = date.charAt(2);
        StringBuilder sb = new StringBuilder();
        sb.append(char5).append(char6).append(char7);

        daysDate.append(getFullDay(sb.toString())).append(" ");

        // Day
        char char1 = date.charAt(3);
        char char2 = date.charAt(4);
        if(char1 == '0'){
            daysDate.append(char2);
            if(char2 == '1'){
                daysDate.append("st");
            } else if (char2 == '2') {
                daysDate.append("nd");
            } else if (char2 == '3') {
                daysDate.append("rd");
            } else {
                daysDate.append("th");
            }
            daysDate.append(" ");
        } else {
            daysDate.append(char1);
            daysDate.append(char2);
            if(char1 == '2'){
                if(char2 == '1'){
                    daysDate.append("st");
                } else if (char2 == '2') {
                    daysDate.append("nd");
                } else if (char2 == '3') {
                    daysDate.append("rd");
                } else {
                    daysDate.append("th");
                }
            } else if (char1 == '3'){
                if(char2 == '1'){
                    daysDate.append("st");
                } else {
                    daysDate.append("th");
                }
            } else {
                daysDate.append("th");
            }
            daysDate.append(" ");
        }

        // Month
        char char3 = date.charAt(5);
        char char4 = date.charAt(6);
        StringBuilder month = new StringBuilder().append(char3).append(char4);
        String mon = month.toString();
        if(mon.equals("01")) {
            daysDate.append(context.getString(R.string.january));
        } else if (mon.equals("02")){
            daysDate.append(context.getString(R.string.february));
        } else if (mon.equals("03")){
            daysDate.append(context.getString(R.string.march));
        } else if (mon.equals("04")){
            daysDate.append(context.getString(R.string.april));
        } else if (mon.equals("05")){
            daysDate.append(context.getString(R.string.may));
        } else if (mon.equals("06")){
            daysDate.append(context.getString(R.string.june));
        } else if (mon.equals("07")){
            daysDate.append(context.getString(R.string.july));
        } else if (mon.equals("08")){
            daysDate.append(context.getString(R.string.august));
        } else if (mon.equals("09")){
            daysDate.append(context.getString(R.string.september));
        } else if (mon.equals("10")){
            daysDate.append(context.getString(R.string.october));
        } else if (mon.equals("11")){
            daysDate.append(context.getString(R.string.november));
        } else if (mon.equals("12")){
            daysDate.append(context.getString(R.string.december));
        } else {
            daysDate.append(mon);
        }

        Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/New_Cicle_Gordita.ttf");

        TextView date_title = (TextView) view.findViewById(R.id.weight_item_date);
        date_title.setText(daysDate.toString());
        date_title.setTypeface(normalTypeface);

        TextView weight = (TextView) view.findViewById(R.id.weight_item_weight);
        weight.setText(weights.get(position).getWeight() + context.getString(R.string.kg));
        weight.setTypeface(normalTypeface);

        return view;
    }

    String[] days_string;
    /**
     * Returns the full spelling of the day when given a three letter code.
     * @param day string: three letter day code.
     * @return String: day of the week.
     */
    public String getFullDay(String day){
        days_string = makeDaysArray();

        if(day.equals(days_string[0])){
            return context.getString(R.string.monday);
        } else if(day.equals(days_string[1])){
            return context.getString(R.string.tuesday);
        } else if(day.equals(days_string[2])){
            return context.getString(R.string.wednesday);
        } else if(day.equals(days_string[3])){
            return context.getString(R.string.thursday);
        } else if(day.equals(days_string[4])){
            return context.getString(R.string.friday);
        } else if(day.equals(days_string[5])){
            return context.getString(R.string.saturday);
        } else if(day.equals(days_string[6])){
            return context.getString(R.string.sunday);
        }

        return null;
    }

    /**
     * Make the array with the three letter day codes.
     * @return string[]: days.
     */
    public String[] makeDaysArray(){
        return new String[]{context.getString(R.string.mondays).substring(0, 3).toUpperCase(), context.getString(R.string.tuesdays).substring(0, 3).toUpperCase(),
                context.getString(R.string.wednesdays).substring(0, 3).toUpperCase(), context.getString(R.string.thursdays).substring(0, 3).toUpperCase(),
                context.getString(R.string.fridays).substring(0, 3).toUpperCase(), context.getString(R.string.saturdays).substring(0, 3).toUpperCase(),
                context.getString(R.string.sundays).substring(0, 3).toUpperCase()};
    }
}
