package com.karl.fragments;

import android.app.Fragment;
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
 * Goals fragment
 *
 * Copyright Karl Jones 2016
 */
public class AddToDatabaseFragment extends Fragment {

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

    public void goToWebsite() {
        WebView wv = (WebView) rootView.findViewById(R.id.webView);
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

            // If the page hits an error, allow the user to go to the settings
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                Snackbar.make(rootView.findViewById(android.R.id.content), getString(R.string.sorry_check_your_network_settings), Snackbar.LENGTH_INDEFINITE)
//                        .setAction(getString(R.string.settings_title), new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
//                            }
//                        })
//                        .show();
            }
        });
    }
}
