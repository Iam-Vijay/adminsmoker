package com.global.adminsmoker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyAdminAmount extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "adminamount.db";
    private static final String TABLE_NAME = "totalamount";
    private static final String ID_TABLE = "id";
    private static final String ID_DATE = "date";
    private static final String ID_Amount = "amount";

    public MyAdminAmount(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_DATE + " TEXT,"
                + ID_Amount + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void addamount(String date, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_Amount, amount);
        contentValues.put(ID_DATE, date);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public String getLastAddedRowamount() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
        String lasttime = null;
        if (cursor.moveToLast()) {
            lasttime = cursor.getString(2);
            // cc.setLvallue(cursor.getString(6));
            // cc.setBvalue(cursor.getString(7));
            // cc.setWvalue(cursor.getString(8));
            //--get other cols values
        }
        db.close();
        cursor.close();
        return lasttime;

    }
    public String getLastAddedRowDate() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
        String lasttime = null;
        if (cursor.moveToLast()) {
            lasttime = cursor.getString(1);
            // cc.setLvallue(cursor.getString(6));
            // cc.setBvalue(cursor.getString(7));
            // cc.setWvalue(cursor.getString(8));
            //--get other cols values
        }
        db.close();
        cursor.close();
        return lasttime;

    }
    public double gettotal() {
        ArrayList<String> contactList = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                contactList.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        double sum = 0;
        for (int i = 0; i < contactList.size(); i++) {
            sum += Double.parseDouble(contactList.get(i));
        }
        return sum;
    }
}