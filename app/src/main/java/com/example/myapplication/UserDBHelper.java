package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {
    public UserDBHelper(Context context) {
        super(context, "UserPasswords.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserInfo(name TEXT , contact TEXT primary key, dob TEXT, inuse TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists UserInfo");
    }
    public Boolean insertuserdata(String name, String contact, String dob, String inuse)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
        contentValues.put("inuse", inuse);
        long result=DB.insert("UserInfo", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean updateuserdata(String name, String contact, String dob, String inuse)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("dob", dob);
        contentValues.put("inuse", inuse);
        Cursor cursor = DB.rawQuery("Select * from UserInfo where contact = ?", new String[]{contact});
        if (cursor.getCount() > 0) {
            long result = DB.update("UserInfo", contentValues, "contact=?", new String[]{contact});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Boolean deletedata (String contact)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserInfo where contact = ?", new String[]{contact});
        if (cursor.getCount() > 0) {
            long result = DB.delete("UserInfo", "contact=?", new String[]{contact});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    /*
        public Cursor getdata ()
        {
            SQLiteDatabase DB = this.getWritableDatabase();
            Cursor cursor = DB.rawQuery("Select * from UserInfo", null);
            return cursor;
        }
        public Cursor getcertaindata (String inuse)
        {
            SQLiteDatabase DB = this.getWritableDatabase();
            Cursor cursor = DB.rawQuery("Select * from UserInfo where inuse = ?", new String[]{inuse});
            return cursor;
        }

     */
    public Cursor getlogindata (String contact)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserInfo where contact = ?", new String[]{contact});
        return cursor;
    }
    public Cursor checklogindata (String dob)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserInfo where dob = ?", new String[]{dob});
        return cursor;
    }

}
