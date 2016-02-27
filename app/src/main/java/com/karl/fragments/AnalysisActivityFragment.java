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

        setSECTION1(getString(R.string.last_month));
        setSECTION2(getString(R.string.year));

        ((MainActivity)getActivity()).setActionBarTitle(getString(R.string.analysis_fragment_title));

        sectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        viewPager = (ViewPager) rootView.findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Inflate the layout for this fragment
        return rootView;
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
                    return new DayAnalysisFragment();
                case 1:
                    return new MonthAnalysisFragment();
            }
            return new DayAnalysisFragment();
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
