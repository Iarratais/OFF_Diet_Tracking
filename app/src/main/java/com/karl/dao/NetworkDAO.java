package com.karl.dao;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Karl on 29/01/2016.
 */
public class NetworkDAO {

    private static final String TAG = "NetworkDAO";

    /**
     * Execute the given URI, and return the data from the URI.
     * @param uri the universal resource indicator for a set of data.
     * @return the set of data provided by the URI.
     */
    public String request(String uri) throws IOException {
        String returnString = "";

        // Use the GET method which submits the search terms in the URL.
        HttpGet httpGet = new HttpGet(uri);

        // How to handle response data.
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        HttpClient httpClient = new DefaultHttpClient();

        returnString = httpClient.execute(httpGet, responseHandler);

        return returnString;
    }
}
