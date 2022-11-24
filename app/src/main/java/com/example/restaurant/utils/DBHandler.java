package com.example.restaurant.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.restaurant.utils.tables.ProductTableHelper;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "restaurant";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + ProductTableHelper.TABLE_NAME + " ("
                + ProductTableHelper.ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductTableHelper.NAME_COL + " TEXT,"
                + ProductTableHelper.DESCRIPTION_COL + " TEXT,"
                + ProductTableHelper.PRICE_COL + " REAL,"
                + ProductTableHelper.QUANTITY_COL + " INTEGER)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + ProductTableHelper.TABLE_NAME);
        onCreate(db);
    }
}
