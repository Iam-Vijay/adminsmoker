package com.global.adminsmoker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TodayDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todaydetails.db";
    private static final String TABLE_NAME = "todaydetails";
    private static final String ID_TABLE = "id";
    private static final String ID_Date = "date";
    private static final String Time = "time";
    public TodayDbHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_Date + " TEXT,"
                + Time + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
    public void addtime(String time,String date){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Time,time);
        contentValues.put(ID_Date,date);
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }
    public long gettodaytotalCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }
    public void clearvalues(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }
    public String getLastAddedRowtime() {
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
    public String getLastAddedRowdate() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
        String lasttime = null;
        if(cursor.moveToLast()){
            lasttime=cursor.getString(1);

            // cc.setLvallue(cursor.getString(6));
            // cc.setBvalue(cursor.getString(7));
            // cc.setWvalue(cursor.getString(8));
            //--get other cols values
        }cursor.close();
        db.close();
        return lasttime ;
    }
    public void deletelastid(){
                SQLiteDatabase db=this.getWritableDatabase();
        String maxid = ID_TABLE + "=" +
                "(SELECT MAX("+ID_TABLE+") FROM " + TABLE_NAME + ")";
            db.delete(TABLE_NAME,maxid,null);
            db.close();
    }
}
