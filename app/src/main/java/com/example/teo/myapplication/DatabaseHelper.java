package com.example.teo.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by Teo on 2016-06-05.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String COLUMN_AVAILABILITY = "Availability";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_RARITY = "Rarity";
    private static final String DATABASE_NAME = "geomon.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_GEOMON = "geomon";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE geomon( Id INTEGER , Name TEXT , Rarity INTEGER , Availability INTEGER );");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST geomon");
        onCreate(db);
    }

    public void addGeomon(int id, String name, int rarity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, Integer.valueOf(id));
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_RARITY, Integer.valueOf(rarity));
        values.put(COLUMN_AVAILABILITY, Integer.valueOf(0));
        db.insert(TABLE_GEOMON, null, values);
        db.close();
    }

    public void deleteGeomon(String geomonName) {
        getWritableDatabase().execSQL("DELETE FROM geomon WHERE Name=\"" + geomonName + "\";");
    }

    public void updateAvailability(List<Integer> araylist) {
        SQLiteDatabase db = getWritableDatabase();
        for (int i = 0; i < araylist.size(); i += DATABASE_VERSION) {
            db.execSQL("update geomon set availability = 1 where id =" + araylist.get(i));
        }
    }

    public int addRandomGeomon() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select id from geomon where availability = 0", null);
        int[] id = new int[c.getCount()];
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                id[i] = c.getInt(c.getColumnIndex(COLUMN_ID));
                i += DATABASE_VERSION;
            } while (c.moveToNext());
            c.close();
            db.close();
            SQLiteDatabase db2 = getWritableDatabase();
            db2.execSQL("update geomon set Availability = 1 where Id=" + id[0]);
            db2.close();
            return id[0];
        }
        id[0] = -1;
        return id[0];
    }
}