package android.karl.models;


public class Goals {

    private float calories;
    private float fat;
    private float sat_fat;
    private float sodium;
    private float carbs;
    private float protein;
    private float sugar;
    private float salt;

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getSat_fat() {
        return sat_fat;
    }

    public void setSat_fat(float sat_fat) {
        this.sat_fat = sat_fat;
    }

    public float getSodium() {
        return sodium;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getSugar() {
        return sugar;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    public float getSalt() {
        return salt;
    }

    public void setSalt(float salt) {
        this.salt = salt;
    }

    public void loadGoals() {
        // This needs to be filled in with the database connection and gathering information
        // about the goals set by the user
    }

    @Override
    public String toString() {
        return "Goals{" +
                "calories=" + calories +
                ", fat=" + fat +
                ", sat_fat=" + sat_fat +
                ", sodium=" + sodium +
                ", carbs=" + carbs +
                ", protein=" + protein +
                ", sugar=" + sugar +
                ", salt=" + salt +
                '}';
    }
}
