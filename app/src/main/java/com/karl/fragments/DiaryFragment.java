package com.karl.fragments;


import android.app.DialogFragment;
import android.graphics.Typeface;
import com.karl.models.Food;
import com.karl.fyp.MainActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karl.fyp.R;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DiaryFragment extends Fragment {

    View rootView;
    ArrayList<Food> ef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_diary, container, false);

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.diary_fragment_title));

        // Todays Entries title
        Typeface titleTypeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
        TextView todays_entries_title = (TextView) rootView.findViewById(R.id.today_entries_title);
        todays_entries_title.setTypeface(titleTypeFace);
        todays_entries_title.setTextSize(25);

        ef = new ArrayList<>();

        // Dairygold
        ef.add(new Food("Dairygold", "000000", "57", "6.3", "2.3", "0", "0.1", "0", "0.199", "0.0787"));

        // Nutella
        ef.add(new Food("Nutella", "000000", "83", "4.75", "1.64", "8.59", "8.51", "0.9", "0.0141", "0.00555"));

        // Royal Bacon - MCDonalds
        ef.add(new Food("Royal Bacon", "000000", "504", "25.6", "12", "33.5", "8.08", "33.5", "1.6", "0.628"));

        // Power crunch protein bar
        ef.add(new Food("Power Crunch", "000000", "375", "11.9", "0", "33.8", "0", "34.7", "0", "0"));

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
        dialogFragment.show(getFragmentManager(), "dialog");
    }

    public void makeList() {
        ListView list = (ListView) rootView.findViewById(R.id.today_chart_information);
        ArrayList<String> listItems = new ArrayList<>();
        for (int i = 0; i < ef.size(); i++) {
            String listItem = ef.get(i).getName() + "\n"
                    + getString(R.string.calories) + " " + ef.get(i).getCalories()
                    + "\n" + getString(R.string.fat) + " " + ef.get(i).getFats()
                    + "\n" + getString(R.string.saturated_fat) + " " + ef.get(i).getSaturated_fat()
                    + "\n" + getString(R.string.carbohydrate) + " " + ef.get(i).getCarbs()
                    + "\n" + getString(R.string.sugar) + " " + ef.get(i).getSugar()
                    + "\n" + getString(R.string.protein) + " " + ef.get(i).getProtein()
                    + "\n" + getString(R.string.salt) + " " + ef.get(i).getSalt()
                    + "\n" + getString(R.string.sodium) + " " + ef.get(i).getSodium();
            listItems.add(listItem);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(adapter);
    }
}
