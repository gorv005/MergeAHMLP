package com.dartmic.mergeahmlp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static android.support.constraint.Constraints.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Constants for Database name, table name, and column names
    public static final String DB_NAME = "NamesDB";
    public static final String TABLE_NAME = "names";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STATUS = "status";


    public static final String TABLE_LOCATION = "location";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LONG = "longi";
    public static final String COLUMN_TIME = "tempo";
    public static final String COLUMN_OF = "of_id";


    public static final String TABLE_NAAM = "startDay";
    public static final String LAT = "lat";
    public static final String LONG = "longi";
    public static final String COLUMN_SAMAY = "tempo";
    public static final String p_id = "of_id";


    public static final String TABLE_N = "stopDay";
    public static final String LATi = "lat";
    public static final String LONGi = "longi";
    public static final String COLUMN_SAMAYi = "tempo";
    public static final String p_idi = "of_id";


    //database version
    private static final int DB_VERSION = 1;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME +
                " VARCHAR, " + COLUMN_STATUS +
                " TINYINT);";

        String sql1 = "CREATE TABLE " + TABLE_LOCATION
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_OF + " VARCHAR, " +
                COLUMN_LAT + " VARCHAR, " +
                COLUMN_LONG + " VARCHAR, " +
                COLUMN_TIME + " DATETIME DEFAULT (datetime('now','localtime')), " +
                COLUMN_STATUS + " TINYINT);";


        String sql11 = "CREATE TABLE " + TABLE_NAAM
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                p_id + " VARCHAR, " +
                LAT + " VARCHAR, " +
                LONG + " VARCHAR, " +
                COLUMN_SAMAY + " DATETIME DEFAULT (datetime('now','localtime')), " +
                COLUMN_STATUS + " TINYINT);";


        String sql111 = "CREATE TABLE " + TABLE_N
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                p_idi + " VARCHAR, " +
                LATi + " VARCHAR, " +
                LONGi + " VARCHAR, " +
                COLUMN_SAMAYi + " DATETIME DEFAULT (datetime('now','localtime')), " +
                COLUMN_STATUS + " TINYINT);";


        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(sql11);
        db.execSQL(sql111);
    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Persons";
        db.execSQL(sql);
        onCreate(db);
    }

    /*
     * This method is taking two arguments
     * first one is the name that is to be saved
     * second one is the status
     * 0 means the name is synced with the server
     * 1 means the name is not synced with the server
     * */
    public boolean addName(String name, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_STATUS, status);

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public long addTrack(String who, String lat, String langi, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_OF, who);
        contentValues.put(COLUMN_LAT, lat);
        contentValues.put(COLUMN_LONG, langi);
        contentValues.put(COLUMN_STATUS, status);

        long id = db.insert(TABLE_LOCATION, null, contentValues);
        db.close();
        return id;
    }


    public long startDay(String wh, String role, String mkt_id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(p_id, wh);
        contentValues.put(LAT, role);
        contentValues.put(LONG, mkt_id);
        contentValues.put(COLUMN_STATUS, status);

        long id = db.insert(TABLE_NAAM, null, contentValues);
        db.close();
        return id;
    }


    public long stopDay(String wh, String role, String mkt_id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(p_id, wh);
        contentValues.put(LAT, role);
        contentValues.put(LONG, mkt_id);
        contentValues.put(COLUMN_STATUS, status);

        long id = db.insert(TABLE_N, null, contentValues);
        db.close();
        return id;
    }


    /*
     * This method taking two arguments
     * first one is the id of the name for which
     * we have to update the sync status
     * and the second one is the status that will be changed
     * */
    public boolean updateNameStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }


    public boolean updateTrackStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_LOCATION, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }


    public boolean updateStartDay(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAAM, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }

public boolean updateStopDay(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_N, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }


    /*
     * this method will give us all the name stored in sqlite
     * */
    public Cursor getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getTrack() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_LOCATION + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }


    public Cursor getStartDay() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAAM + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }


    /*
     * this method is for getting all the unsynced name
     * so that we can sync it with database
     * */
    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getUnsyncedTracks() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_LOCATION + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getUnsyncedStartDay() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAAM + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getUnsyncedStopDay() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_N + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

}
