package com.karl.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import com.karl.fyp.MainActivity;
import com.karl.fyp.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Copyright Karl jones 2016.
 *
 * This fragment is used to display the OpenFoodFacts website to the user so that they can log in
 * and input information from their devices if they are so inclined to.
 */
public class AddToDatabaseFragment extends android.support.v4.app.Fragment {

    // The URL for the openfoodfacts database website.
    public static String url = "http://world.openfoodfacts.org/";

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_addtodatabase, container, false);

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.addtodatabase_fragment_title));

        goToWebsite();

        return rootView;
    }

    /**
     * This loads the website for the user to be able to use.
     */
    public void goToWebsite() {
        final WebView wv = (WebView) rootView.findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAppCacheEnabled(true);
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient() {

            // This prevents the page from going into the phones built in browser
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }

            // If the page hits an error, allow the user to go to the settings and also hides the
            // webview.
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Snackbar.make(rootView, getString(R.string.error_sorry_check_your_network_settings), Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.settings_fragment_title), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                            }
                        })
                        .show();
                wv.setVisibility(View.GONE);
            }
        });
    }
}
