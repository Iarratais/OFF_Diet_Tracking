package android.karl.dao;

import android.karl.models.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 29/01/2016.
 */
public class FoodDAO implements IFoodDAO {

    private final NetworkDAO networkDAO;

    public FoodDAO() {
        networkDAO = new NetworkDAO();
    }

    @Override
    public List<Food> fetchFood(String searchBarcode) throws IOException, JSONException {
        String uri = "http://world.openfoodfacts.org/api/v0/product/" + searchBarcode + ".json";
        String request = networkDAO.request(uri);

        List<Food> allFoods = new ArrayList<Food>();

        // Parse the string into JSON here
        JSONObject root = new JSONObject(request);
        JSONObject product = root.getJSONObject("product");
        JSONObject nutriments = product.getJSONObject("nutriments");

        for(int i = 0; i < 1; i++) {

            // 100g/100ml
            Food food = new Food();

            String product_name = "Result";
            String energy = "0";
            String fat = "0";
            String saturated_fat = "0";
            String carbohydrates = "0";
            String sugar = "0";
            String protein = "0";
            String salt = "0";
            String sodium = "0";

            try {
                product_name = product.getString("product_name");
                if(product_name.equals(""))
                    product_name = "Result";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                energy = nutriments.getString("energy_100g");
                if(energy.equals(""))
                    energy = "0";
            } catch (JSONException e){
                e.printStackTrace();
            }

            try {
                fat = nutriments.getString("fat_100g");
                if(fat.equals(""))
                    fat = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                saturated_fat = nutriments.getString("saturated-fat_100g");
                if(saturated_fat.equals(""))
                    saturated_fat = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                carbohydrates = nutriments.getString("carbohydrates_100g");
                if(carbohydrates.equals(""))
                    carbohydrates = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                sugar = nutriments.getString("sugars_100g");
                if(sugar.equals(""))
                    sugar = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                protein = nutriments.getString("proteins_100g");
                if(protein.equals(""))
                    protein = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                salt = nutriments.getString("salt_100g");
                if(salt.equals(""))
                    salt = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                sodium = nutriments.getString("sodium_100g");
                if(sodium.equals(""))
                    sodium = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            food.setName(product_name);
            food.setBarcode_number(searchBarcode);
            food.setCalories(energy);
            food.setFats(fat);
            food.setSat_fats(saturated_fat);
            food.setCarbs(carbohydrates);
            food.setSugar(sugar);
            food.setProtein(protein);
            food.setSalt(salt);
            food.setSodium(sodium);
            System.out.println("FOOD : " + food.toString());

            allFoods.add(food);

            // Per Serving
            Food foodServing = new Food();

            String energy_serving = "0";
            String fat_serving = "0";
            String saturated_fat_serving = "0";
            String carbohydrates_serving = "0";
            String sugar_serving = "0";
            String protein_serving = "0";
            String salt_serving = "0";
            String sodium_serving = "0";

            try {
                energy_serving = nutriments.getString("energy_serving");
                if(energy_serving.equals(""))
                    energy_serving = "0";
            } catch (JSONException e){
                e.printStackTrace();
            }

            try {
                fat_serving = nutriments.getString("fat_serving");
                if(fat_serving.equals(""))
                    fat_serving = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                saturated_fat_serving = nutriments.getString("saturated-fat_serving");
                if(saturated_fat_serving.equals(""))
                    saturated_fat_serving = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                carbohydrates_serving = nutriments.getString("carbohydrates_serving");
                if(carbohydrates_serving.equals(""))
                    carbohydrates_serving = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                sugar_serving = nutriments.getString("sugars_serving");
                if(sugar_serving.equals(""))
                    sugar_serving = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                protein_serving = nutriments.getString("proteins_serving");
                if(protein_serving.equals(""))
                    protein_serving = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                salt_serving = nutriments.getString("salt_serving");
                if(salt_serving.equals(""))
                    salt_serving = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                sodium_serving = nutriments.getString("sodium_serving");
                if(sodium_serving.equals(""))
                    sodium_serving = "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            foodServing.setName(product_name);
            foodServing.setBarcode_number(searchBarcode);
            foodServing.setCalories(energy_serving);
            foodServing.setFats(fat_serving);
            foodServing.setSat_fats(saturated_fat_serving);
            foodServing.setCarbs(carbohydrates_serving);
            foodServing.setSugar(sugar_serving);
            foodServing.setProtein(protein_serving);
            foodServing.setSalt(salt_serving);
            foodServing.setSodium(sodium_serving);
            System.out.println("FOODSERVING : " + foodServing.toString());

            allFoods.add(foodServing);
        }

        return allFoods;
    }
}
