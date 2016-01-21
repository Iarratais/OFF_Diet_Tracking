package android.karl.fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database Class
 *
 * Copyright Karl Jones 2016
 */
public class MySQLiteHelper extends SQLiteOpenHelper{

    // Common
    private static final int DATABASE_VERSION = 3;
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
    private static final String TODAY_KEY_DAY_ID = "day_id";
    private static final String TODAY_KEY_USER_ID = "user_id";
    private static final String TODAY_KEY_DATE = "date";
    private static final String TODAY_KEY_FOOD_NAME = "food_name";
    private static final String TODAY_KEY_BARCODE_NO = "barcode_number";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create the user table
        createUserTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    /*
    Create the User table
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

    // Only to be called once before being removed
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

    public Cursor getAllDataUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + USER_TABLE, null);
        db.close();
        return res;
    }

    public Cursor getUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + USER_KEY_ID + " = 1", null);
    }

    public void clearAllUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + USER_TABLE);
        db.close();
    }

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
    public void createTodayTable() {

    }


    /*
    Create the Today_stats table
     */
    public void createTodayStatsTable() {

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
}
