package com.kadirayk.debestore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kadirayk.debestore.models.DebeListItem;

import java.util.ArrayList;

/**
 * Created by Kadiray on 12.04.2015.
 */
public class DebeDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { DatabaseHelper.DEBE_COLUMN_ID,
            DatabaseHelper.DEBE_COLUMN_PLACE,
            DatabaseHelper.DEBE_COLUMN_TITLE,
            DatabaseHelper.DEBE_COLUMN_AUTHOR,
            DatabaseHelper.DEBE_COLUMN_URL,
            DatabaseHelper.DEBE_COLUMN_DATE };

    public DebeDataSource(Context context) {
        dbHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public DebeListItem createDebe(int place, String title, String author, String url, String date) {
        title = title.trim();
        author = author.trim();
        url = url.trim();
        date = date.trim();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.DEBE_COLUMN_PLACE, place);
        values.put(DatabaseHelper.DEBE_COLUMN_TITLE, title);
        values.put(DatabaseHelper.DEBE_COLUMN_AUTHOR, author);
        values.put(DatabaseHelper.DEBE_COLUMN_URL, url);
        values.put(DatabaseHelper.DEBE_COLUMN_DATE, date);

        long insertId = database.insert(DatabaseHelper.TABLE_DEBE, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_DEBE,
                allColumns, DatabaseHelper.DEBE_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        DebeListItem newDebe = cursorToDebeListItem(cursor);
        cursor.close();
        return newDebe;
    }

    public void deleteDebe(DebeListItem debeListItem) {
        long id = debeListItem.getId();
        System.out.println("debe deleted with id: " + id);
        database.delete(DatabaseHelper.TABLE_DEBE, DatabaseHelper.DEBE_COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<DebeListItem> getAllDebeListItems() {
        ArrayList<DebeListItem> debeList = new ArrayList<DebeListItem>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_DEBE,
                allColumns, null, null, null, null, DatabaseHelper.DEBE_COLUMN_PLACE + " ASC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DebeListItem debeListItem = cursorToDebeListItem(cursor);
            debeList.add(debeListItem);
            cursor.moveToNext();
        }

        cursor.close();
        return debeList;
    }

    public ArrayList<DebeListItem> getAllDebeListItemsByDate(String date) {
        ArrayList<DebeListItem> debeList = new ArrayList<DebeListItem>();

        Cursor cursor = database.rawQuery("SELECT * FROM "
                + DatabaseHelper.TABLE_DEBE + " WHERE " + DatabaseHelper.DEBE_COLUMN_DATE + " = '" + date
                + "' ORDER BY " + DatabaseHelper.DEBE_COLUMN_PLACE + " ASC ", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DebeListItem debeListItem = cursorToDebeListItem(cursor);
            debeList.add(debeListItem);
            cursor.moveToNext();
        }

        cursor.close();
        return debeList;
    }

    public String getLastDate(){
        String lastDate = "";

        Cursor cursor = database.rawQuery("SELECT " + DatabaseHelper.DEBE_COLUMN_DATE +" FROM "
                + DatabaseHelper.TABLE_DEBE +" ORDER BY "
                + DatabaseHelper.DEBE_COLUMN_ID + " DESC LIMIT 1", null);
        cursor.moveToFirst();
        if(cursor!=null){
            lastDate = cursor.getString(0);
        }
        return lastDate;
    }

    private DebeListItem cursorToDebeListItem(Cursor cursor) {
        DebeListItem debeListItem = new DebeListItem(0, "", "", "", "");
        debeListItem.setId(cursor.getLong(0));
        debeListItem.setPlace(cursor.getInt(1));
        debeListItem.setTitle(cursor.getString(2));
        debeListItem.setAuthor(cursor.getString(3));
        debeListItem.setUrl(cursor.getString(4));
        debeListItem.setDate(cursor.getString(5));
        return debeListItem;
    }

}
