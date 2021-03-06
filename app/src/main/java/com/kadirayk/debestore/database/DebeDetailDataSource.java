package com.kadirayk.debestore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kadirayk.debestore.model.DebeDetailItem;

import java.util.ArrayList;

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
                allColumns, DatabaseHelper.DEBE_DETAIL_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        DebeDetailItem debeDetailItem = null; //TODO = cursorTODebeDetailItem(cursor);
        cursor.close();
        return debeDetailItem;
    }

    public void deleteDebeDetail(DebeDetailItem debeDetailItem){
        long id = debeDetailItem.getId();
        database.delete(DatabaseHelper.TABLE_DEBE_DETAIL, DatabaseHelper.DEBE_DETAIL_COLUMN_ID
            + " = " + id, null);
    }

    public ArrayList<DebeDetailItem> getAllDebeDetailItems(){
        ArrayList<DebeDetailItem> debeDetailItems = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_DEBE_DETAIL,
                allColumns, null, null, null, null, DatabaseHelper.DEBE_DETAIL_COLUMN_PLACE + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DebeDetailItem debeDetailItem = cursorToDebeDetailItem(cursor);
            debeDetailItems.add(debeDetailItem);
            cursor.moveToNext();
        }

        cursor.close();
        return debeDetailItems;
    }

    public ArrayList<DebeDetailItem> getAllDebeDetailItemsByDate(String date){
        ArrayList<DebeDetailItem> debeDetailItems = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM "
                + DatabaseHelper.TABLE_DEBE_DETAIL + " WHERE " + DatabaseHelper.DEBE_DETAIL_COLUMN_DATE + " = '" + date
                + "' ORDER BY " + DatabaseHelper.DEBE_DETAIL_COLUMN_PLACE + " ASC ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DebeDetailItem debeDetailItem = cursorToDebeDetailItem(cursor);
            debeDetailItems.add(debeDetailItem);
            cursor.moveToNext();
        }

        cursor.close();
        return debeDetailItems;
    }

    private DebeDetailItem cursorToDebeDetailItem(Cursor cursor){
        DebeDetailItem debeDetailItem = new DebeDetailItem(0, 0, "", "", "", "", "");
        debeDetailItem.setId(cursor.getLong(0));
        debeDetailItem.setParentId(cursor.getLong(1));
        debeDetailItem.setPlace(cursor.getInt(2));
        debeDetailItem.setTitle(cursor.getString(3));
        debeDetailItem.setAuthor(cursor.getString(4));
        debeDetailItem.setUrl(cursor.getString(5));
        debeDetailItem.setContent(cursor.getString(6));
        debeDetailItem.setDate(cursor.getString(7));
        return null;
    }


}
