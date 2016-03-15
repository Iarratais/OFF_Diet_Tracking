package com.karl.dao;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Copyright Karl jones 2016.
 *
 * The NetworkDAO connects the device to the OpenFoodFacts database to get information for the
 * FoodDAO.
 */

public class NetworkDAO {

    private static final String TAG = "NetworkDAO";

    /**
     * Execute the given URI, and return the data from the URI.
     * @param uri the universal resource indicator for a set of data.
     * @return the set of data provided by the URI.
     */
    public String request(String uri) throws IOException {
        // Use the GET method which submits the search terms in the URL.
        HttpGet httpGet = new HttpGet(uri);

        // How to handle response data.
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        HttpClient httpClient = new DefaultHttpClient();

        return httpClient.execute(httpGet, responseHandler);
    }
}
