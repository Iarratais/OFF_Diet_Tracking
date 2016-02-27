package com.karl.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;
import com.karl.models.Day;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryListAdapter extends ArrayAdapter<String> {
    private static final String TAG = "HistoryListAdapter";

    private final Activity context;
    private final ArrayList<Day> days;
    private final String[] dates;

    public LayoutInflater inflater;

    public HistoryListAdapter(Activity context, String[] dates, ArrayList<Day> days) {
        super(context, R.layout.history_list_item, dates);

        this.context = context;
        this.dates = dates;
        this.days = days;

        inflater = LayoutInflater.from(this.context);
    }


    public View getView(final int position, View view, ViewGroup parent){

        if(view == null) {
            view = inflater.inflate(R.layout.history_list_item, null);
        }

        // Chop up the date and give back formatted date
        StringBuilder daysDate = new StringBuilder();
        StringBuffer date = new StringBuffer(dates[position]);



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

        final TextView dateView = (TextView) view.findViewById(R.id.textView3);
        dateView.setText(daysDate.toString());

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

        TextView fatView = (TextView) view.findViewById(R.id.textView5);
        fatView.setText(days.get(position).getFats() + context.getString(R.string.grams_abbv));

        TextView satFatView = (TextView) view.findViewById(R.id.textView6);
        satFatView.setText(days.get(position).getSaturated_fat() + context.getString(R.string.grams_abbv));

        TextView carbsView = (TextView) view.findViewById(R.id.textView9);
        carbsView.setText(days.get(position).getCarbs() + context.getString(R.string.grams_abbv));

        TextView sugarView = (TextView) view.findViewById(R.id.textView10);
        sugarView.setText(days.get(position).getSugar() + context.getString(R.string.grams_abbv));

        TextView saltView = (TextView) view.findViewById(R.id.textView7);
        saltView.setText(days.get(position).getSalt() + context.getString(R.string.grams_abbv));

        TextView sodiumView = (TextView) view.findViewById(R.id.textView8);
        sodiumView.setText(days.get(position).getSodium() + context.getString(R.string.grams_abbv));

        TextView proteinView = (TextView) view.findViewById(R.id.textView11);
        proteinView.setText(days.get(position).getProtein() + context.getString(R.string.grams_abbv));

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
     * @return String title/
     */
    public String setDateTitle(String dates){

        // Chop up the date and give back formatted date
        StringBuilder daysDate = new StringBuilder();
        StringBuffer date = new StringBuffer(dates);

        char char5 = date.charAt(0);
        char char6 = date.charAt(1);
        char char7 = date.charAt(2);
        daysDate.append(char5).append(char6).append(char7).append(" ");

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
}
