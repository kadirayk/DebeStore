package com.kadirayk.debestore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kadiray on 16.04.2015.
 */
public class DebeDetailDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { DatabaseHelper.DEBE_DETAIL_COLUMN_ID,
            DatabaseHelper.DEBE_DETAIL_COLUMN_PARENT_ID,
            DatabaseHelper.DEBE_DETAIL_COLUMN_PLACE,
            DatabaseHelper.DEBE_DETAIL_COLUMN_TITLE,
            DatabaseHelper.DEBE_DETAIL_COLUMN_AUTHOR,
            DatabaseHelper.DEBE_DETAIL_COLUMN_URL,
            DatabaseHelper.DEBE_DETAIL_COLUMN_CONTENT,
            DatabaseHelper.DEBE_DETAIL_COLUMN_DATE };

    public DebeDetailDataSource(Context context) {
        dbHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }
}
