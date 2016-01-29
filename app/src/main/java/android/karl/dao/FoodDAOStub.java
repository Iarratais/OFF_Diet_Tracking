package android.karl.dao;

import android.karl.models.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 29/01/2016.
 */
public class FoodDAOStub implements IFoodDAO {

    @Override
    public List<Food> fetchFood(String searchBarcode){
        // Declare return type
        List<Food> allFood = new ArrayList<Food>();

        // Populate the list with a harcoded, known set of plants
        Food foods = new Food();
        foods.setName("Nutella - Ferrero - 1kg");
        foods.setCalories("544");
        foods.setFats("31.6");
        foods.setSat_fats("10.9");
        foods.setCarbs("57.3");
        foods.setSugar("56.7");
        foods.setProtein("6");
        foods.setSalt("0.09398");
        foods.setSodium("0.037");

        // Add the food to our collection
        allFood.add(foods);



        // Return the return value
        return allFood;
    }
}
