package com.karl.fragments;


import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karl.fyp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class YearAnalysisFragment extends android.support.v4.app.Fragment {


    public YearAnalysisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_year_analysis, container, false);
    }

    class Yearly_Analysis extends AsyncTask<Context, Void, Void> {

        private String[] months;
        private Context context;

        @Override
        protected Void doInBackground(Context... params) {
            context = params[0];
            Resources res = context.getResources();

            months = new String[] {res.getString(R.string.january), res.getString(R.string.february), res.getString(R.string.march), res.getString(R.string.april),
                    res.getString(R.string.may), res.getString(R.string.june), res.getString(R.string.july),
                    res.getString(R.string.august), res.getString(R.string.september), res.getString(R.string.october),
                    res.getString(R.string.november), res.getString(R.string.december)};

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
