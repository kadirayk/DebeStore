package com.kadirayk.debestore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.kadirayk.debestore.models.DebeDetailItem;

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

    public DebeDetailItem createDebeDetail(int parentId, int place, String title, String author, String url, String content, String date){
        title = title.trim();
        author = author.trim();
        url = url.trim();
        date = date.trim();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.DEBE_DETAIL_COLUMN_PARENT_ID, parentId);
        values.put(DatabaseHelper.DEBE_DETAIL_COLUMN_PLACE, place);
        values.put(DatabaseHelper.DEBE_DETAIL_COLUMN_TITLE, title);
        values.put(DatabaseHelper.DEBE_DETAIL_COLUMN_AUTHOR, author);
        values.put(DatabaseHelper.DEBE_DETAIL_COLUMN_URL, url);
        values.put(DatabaseHelper.DEBE_DETAIL_COLUMN_CONTENT, content);
        values.put(DatabaseHelper.DEBE_DETAIL_COLUMN_DATE, date);

        long insertId = database.insert(DatabaseHelper.TABLE_DEBE_DETAIL, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_DEBE_DETAIL,
                allColumns, DatabaseHelper.DEBE_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        DebeDetailItem debeDetailItem = null; //TODO = cursorTODebeDetailItem(cursor);
        cursor.close();
        return debeDetailItem;
    }
}
