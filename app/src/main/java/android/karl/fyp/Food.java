package android.karl.fyp;

public class Food {

    private String name;
    private float calories;
    private float fats;
    private float sat_fats;
    private float carbs;
    private float sugar;
    private float protein;
    private float salt;
    private float sodium;

    public Food(String name, float calories, float fats, float sat_fats, float carbs, float sugar, float protein, float salt, float sodium) {
        this.name = name;
        this.calories = calories;
        this.fats = fats;
        this.sat_fats = sat_fats;
        this.carbs = carbs;
        this.sugar = sugar;
        this.protein = protein;
        this.salt = salt;
        this.sodium = sodium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getSat_fats() {
        return sat_fats;
    }

    public void setSat_fats(float sat_fats) {
        this.sat_fats = sat_fats;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getSugar() {
        return sugar;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getSalt() {
        return salt;
    }

    public void setSalt(float salt) {
        this.salt = salt;
    }

    public float getSodium() {
        return sodium;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }


}
