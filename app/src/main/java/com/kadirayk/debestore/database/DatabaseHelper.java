package com.kadirayk.debestore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kadiray on 12.04.2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String TABLE_DEBE = "debes";
    public static final String DEBE_COLUMN_ID = "_id";
    public static final String DEBE_COLUMN_PLACE = "place";
    public static final String DEBE_COLUMN_TITLE = "title";
    public static final String DEBE_COLUMN_AUTHOR = "author";
    public static final String DEBE_COLUMN_URL = "url";
    public static final String DEBE_COLUMN_DATE = "date";

    public static final String DATABASE_NAME = "YMLE.db";
    public static final int DATABASE_VERSION = 1;

    //Database creation statement
    private static final String CREATE_TABLE_DEBE = "create table " + TABLE_DEBE
            + "(" + DEBE_COLUMN_ID + " integer primary key autoincrement, "
            + DEBE_COLUMN_PLACE + " integer not null, "
            + DEBE_COLUMN_TITLE + " text not null, "
            + DEBE_COLUMN_AUTHOR + " text not null, "
            + DEBE_COLUMN_URL + " text not null, "
            + DEBE_COLUMN_DATE + " text not null);";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DEBE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEBE);
        onCreate(db);
    }
}
