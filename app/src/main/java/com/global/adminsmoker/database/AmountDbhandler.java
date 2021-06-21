package com.global.adminsmoker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AmountDbhandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "amountdetails.db";
    private static final String TABLE_NAME = "todayamount";
    private static final String ID_TABLE = "id";
    private static final String ID_Amount = "amount";

    public AmountDbhandler ( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_Amount + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
    public void addamount(String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ID_Amount,amount);
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }
    public ArrayList<String> getAllContacts() {
        ArrayList<String> contactList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
               contactList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return contactList;
    }
    public String getLastAddedRow() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
        String lasttime = null;
        if(cursor.moveToLast()){
            lasttime=cursor.getString(0);

            // cc.setLvallue(cursor.getString(6));
            // cc.setBvalue(cursor.getString(7));
            // cc.setWvalue(cursor.getString(8));
            //--get other cols values
        }cursor.close();
        return lasttime ;
    }
    public double gettotal()
    {
        ArrayList<String> contactList = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                contactList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        double sum = 0;
        for(int i = 0; i < contactList.size(); i++)
        {
            sum += Double.parseDouble(contactList.get(i));
        }
        db.close();
        return sum;
    }
    public void clearvalues(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }
    public void deletelastid(){
        SQLiteDatabase db=this.getWritableDatabase();
        String maxid = ID_TABLE + "=" +
                "(SELECT MAX("+ID_TABLE+") FROM " + TABLE_NAME + ")";
        db.delete(TABLE_NAME,maxid,null);
        db.close();
    }
    public String getLastAddedRowamount() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
        String lasttime = null;
        if(cursor.moveToLast()){
            lasttime=cursor.getString(2);
            // cc.setLvallue(cursor.getString(6));
            // cc.setBvalue(cursor.getString(7));
            // cc.setWvalue(cursor.getString(8));
            //--get other cols values
        }cursor.close();
        db.close();
        return lasttime ;

    }
}
