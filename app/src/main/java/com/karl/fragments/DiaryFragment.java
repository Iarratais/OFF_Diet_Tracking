package com.karl.fragments;

import android.app.DialogFragment;
import android.graphics.Typeface;
import com.karl.models.Food;
import com.karl.fyp.MainActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karl.fyp.R;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class DiaryFragment extends android.support.v4.app.Fragment {

    View rootView;

    private static final String TAG = "DiaryFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_diary, container, false);

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(setDateTitle());

        // Todays Entries title
        Typeface titleTypeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
        TextView todays_entries_title = (TextView) rootView.findViewById(R.id.today_entries_title);
        todays_entries_title.setTypeface(titleTypeFace);
        todays_entries_title.setTextSize(25);

        makeList();

        return rootView;
    }

    /**
     *
     * @param title of the alert
     * @param message to be contained in the alert
     */
    public void makeAlert(String title, String message) {
        DialogFragment dialogFragment = MyAlertDialogFragment.newInstance(title, message);
        dialogFragment.show(getActivity().getFragmentManager(), "dialog");
    }

    /**
     * Create the list to display the information
     */
    public void makeList() {
        ListView list = (ListView) rootView.findViewById(R.id.today_chart_information);
        ArrayList<String> listItems = new ArrayList<>();
        for (int i = 0; i < ef.size(); i++) {
            String listItem = ef.get(i).getName() + "\n"
                    + getString(R.string.calories) + " " + ef.get(i).getCalories()
                    + "\n" + getString(R.string.fat) + " " + ef.get(i).getFats()
                    + "\n" + getString(R.string.saturated_fat) + " " + ef.get(i).getSaturated_fat()
                    + "\n" + getString(R.string.carbohydrate) + " " + ef.get(i).getCarbohydrates()
                    + "\n" + getString(R.string.sugar) + " " + ef.get(i).getSugar()
                    + "\n" + getString(R.string.protein) + " " + ef.get(i).getProtein()
                    + "\n" + getString(R.string.salt) + " " + ef.get(i).getSalt()
                    + "\n" + getString(R.string.sodium) + " " + ef.get(i).getSodium();
            listItems.add(listItem);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(adapter);

    }

    /**
     * Set the title of the page using the current date.
     * @return String title/
     */
    public String setDateTitle(){

        // Chop up the date and give back formatted date
        StringBuilder daysDate = new StringBuilder();
        StringBuffer date = new StringBuffer(getDate());

        // Day
        char char1 = date.charAt(0);
        char char2 = date.charAt(1);
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
        char char3 = date.charAt(2);
        char char4 = date.charAt(3);
        StringBuilder month = new StringBuilder().append(char3).append(char4);
        String mon = month.toString();
        if(mon.equals("01")) {
            daysDate.append(getString(R.string.january));
        } else if (mon.equals("02")){
            daysDate.append(getString(R.string.february));
        } else if (mon.equals("03")){
            daysDate.append(getString(R.string.march));
        } else if (mon.equals("04")){
            daysDate.append(getString(R.string.april));
        } else if (mon.equals("05")){
            daysDate.append(getString(R.string.may));
        } else if (mon.equals("06")){
            daysDate.append(getString(R.string.june));
        } else if (mon.equals("07")){
            daysDate.append(getString(R.string.july));
        } else if (mon.equals("08")){
            daysDate.append(getString(R.string.august));
        } else if (mon.equals("09")){
            daysDate.append(getString(R.string.september));
        } else if (mon.equals("10")){
            daysDate.append(getString(R.string.october));
        } else if (mon.equals("11")){
            daysDate.append(getString(R.string.november));
        } else if (mon.equals("12")){
            daysDate.append(getString(R.string.december));
        } else {
            daysDate.append(mon);
        }

        return daysDate.toString();
    }

    /**
     * Get the current date.
     * @return string of current date. For example, 02012016.
     */
    public String getDate(){
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

        return day + month + year;
    }
}
