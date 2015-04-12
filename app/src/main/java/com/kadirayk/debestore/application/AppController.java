package com.kadirayk.debestore.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

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


}
