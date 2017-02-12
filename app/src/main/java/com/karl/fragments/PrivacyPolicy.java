package com.karl.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.karl.fyp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyPolicy extends Fragment {

    private static final String PRIVACY_POLICY = "https://karljones.space/food-keep-privacy-policy/";

    View rootView;

    public PrivacyPolicy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_privacy_policy, container, false);

        WebView webView = (WebView) rootView.findViewById(R.id.fragment_privacy_policy_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(PRIVACY_POLICY);

        return rootView;
    }

}
