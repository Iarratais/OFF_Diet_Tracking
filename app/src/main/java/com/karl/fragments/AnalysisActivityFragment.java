package com.karl.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karl.fyp.MainActivity;
import com.karl.fyp.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnalysisActivityFragment extends android.support.v4.app.Fragment {

    View rootView;

    private String SECTION1;
    private String SECTION2;

    private final static int NUM_PAGES = 2;

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    public AnalysisActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_analysis_activity, container, false);

        setSECTION1(getPreviousMonth());
        setSECTION2(getYear());

        ((MainActivity)getActivity()).setActionBarTitle(getString(R.string.analysis_fragment_title));

        sectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        viewPager = (ViewPager) rootView.findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * Get the previous month.
     * @return String : previous month.
     */
    public String getPreviousMonth(){
        Calendar calendar = Calendar.getInstance();
        int thisMonth = calendar.get(Calendar.MONTH);

        // We want the previous month.
        thisMonth -= 1;

        if(thisMonth < 0) {
            thisMonth = 11;
        }

        // Give back the month as a full word.
        switch(thisMonth) {
            case 0:
                return getString(R.string.january);
            case 1:
                return getString(R.string.february);
            case 2:
                return getString(R.string.march);
            case 3:
                return getString(R.string.april);
            case 4:
                return getString(R.string.may);
            case 5:
                return getString(R.string.june);
            case 6:
                return getString(R.string.july);
            case 7:
                return getString(R.string.august);
            case 8:
                return getString(R.string.september);
            case 9:
                return getString(R.string.october);
            case 10:
                return getString(R.string.november);
            case 11:
                return getString(R.string.december);
            default:
                return getString(R.string.last_month);
        }
    }

    /**
     * Get the current year.
     * @return String : current year.
     */
    public String getYear(){
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);

        return String.valueOf(thisYear);
    }

    public String getSECTION1() {
        return SECTION1;
    }

    public void setSECTION1(String SECTION1) {
        this.SECTION1 = SECTION1;
    }

    public String getSECTION2() {
        return SECTION2;
    }

    public void setSECTION2(String SECTION2) {
        this.SECTION2 = SECTION2;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new MonthAnalysisFragment();
                case 1:
                    return new YearAnalysisFragment();
            }
            return new MonthAnalysisFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        /**
         *
         * @param position that the page is at
         * @return the title of the page
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getSECTION1();
                case 1:
                    return getSECTION2();
            }
            return null;
        }
    }
}
