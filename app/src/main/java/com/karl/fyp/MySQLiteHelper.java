package com.karl.fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.karl.models.Food;
import com.karl.models.Goals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Copyright Karl jones 2016.
 * MySQLiteHelper
 *
 * This handles the methods of the database.
 */

public class MySQLiteHelper extends SQLiteOpenHelper{

    private static final String TAG = "MySQLiteHelper";

    // Common
    private static final int DATABASE_VERSION = 12;
    private static final String DATABASE_NAME = "diet.db";
    private static final String USER_TABLE = "user";
    private static final String TODAY_TABLE = "today";
    private static final String TODAY_STATS_TABLE = "todaystats";
    private static final String HISTORY_TABLE = "history";
    private static final String WEIGHT_HISTORY_TABLE = "weight_history";
    private static final String GOALS_TABLE = "goals";
    private static final String QUERY100_TABLE = "query100";
    private static final String QUERYSERVING_TABLE = "queryserving";

    // User table
    private static final String USER_KEY_ID = "user_id";
    private static final String USER_KEY_NAME = "name";
    private static final String USER_KEY_GENDER = "gender";
    private static final String USER_KEY_WEIGHT = "weight";
    private static final String USER_KEY_HEIGHT = "height";

    // Today table
    private static final String TODAY_KEY_ID = "food_id";
    private static final String TODAY_KEY_DATE = "date";
    private static final String TODAY_KEY_FOOD_NAME = "food_name";
    private static final String TODAY_KEY_BARCODE_NO = "barcode_number";

    // Today Stats table
    private static final String TODAY_STATS_KEY_ID = "food_id";
    private static final String TODAY_STATS_KEY_DATE = "date";
    private static final String TODAY_STATS_KEY_CALORIES = "calories";
    private static final String TODAY_STATS_KEY_FAT = "fat";
    private static final String TODAY_STATS_KEY_SATURATED_FAT = "sat_fat";
    private static final String TODAY_STATS_KEY_CARBOHYDRATES = "carbs";
    private static final String TODAY_STATS_KEY_SUGAR = "sugar";
    private static final String TODAY_STATS_KEY_PROTEIN = "protein";
    private static final String TODAY_STATS_KEY_SALT = "salt";
    private static final String TODAY_STATS_KEY_SODIUM = "sodium";

    // History table
    private static final String HISTORY_KEY_ID = "history_id";
    private static final String HISTORY_DATE = "date";
    private static final String HISTORY_CALORIES = "calories";
    private static final String HISTORY_FAT = "fats";
    private static final String HISTORY_SAT_FAT = "sat_fat";
    private static final String HISTORY_SALT = "salt";
    private static final String HISTORY_SODIUM = "sodium";
    private static final String HISTORY_CARBS = "carbohydrates";
    private static final String HISTORY_SUGAR = "sugar";
    private static final String HISTORY_PROTEIN = "protein";

    // Goals table
    private static final String GOAL_KEY_ID = "goal_id";
    private static final String GOAL_CALORIES = "calories";
    private static final String GOAL_FAT = "fat";
    private static final String GOAL_SATURATED_FAT = "saturated_fat";
    private static final String GOAL_SALT = "salt";
    private static final String GOAL_SODIUM = "sodium";
    private static final String GOAL_CARBS = "carbohydrates";
    private static final String GOAL_SUGAR = "sugar";
    private static final String GOAL_PROTEIN = "protein";
    private static final String GOAL_WEIGHT = "weight";

    // Weight history table
    private static final String WEIGHT_KEY_ID = "weight_id";
    private static final String WEIGHT_DATE = "date";
    private static final String WEIGHT_TOTAL = "weight";
    private static final String WEIGHT_CHANGE = "change";

    // Query100 table
    private static final String QUERY100_KEY_ID = "query100_id";
    private static final String QUERY100_BARCODE = "barcode";
    private static final String QUERY100_CALORIES = "calories";
    private static final String QUERY100_FAT = "fats";
    private static final String QUERY100_SAT_FAT = "sat_fat";
    private static final String QUERY100_SALT = "salt";
    private static final String QUERY100_SODIUM = "sodium";
    private static final String QUERY100_CARBS = "carbohydrates";
    private static final String QUERY100_SUGAR = "sugar";
    private static final String QUERY100_PROTEIN = "protein";

    // QueryServing table
    private static final String QUERYSERVING_KEY_ID = "query100_id";
    private static final String QUERYSERVING_BARCODE= "barcode";
    private static final String QUERYSERVING_CALORIES = "calories";
    private static final String QUERYSERVING_FAT = "fats";
    private static final String QUERYSERVING_SAT_FAT = "sat_fat";
    private static final String QUERYSERVING_SALT = "salt";
    private static final String QUERYSERVING_SODIUM = "sodium";
    private static final String QUERYSERVING_CARBS = "carbohydrates";
    private static final String QUERYSERVING_SUGAR = "sugar";
    private static final String QUERYSERVING_PROTEIN = "protein";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);

        createTodayTable(db);

        createTodayStatsTable(db);

        createHistoryTable(db);

        createGoalsTable(db);

        createWeightHistoryTable(db);

        createQuery100Table(db);

        createQueryServingTable(db);
    }

    public String getDatabaseName(){
        return DATABASE_NAME;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TODAY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TODAY_STATS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GOALS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WEIGHT_HISTORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + QUERY100_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + QUERYSERVING_TABLE);
        onCreate(db);
    }

    /**
     * Create the user table.
     * @param db instance.
     */
    public void createUserTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE
                        + "(" + USER_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + USER_KEY_NAME + " TEXT, "
                        + USER_KEY_GENDER + " TEXT, "
                        + USER_KEY_WEIGHT + " TEXT, "
                        + USER_KEY_HEIGHT + " TEXT)"
        );
        Log.d(TAG, "createUserTable run");
    }

    /**
     * Create a user profile.
     * @param name of the user.
     * @param gender of the user.
     * @param height of the user.
     * @param weight of the user.
     * @return id created for the user.
     */
    public long createUser(String name, String gender, String height, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(USER_KEY_NAME, name);
        cv.put(USER_KEY_GENDER, gender);
        cv.put(USER_KEY_WEIGHT, weight);
        cv.put(USER_KEY_HEIGHT, height);

        long result = db.insert(USER_TABLE, null, cv);
        Log.d(TAG, "User created: " + result);

        db.close();

        return result;
    }

    /**
     * Get the user data.
     * @return cursor containing user information.
     */
    public Cursor getAllDataUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + USER_TABLE, null);
        db.close();
        return res;
    }

    /**
     * Get a user information.
     * @return cursor with user information.
     */
    public Cursor getUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + USER_TABLE, null);
    }

    /**
     * Clear all of the user data.
     */
    public void wipeUserTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + USER_TABLE);
        db.close();
    }

    /**
     * Update the users profile.
     * @param name of the user.
     * @param gender of the user.
     * @param weight of the user.
     * @param height of the user.
     */
    public void updateUser(String name, String gender, String weight, String height) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(USER_KEY_NAME, name);
        cv.put(USER_KEY_GENDER, gender);
        cv.put(USER_KEY_WEIGHT, weight);
        cv.put(USER_KEY_HEIGHT, height);

        String selection = USER_KEY_ID + " LIKE ?";
        String[] selectionArgs = { "1" };
        db.update(USER_TABLE, cv, selection, selectionArgs);
        Log.d(TAG, "User updated");
        db.close();
    }


    /*
    Create the Today table
     */
    public void createTodayTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TODAY_TABLE
                        + "(" + TODAY_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + TODAY_KEY_DATE + " TEXT, "
                        + TODAY_KEY_FOOD_NAME + " TEXT, "
                        + TODAY_KEY_BARCODE_NO + " TEXT)"
        );
        Log.d(TAG, "createTodayTable run");
    }


    /*
    Create the Today_stats table
     */
    public void createTodayStatsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TODAY_STATS_TABLE
                + "(" + TODAY_STATS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TODAY_STATS_KEY_DATE + " TEXT, "
                + TODAY_STATS_KEY_CALORIES + " TEXT, "
                + TODAY_STATS_KEY_FAT + " TEXT, "
                + TODAY_STATS_KEY_SATURATED_FAT + " TEXT, "
                + TODAY_STATS_KEY_CARBOHYDRATES + " TEXT, "
                + TODAY_STATS_KEY_SUGAR + " TEXT, "
                + TODAY_STATS_KEY_PROTEIN + " TEXT, "
                + TODAY_STATS_KEY_SALT + " TEXT, "
                + TODAY_STATS_KEY_SODIUM + " TEXT)");
    }

    public void createNewTodayEntry(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues today = new ContentValues();
        today.put(TODAY_KEY_DATE, getDate());
        today.put(TODAY_KEY_FOOD_NAME, food.getName());
        today.put(TODAY_KEY_BARCODE_NO, food.getBarcode_number());

        db.insert(TODAY_TABLE, null, today);

        ContentValues today_stats = new ContentValues();
        today_stats.put(TODAY_STATS_KEY_DATE, getDate());
        today_stats.put(TODAY_STATS_KEY_CALORIES, food.getCalories());
        today_stats.put(TODAY_STATS_KEY_FAT, food.getFats());
        today_stats.put(TODAY_STATS_KEY_SATURATED_FAT, food.getSaturated_fat());
        today_stats.put(TODAY_STATS_KEY_CARBOHYDRATES, food.getCarbohydrates());
        today_stats.put(TODAY_STATS_KEY_SUGAR, food.getSugar());
        today_stats.put(TODAY_STATS_KEY_PROTEIN, food.getProtein());
        today_stats.put(TODAY_STATS_KEY_SALT, food.getSalt());
        today_stats.put(TODAY_STATS_KEY_SODIUM, food.getSodium());

        db.insert(TODAY_STATS_TABLE, null, today_stats);

        Log.d(TAG, "DATE: " + getDate() + "\nFOOD NAME: " + food.getName() + "\nBARCODE NUMBER: " + food.getBarcode_number() + "\nCALORIES: " +
        food.getCalories() + "\nFATS: " + food.getFats() + "\nSATURATED FATS: " + food.getSaturated_fat() + "\nCARBOHYDRATES: " + food.getCarbohydrates() +
        "\nSUGAR: " + food.getSugar() + "\nPROTEIN: " + food.getProtein() + "\nSALT: " + food.getSalt() + "\nSODIUM: " + food.getSodium() );
    }

    /**
     * Return all entries from the today table for current date.
     * @return cursor with today table information.
     */
    public Cursor returnTodaysEntries() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TODAY_TABLE + " WHERE " + TODAY_KEY_DATE + " LIKE ?";
        return db.rawQuery(query, new String[]{String.valueOf(getDate())});
    }

    /**
     * Return all entries from the today table.
     * @return cursor with today table information.
     */
    public Cursor returnAllTodayEntries() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TODAY_TABLE;
        return db.rawQuery(query, null);
    }

    /**
     * Return information from the today stats table for current date.
     * @return cursor containing todas stats information.
     */
    public Cursor returnTodayStatsEntries() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TODAY_STATS_TABLE + " WHERE " + TODAY_STATS_KEY_DATE + " LIKE ?";
        return db.rawQuery(query, new String[]{String.valueOf(getDate())});
    }

    /**
     * Clear information from the today table.
     */
    public void clearToday(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TODAY_TABLE);
        db.close();
    }

    /**
     * Return all information from today stats table.
     * @return cursor with informatino from today stats.
     */
    public Cursor returnAllTodaysStats(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TODAY_STATS_TABLE;
        return db.rawQuery(query, null);
    }

    /**
     * Clear information from the today stats table.
     */
    public void clearTodayStats(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TODAY_STATS_TABLE);
        db.close();
    }

    public void clearTodayStats(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODAY_STATS_TABLE, TODAY_STATS_KEY_DATE + " = ?", new String[]{date});
        db.close();
    }

    public void clearToday(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODAY_TABLE, TODAY_KEY_DATE + " = ? ", new String[]{date});
        db.close();
    }

    public void deleteTodayEntry(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODAY_TABLE, TODAY_KEY_ID + " = ?", new String[]{id});
        db.close();
    }

    public void deleteTodayStatsEntry(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODAY_STATS_TABLE, TODAY_STATS_KEY_ID + " + ?", new String[]{id});
        db.close();
    }

    /**
     * Create the history table.
     * @param db database object.
     */
    public void createHistoryTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + HISTORY_TABLE + " ( "
                + HISTORY_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HISTORY_DATE + " TEXT, "
                + HISTORY_CALORIES + " TEXT, "
                + HISTORY_FAT + " TEXT, "
                + HISTORY_SAT_FAT + " TEXT, "
                + HISTORY_SALT + " TEXT, "
                + HISTORY_SODIUM + " TEXT, "
                + HISTORY_CARBS + " TEXT, "
                + HISTORY_SUGAR + " TEXT, "
                + HISTORY_PROTEIN + " TEXT)");
        Log.d(TAG, "createHistoryTable run");
    }

    public void createHistoryEntry(Food food){
        ContentValues cv = new ContentValues();
        cv.put(HISTORY_DATE, food.getDate());
        cv.put(HISTORY_CALORIES, food.getCalories());
        cv.put(HISTORY_FAT, food.getFats());
        cv.put(HISTORY_SAT_FAT, food.getSaturated_fat());
        cv.put(HISTORY_SALT, food.getSalt());
        cv.put(HISTORY_SODIUM, food.getSodium());
        cv.put(HISTORY_CARBS, food.getCarbohydrates());
        cv.put(HISTORY_SUGAR, food.getSugar());
        cv.put(HISTORY_PROTEIN, food.getProtein());

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "HISTORY TABLE INSERTED: " + db.insert(HISTORY_TABLE, null, cv));
    }

    /**
     * Return all history.
     * @return Cursor: history from the user.
     */
    public Cursor getHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + HISTORY_TABLE, null);
    }

    public Cursor getHistoryByDate(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + HISTORY_TABLE + " WHERE " + HISTORY_DATE + " LIKE '%" + query + "%'", null);
    }

    /**
     * Clear the history table
     */
    public void clearHistory(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + HISTORY_TABLE);
        Log.d(TAG, "History database cleared");
    }

    public void deleteHistoryItem(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HISTORY_TABLE, HISTORY_KEY_ID + " LIKE ?", new String[]{id});
        db.close();
    }

    /**
     * Create the goals table
     * @param db the database object
     */
    public void createGoalsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + GOALS_TABLE + "("
                + GOAL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GOAL_CALORIES + " TEXT, "
                + GOAL_FAT + " TEXT, "
                + GOAL_SATURATED_FAT + " TEXT, "
                + GOAL_SALT + " TEXT, "
                + GOAL_SODIUM + " TEXT, "
                + GOAL_CARBS + " TEXT, "
                + GOAL_SUGAR + " TEXT, "
                + GOAL_PROTEIN + " TEXT, "
                + GOAL_WEIGHT + " TEXT)");
        Log.d(TAG, "createGoalsTable run");
    }

    public void clearGoals(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + GOALS_TABLE);
        Log.d(TAG, "Goals table cleared");
    }

    /**
     * Set the users default goals, based on the recommended daily allowance
     */
    public void setDefaultGoals(String desiredWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GOAL_CALORIES, "2000");
        cv.put(GOAL_FAT, "65");
        cv.put(GOAL_SATURATED_FAT, "20");
        cv.put(GOAL_SALT, "5");
        cv.put(GOAL_SODIUM, "2.4");
        cv.put(GOAL_CARBS, "300");
        cv.put(GOAL_SUGAR, "160");
        cv.put(GOAL_PROTEIN, "50");
        cv.put(GOAL_WEIGHT, desiredWeight);

        Log.d(TAG, "Default goals set: " + db.insert(GOALS_TABLE, null, cv));
    }

    /**
     * Update the goals table
     * @param goal object containing information about the goals
     */
    public int updateGoals(Goals goal){
        ContentValues cv = new ContentValues();
        cv.put(GOAL_CALORIES, goal.getCalories());
        cv.put(GOAL_FAT, goal.getFat());
        cv.put(GOAL_SATURATED_FAT, goal.getSaturatedFat());
        cv.put(GOAL_SALT, goal.getSalt());
        cv.put(GOAL_SODIUM, goal.getSodium());
        cv.put(GOAL_CARBS, goal.getCarbohydrates());
        cv.put(GOAL_SUGAR, goal.getSugar());
        cv.put(GOAL_PROTEIN, goal.getProtein());
        cv.put(GOAL_WEIGHT, goal.getWeight());

        String selection = GOAL_KEY_ID + " LIKE ?";
        String[] selectionArgs = {"1"};
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(GOALS_TABLE, cv, selection, selectionArgs);
    }

    public Cursor getGoals() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + GOALS_TABLE, null);
    }

    /**
     * Create the weight history database.
     * @param db database.
     */
    public void createWeightHistoryTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + WEIGHT_HISTORY_TABLE + " ( "
            + WEIGHT_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + WEIGHT_DATE + " TEXT, "
            + WEIGHT_TOTAL + " TEXT, "
            + WEIGHT_CHANGE + " TEXT)");
        Log.d(TAG, "createWeightHistoryTable - run");
    }

    public void logWeight(String date, String total, String change){
        ContentValues cv = new ContentValues();
        cv.put(WEIGHT_DATE, date);
        cv.put(WEIGHT_TOTAL, total);
        cv.put(WEIGHT_CHANGE, change);

        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG, "Weight logged: ID: " + db.insert(WEIGHT_HISTORY_TABLE, null, cv) + " DATE: " + date + " TOTAL: " + total + " CHANGE: " + change);
    }

    public Cursor getWeightInformation(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + WEIGHT_HISTORY_TABLE, null);
    }

    public void deleteWeight(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WEIGHT_HISTORY_TABLE, WEIGHT_KEY_ID + " + ?", new String[]{id});
        db.close();
    }

    public void clearWeightTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + WEIGHT_HISTORY_TABLE);
        Log.d(TAG, "Weight table cleared");
    }

    public void createQuery100Table(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + QUERY100_TABLE + "("
            + QUERY100_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUERY100_BARCODE + " TEXT, "
            + QUERY100_CALORIES + " TEXT, "
            + QUERY100_FAT + " TEXT, "
            + QUERY100_SAT_FAT + " TEXT, "
            + QUERY100_SALT + " TEXT, "
            + QUERY100_SODIUM + " TEXT, "
            + QUERY100_CARBS + " TEXT, "
            + QUERY100_SUGAR + " TEXT, "
            + QUERY100_PROTEIN + " TEXT)");
        Log.d(TAG, "createQuery100Table run");
    }

    public void insertIntoQuery100(Food food){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues query = new ContentValues();
        query.put(QUERY100_BARCODE, food.getBarcode_number());
        query.put(QUERY100_CALORIES, food.getCalories());
        query.put(QUERY100_FAT, food.getFats());
        query.put(QUERY100_SAT_FAT, food.getSaturated_fat());
        query.put(QUERY100_SALT, food.getSalt());
        query.put(QUERY100_SODIUM, food.getSodium());
        query.put(QUERY100_CARBS, food.getCarbohydrates());
        query.put(QUERY100_SUGAR, food.getSugar());
        query.put(QUERY100_PROTEIN, food.getProtein());

        db.insert(QUERY100_TABLE, null, query);
    }

    public Cursor getQuery100Information(String barcode){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + QUERY100_TABLE + " WHERE " + QUERY100_BARCODE + " LIKE ?";
        return db.rawQuery(query, new String[]{barcode});
    }

    public void createQueryServingTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + QUERYSERVING_TABLE + "("
                + QUERYSERVING_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUERYSERVING_BARCODE + " TEXT, "
                + QUERYSERVING_CALORIES + " TEXT, "
                + QUERYSERVING_FAT + " TEXT, "
                + QUERYSERVING_SAT_FAT + " TEXT, "
                + QUERYSERVING_SALT + " TEXT, "
                + QUERYSERVING_SODIUM + " TEXT, "
                + QUERYSERVING_CARBS + " TEXT, "
                + QUERYSERVING_SUGAR + " TEXT, "
                + QUERYSERVING_PROTEIN + " TEXT)");
        Log.d(TAG, "createQueryServingTable run");
    }

    public void insertIntoQueryServing(Food food){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues query = new ContentValues();
        query.put(QUERYSERVING_BARCODE, food.getBarcode_number());
        query.put(QUERYSERVING_CALORIES, food.getCalories());
        query.put(QUERYSERVING_FAT, food.getFats());
        query.put(QUERYSERVING_SAT_FAT, food.getSaturated_fat());
        query.put(QUERYSERVING_SALT, food.getSalt());
        query.put(QUERYSERVING_SODIUM, food.getSodium());
        query.put(QUERYSERVING_CARBS, food.getCarbohydrates());
        query.put(QUERYSERVING_SUGAR, food.getSugar());
        query.put(QUERYSERVING_PROTEIN, food.getProtein());

        db.insert(QUERYSERVING_TABLE, null, query);
    }

    /**
     * Check if the food item exists in the database.
     * @param barcode the barcode to be checked.
     * @return boolean: true if does not exists, false otherwise.
     */
    public boolean checkIfItemExists(String barcode){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + QUERY100_TABLE + " WHERE " + QUERY100_BARCODE + " LIKE ?";
        Cursor res = db.rawQuery(query, new String[]{barcode});
        return res.getCount() < 1;
    }

    public String getDate() {
        Calendar c = Calendar.getInstance();
        String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(c.get(Calendar.MONTH) + 1);
        String year = Integer.toString(c.get(Calendar.YEAR));

        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        weekDay = dayFormat.format(c.getTime());

        if(day.length() < 2) {
            String temp = day;
            day = "0" + temp;
        }
        if(month.length() < 2) {
            String temp = month;
            month = "0" + temp;
        }

        return weekDay.substring(0, 3).toUpperCase() + day + month + year;
    }
}
