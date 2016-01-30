package com.karl.dao;

import com.karl.models.Food;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Karl on 29/01/2016.
 */
public interface IFoodDAO {
    List<Food> fetchFood(String searchBarcode) throws IOException, JSONException;
    boolean checkProduct(String barcode) throws IOException, JSONException;
}
