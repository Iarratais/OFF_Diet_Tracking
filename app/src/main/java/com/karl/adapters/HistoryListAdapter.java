package com.karl.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Day;

import java.util.ArrayList;

public class HistoryListAdapter extends ArrayAdapter<String> {
    private static final String TAG = "HistoryListAdapter";

    private final Activity context;
    private final ArrayList<Day> days;
    private final String[] dates;
    private final boolean showDays;

    public LayoutInflater inflater;

    public HistoryListAdapter(Activity context, String[] dates, ArrayList<Day> days, boolean showDays) {
        super(context, R.layout.history_list_item, dates);

        this.context = context;
        this.dates = dates;
        this.days = days;
        this.showDays = showDays;

        inflater = LayoutInflater.from(this.context);
    }


    public View getView(final int position, View view, ViewGroup parent){

        if(view == null) {
            view = inflater.inflate(R.layout.history_list_item, null);
        }

        // Chop up the date and give back formatted date
        StringBuilder daysDate = new StringBuilder();
        StringBuffer date = new StringBuffer(dates[position]);

        char char5 = date.charAt(0);
        char char6 = date.charAt(1);
        char char7 = date.charAt(2);
        StringBuilder sb = new StringBuilder();
        sb.append(char5).append(char6).append(char7);
        if(showDays){
            daysDate.append(getFullDay(sb.toString())).append(" ");
        }

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

        final TextView dateView = (TextView) view.findViewById(R.id.textView3);
        dateView.setText(daysDate.toString());
        dateView.setTypeface(normalTypeface);

        final RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.relative_layout1);
        final RelativeLayout fullItem = (RelativeLayout) view.findViewById(R.id.relative_layout_history_list_item);
        layout.setVisibility(View.GONE);

        final ImageView button = (ImageView) view.findViewById(R.id.dropdownarrow);

        fullItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout.isShown()) {
                    layout.setVisibility(View.GONE);
                    button.setImageResource(R.drawable.ic_expand_more_black_18dp);
                } else {
                    layout.setVisibility(View.VISIBLE);
                    button.setImageResource(R.drawable.ic_expand_less_black_18dp);
                }
            }
        });

        TextView caloriesView = (TextView) view.findViewById(R.id.textView4);
        caloriesView.setText(days.get(position).getCalories());
        caloriesView.setTypeface(normalTypeface);

        TextView fatView = (TextView) view.findViewById(R.id.textView5);
        fatView.setText(days.get(position).getFats() + context.getString(R.string.grams_abbv));
        fatView.setTypeface(normalTypeface);

        TextView satFatView = (TextView) view.findViewById(R.id.textView6);
        satFatView.setText(days.get(position).getSaturated_fat() + context.getString(R.string.grams_abbv));
        satFatView.setTypeface(normalTypeface);

        TextView carbsView = (TextView) view.findViewById(R.id.textView9);
        carbsView.setText(days.get(position).getCarbs() + context.getString(R.string.grams_abbv));
        carbsView.setTypeface(normalTypeface);

        TextView sugarView = (TextView) view.findViewById(R.id.textView10);
        sugarView.setText(days.get(position).getSugar() + context.getString(R.string.grams_abbv));
        sugarView.setTypeface(normalTypeface);

        TextView saltView = (TextView) view.findViewById(R.id.textView7);
        saltView.setText(days.get(position).getSalt() + context.getString(R.string.grams_abbv));
        saltView.setTypeface(normalTypeface);

        TextView sodiumView = (TextView) view.findViewById(R.id.textView8);
        sodiumView.setText(days.get(position).getSodium() + context.getString(R.string.grams_abbv));
        sodiumView.setTypeface(normalTypeface);

        TextView proteinView = (TextView) view.findViewById(R.id.textView11);
        proteinView.setText(days.get(position).getProtein() + context.getString(R.string.grams_abbv));
        proteinView.setTypeface(normalTypeface);

        final MySQLiteHelper db = new MySQLiteHelper(getContext());
        TextView delete = (TextView) view.findViewById(R.id.textView12);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setCancelable(true)
                        .setMessage(context.getString(R.string.history_fragment_are_you_sure, setDateTitle(dates[position])))
                        .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteHistoryItem(days.get(position).getId());
                                dateView.setTextColor(context.getColor(R.color.light_grey));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        return view;
    }

    /**
     * Set the title of the page using the current date.
     * @return String title.
     */
    public String setDateTitle(String dates){

        // Chop up the date and give back formatted date
        StringBuilder daysDate = new StringBuilder();
        StringBuffer date = new StringBuffer(dates);

        char char5 = date.charAt(0);
        char char6 = date.charAt(1);
        char char7 = date.charAt(2);
        StringBuilder sb = new StringBuilder();
        sb.append(char5).append(char6).append(char7);
        if(showDays){
            daysDate.append(getFullDay(sb.toString())).append(" ");
        }

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

        String date_ = daysDate.toString();

        return date_;
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

    public String[] makeDaysArray(){
        return new String[]{context.getString(R.string.mondays).substring(0, 3).toUpperCase(), context.getString(R.string.tuesdays).substring(0, 3).toUpperCase(),
                context.getString(R.string.wednesdays).substring(0, 3).toUpperCase(), context.getString(R.string.thursdays).substring(0, 3).toUpperCase(),
                context.getString(R.string.fridays).substring(0, 3).toUpperCase(), context.getString(R.string.saturdays).substring(0, 3).toUpperCase(),
                context.getString(R.string.sundays).substring(0, 3).toUpperCase()};
    }
}
