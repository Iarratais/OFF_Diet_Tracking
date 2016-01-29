package com.karl.dao;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by Karl on 29/01/2016.
 */
public class NetworkDAO {

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

        System.out.println("NETWORK DAO : " + returnString);
        return returnString;
    }
}
