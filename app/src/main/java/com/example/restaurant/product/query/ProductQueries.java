package com.example.restaurant.product.query;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurant.utils.tables.ProductTableHelper;

public class ProductQueries {

    public static void addNewProduct(String productName, String productDescription, Float productPrice, Integer productQuantity, SQLiteDatabase db) {

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(ProductTableHelper.NAME_COL, productName);
        values.put(ProductTableHelper.DESCRIPTION_COL, productDescription);
        values.put(ProductTableHelper.PRICE_COL, productPrice);
        values.put(ProductTableHelper.QUANTITY_COL, productQuantity);

        // after adding all values we are passing
        // content values to our table.
        db.insert(ProductTableHelper.TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }
}
