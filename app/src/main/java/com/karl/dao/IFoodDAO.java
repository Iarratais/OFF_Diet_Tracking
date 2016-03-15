package com.karl.dao;

import com.karl.models.Food;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Copyright Karl jones 2016.
 *
 * Interface for the food data access object.
 */

public interface IFoodDAO {
    List<Food> fetchFood(String searchBarcode) throws IOException, JSONException;
    boolean checkProduct(String barcode) throws IOException, JSONException;
}
