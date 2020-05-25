package com.example.patanjali;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "patanjali.db";
    public static final String TABLE_NAME1 = "item_details";
    public static final String COL1 = "id";
    public static final String COL2 = "barcode";
    public static final String COL3 = "name";
    public static final String COL4 = "price";
    public static final String COL5 = "quantity";

    public static final String TABLE_NAME2 = "sold";
    public static final String COL21 = "id";
    public static final String COL22 = "barcode";
    public static final String COL23 = "name";
    public static final String COL24 = "quantity";

    public database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME1 + " (id INTEGER PRIMARY KEY AUTOINCREMENT,barcode TEXT,name TEXT,price TEXT,quantity TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME2 + " (id INTEGER PRIMARY KEY AUTOINCREMENT,barcode TEXT,name TEXT,quantity TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    public boolean insert1(SQLiteDatabase db, String barcode, String name, String price, String quantity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, barcode);
        contentValues.put(COL3, name);
        contentValues.put(COL4, price);
        contentValues.put(COL5, quantity);
        long x = db.insert(TABLE_NAME1, null, contentValues);
        if (x == -1) {
            return false;
        }
        return true;
    }
    public boolean update1(SQLiteDatabase db,String barcode,String quantity){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL5,quantity);
        long re=db.update(TABLE_NAME1,
                contentValues,
                "barcode" + " = ? ",
                new String[]{barcode});
        if(re == -1)
            return false;
        else
            return true;
    }
    public Cursor getitemdetails(SQLiteDatabase db,String barcode) {
        Cursor cursor = db.query(TABLE_NAME1, new String[]{"name", "price","quantity"}, "barcode=?", new String[]{barcode}, null, null, null);
        return cursor;
    }
}
