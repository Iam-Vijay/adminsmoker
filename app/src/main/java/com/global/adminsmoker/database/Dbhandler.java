package com.global.adminsmoker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.global.adminsmoker.Pojopack1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dbhandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Admindetails.db";
    private static final String TABLE_NAME = "admindetails";
    private static final String ID_TABLE = "id";
    private static final String DATE = "date";
    private static final String TOTALCOUNT = "totalcount";

    public Dbhandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE + " TEXT,"
                + TOTALCOUNT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void adddateandtotal(String date, String totalcount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(TOTALCOUNT, totalcount);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public String getLastAddedcount() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
        String lasttime = null;
        if(cursor.moveToLast()){
            lasttime=cursor.getString(2);

            // cc.setLvallue(cursor.getString(6));
            // cc.setBvalue(cursor.getString(7));
            // cc.setWvalue(cursor.getString(8));
            //--get other cols values
        }
        db.close();
        cursor.close();
        return lasttime ;
    }

    public long gettodaytotalCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }
    public Cursor get1month() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE " +ID_TABLE+ " ORDER BY Id DESC LIMIT 30 ";
        Cursor cursor=db.rawQuery(selectQuery,null);
        return cursor;
    }
    public Cursor get7dates() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE " +ID_TABLE+ " ORDER BY Id DESC LIMIT 7 ";
        Cursor cursor=db.rawQuery(selectQuery,null);
        return cursor;
    }
    public Cursor get3dates() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE " +ID_TABLE+ " ORDER BY Id DESC LIMIT 3 ";
        Cursor cursor=db.rawQuery(selectQuery,null);
        return cursor;
    }
}