package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class ItemsDBHelper extends SQLiteOpenHelper {

    public ItemsDBHelper(Context context) {
        super(context, "Items.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table ItemsTable(Barcode TEXT primary key , item_name TEXT, price TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists ItemsTable");
    }

    public Boolean additem(String barcode, String item_name, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Barcode", barcode);
        cv.put("item_name", item_name);
        cv.put("price", price);
        long result=db.insert("ItemsTable", null, cv);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteitem(String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from ItemsTable where Barcode = ?", new String[]{barcode});
        if (cursor.getCount() > 0) {
            long result = db.delete("ItemsTable", "Barcode=?", new String[]{barcode});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Cursor getCertainItem (String barcode)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from ItemsTable where Barcode = ?", new String[]{barcode});
        return cursor;
    }
    public Cursor getItems ()
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        String[] projection = {"Barcode", "item_name", "price"};
        String sortOrder = "item_name" + " ASC";
        Cursor cursor = DB.query("ItemsTable", projection, null, null, null, null, sortOrder);
        return cursor;
    }

}
