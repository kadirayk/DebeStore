package com.kadirayk.debestore.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kadiray on 12.04.2015.
 */
public class AppController extends Application{

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static synchronized AppController getInstance(){
        return mInstance;
    }

    public static int getDebeListCount(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("AppInfo", MODE_PRIVATE);
        int debeListCount = mPrefs.getInt("DebeListCount", 0);
        return debeListCount;
    }

    public static void storeDebeListCount(Context context, int debeListCount){
        SharedPreferences mPrefs = context.getSharedPreferences("AppInfo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt("DebeListCount", debeListCount);
        mEditor.commit();
    }

    public static boolean getIfTodaysDebeListStored(Context context, String today){
        SharedPreferences mPrefs = context.getSharedPreferences("AppInfo", MODE_PRIVATE);
        boolean isStored = mPrefs.getString("IfTodaysDebeListStored", "").equals(today);
        return isStored;
    }

    public static void storeIfTodaysDebeListStored(Context context, String today){
        SharedPreferences mPrefs = context.getSharedPreferences("AppInfo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("IfTodaysDebeListStored", today);
        mEditor.commit();
    }

    public static String getLastDate(Context context){
        SharedPreferences mPreferences = context.getSharedPreferences("AppInfo", MODE_PRIVATE);
        String lastDate = mPreferences.getString("lastDate", "");
        return lastDate;
    }

    public static void storeLastDate(Context context, String lastDate){
        SharedPreferences mPreferences = context.getSharedPreferences("AppInfo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPreferences.edit();
        mEditor.putString("lastDate", lastDate);
        mEditor.commit();
    }

    public static String getSystemDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }


}
