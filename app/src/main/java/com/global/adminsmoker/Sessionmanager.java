package com.global.adminsmoker;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;

public class Sessionmanager {
    private static String TAG = Sessionmanager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Shared preferences file name

    private static final String PREF_NAME = "AndroidLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";



    public Sessionmanager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }


    public void savetodaytotal(String key, String value) {

        editor.putString(key, value);

        editor.commit();
    }


    public void savelasttime(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }

    public void savename(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void savebrand(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void saveamount(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }

    public String getValue(String key) {

        return pref.getString(key, null);

    }
    public void clearvalue(){
        editor.clear();
        editor.commit();
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

}