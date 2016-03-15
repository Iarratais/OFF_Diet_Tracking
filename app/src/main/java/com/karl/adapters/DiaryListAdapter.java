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
import com.karl.models.Food;

import java.util.ArrayList;

/**
 * Created by Karl on 18/02/2016.
 */
public class DiaryListAdapter extends ArrayAdapter<Food> {

    private static final String TAG = "DiaryListAdapter";

    private final Activity context;
    private final ArrayList<Food> foods;

    public LayoutInflater inflater;

    public DiaryListAdapter(Activity context, ArrayList<Food> foods){
        super(context, R.layout.history_list_item, foods);

        this.context = context;
        this.foods = foods;

        inflater = LayoutInflater.from(this.context);
    }

    public View getView(final int position, View view, ViewGroup parent){
        if(view == null) {
            view = inflater.inflate(R.layout.history_list_item, null);
        }

        Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/New_Cicle_Gordita.ttf");

        final TextView dateView = (TextView) view.findViewById(R.id.textView3);
        dateView.setText(foods.get(position).getName());
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
        caloriesView.setText(foods.get(position).getCalories());
        caloriesView.setTypeface(normalTypeface);

        TextView fatView = (TextView) view.findViewById(R.id.textView5);
        fatView.setText(foods.get(position).getFats() + context.getString(R.string.grams_abbv));
        fatView.setTypeface(normalTypeface);

        TextView satFatView = (TextView) view.findViewById(R.id.textView6);
        satFatView.setText(foods.get(position).getSaturated_fat() + context.getString(R.string.grams_abbv));
        fatView.setTypeface(normalTypeface);

        TextView carbsView = (TextView) view.findViewById(R.id.textView9);
        carbsView.setText(foods.get(position).getCarbohydrates() + context.getString(R.string.grams_abbv));
        carbsView.setTypeface(normalTypeface);

        TextView sugarView = (TextView) view.findViewById(R.id.textView10);
        sugarView.setText(foods.get(position).getSugar() + context.getString(R.string.grams_abbv));
        sugarView.setTypeface(normalTypeface);

        TextView saltView = (TextView) view.findViewById(R.id.textView7);
        saltView.setText(foods.get(position).getSalt() + context.getString(R.string.grams_abbv));
        saltView.setTypeface(normalTypeface);

        TextView sodiumView = (TextView) view.findViewById(R.id.textView8);
        sodiumView.setText(foods.get(position).getSodium() + context.getString(R.string.grams_abbv));
        sodiumView.setTypeface(normalTypeface);

        TextView proteinView = (TextView) view.findViewById(R.id.textView11);
        proteinView.setText(foods.get(position).getProtein() + context.getString(R.string.grams_abbv));
        proteinView.setTypeface(normalTypeface);

        final MySQLiteHelper db = new MySQLiteHelper(getContext());
        TextView delete = (TextView) view.findViewById(R.id.textView12);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setCancelable(true)
                        .setMessage(context.getString(R.string.history_fragment_are_you_sure, foods.get(position).getName()))
                        .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteTodayEntry(foods.get(position).getId());
                                db.deleteTodayStatsEntry(foods.get(position).getId());
                                dateView.setTextColor(context.getColor(R.color.light_grey));
                            }
                        })
                        .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
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
}
