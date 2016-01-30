package com.karl.fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.karl.models.Food;

import java.util.Calendar;

/**
 * Database Class
 *
 * Copyright Karl Jones 2016
 */
public class MySQLiteHelper extends SQLiteOpenHelper{

    // Common
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "diet.db";
    private static final String USER_TABLE = "user";
    private static final String TODAY_TABLE = "today";
    private static final String TODAY_STATS_TABLE = "todaystats";
    private static final String HISTORY_TABLE = "history";
    private static final String WEIGHT_HISTORY_TABLE = "weighthistory";
    private static final String GOALS_TABLE = "goals";

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


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);

        createTodayTable(db);

        createTodayStatsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TODAY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TODAY_STATS_TABLE);
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
        System.out.println("MySQLiteHelper: createUserTable() - run");
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
        return db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + USER_KEY_ID + " = 1", null);
    }

    /**
     * Clear all of the user data.
     */
    public void clearAllUser() {
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
        System.out.println("MySQLiteHelper: createTodayTable() - run");
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

    public void createNewEntryToday(Food food) {
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
        today_stats.put(TODAY_STATS_KEY_SATURATED_FAT, food.getSat_fats());
        today_stats.put(TODAY_STATS_KEY_CARBOHYDRATES, food.getCarbs());
        today_stats.put(TODAY_STATS_KEY_SUGAR, food.getSugar());
        today_stats.put(TODAY_STATS_KEY_PROTEIN, food.getProtein());
        today_stats.put(TODAY_STATS_KEY_SALT, food.getSalt());
        today_stats.put(TODAY_STATS_KEY_SODIUM, food.getSodium());

        db.insert(TODAY_STATS_TABLE, null, today_stats);

        System.out.println("DATE: " + getDate() + "\nFOOD NAME: " + food.getName() + "\nBARCODE NUMBER: " + food.getBarcode_number() + "\nCALORIES: " +
        food.getCalories() + "\nFATS: " + food.getFats() + "\nSATURATED FATS: " + food.getSat_fats() + "\nCARBOHYDRATES: " + food.getCarbs() +
        "\nSUGAR: " + food.getSugar() + "\nPROTEIN: " + food.getProtein() + "\nSALT: " + food.getSalt() + "\nSODIUM: " + food.getSodium() );
    }

    public Cursor returnTodaysEntries() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TODAY_TABLE + " WHERE " + TODAY_KEY_DATE + " LIKE ?";
        return db.rawQuery(query, new String[]{String.valueOf(getDate())});
    }

    /**
     *
     * @return cursor containing todats stats
     */
    public Cursor returnTodayStatsEntries() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TODAY_STATS_TABLE + " WHERE " + TODAY_STATS_KEY_DATE + " LIKE ?";
        return db.rawQuery(query, new String[]{String.valueOf(getDate())});
    }

    /*
    Create the History table
     */
    public void createHistoryTable() {

    }

    /*
    Create the Goals table
     */
    public void createGoalsTable() {

    }

    /*
    Create the Weight_History table
     */
    public void createWeightHistoryTable() {

    }

    public String getDate() {
        Calendar c = Calendar.getInstance();
        String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(c.get(Calendar.MONTH) + 1);
        String year = Integer.toString(c.get(Calendar.YEAR));

        if(day.length() < 2) {
            String temp = day;
            day = "0" + temp;
        }
        if(month.length() < 2) {
            String temp = month;
            month = "0" + temp;
        }

        System.out.println("Date: " + day + month + year);
        return day + month + year;
    }
}
