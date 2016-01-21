package android.karl.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.karl.fyp.Food;
import android.karl.fyp.MainActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.karl.fyp.R;
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
        ef.add(new Food("Dairygold", 57f, 6.3f, 2.3f, 0f, 0.1f, 0f, 0.199f, 0.0787f));

        // Nutella
        ef.add(new Food("Nutella", 83f, 4.75f, 1.64f, 8.59f, 8.51f, 0.9f, 0.0141f, 0.00555f));

        // Royal Bacon - MCDonalds
        ef.add(new Food("Royal Bacon", 504f, 25.6f, 12f, 33.5f, 8.08f, 33.5f, 1.6f, 0.628f));

        // Power crunch protein bar
        ef.add(new Food("Power Crunch", 375f, 11.9f, 0f, 33.8f, 0f, 34.7f, 0f, 0f));

        makeList();

        return rootView;
    }

    public void makeAlert(String title, String message) {
        new AlertDialog.Builder(this.getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public void makeList() {
        ListView list = (ListView) rootView.findViewById(R.id.today_chart_information);
        ArrayList<String> listItems = new ArrayList<>();
        for (int i = 0; i < ef.size(); i++) {
            String listItem = ef.get(i).getName() + "\n"
                    + getString(R.string.calories) + " " + ef.get(i).getCalories()
                    + "\n" + getString(R.string.fat) + " " + ef.get(i).getFats()
                    + "\n" + getString(R.string.saturated_fat) + " " + ef.get(i).getSat_fats()
                    + "\n" + getString(R.string.carbohydrate) + " " + ef.get(i).getCarbs()
                    + "\n" + getString(R.string.sugar) + " " + ef.get(i).getSugar()
                    + "\n" + getString(R.string.protein) + " " + ef.get(i).getProtein()
                    + "\n" + getString(R.string.salt) + " " + ef.get(i).getSalt()
                    + "\n" + getString(R.string.sodium) + " " + ef.get(i).getSodium();
            listItems.add(listItem);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(adapter);
    }
}
