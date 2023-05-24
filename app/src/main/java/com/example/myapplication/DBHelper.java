package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Item.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL("create Table ItemTable(Barcode TEXT primary key, item_name TEXT, price TEXT, user TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists ItemTable");
    }
    public Boolean insertuserdata(String Barcode, String item_name, String price, String user)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Barcode", Barcode);
        contentValues.put("item_name", item_name);
        contentValues.put("price", price);
        contentValues.put("user", user);
        long result=DB.insert("ItemTable", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean deletedata (String barcode)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from ItemTable where Barcode = ?", new String[]{barcode});
        if (cursor.getCount() > 0) {
            long result = DB.delete("ItemTable", "Barcode=?", new String[]{barcode});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata (String user)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from ItemTable where user = ?", new String[]{user});
        return cursor;
    }
    public Cursor getcertaindata (String barcode)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from ItemTable where barcode = ?", new String[]{barcode});
        return cursor;
    }
}
