package com.example.lostfound2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "lostfound.db";
    private static final int DB_VER  = 1;

    public static final String TBL_ITEMS    = "items";
    public static final String COL_ID       = "_id";
    public static final String COL_NAME     = "person_name";
    public static final String COL_PHONE    = "phone";
    public static final String COL_DESC     = "description";
    public static final String COL_DATE     = "date";
    public static final String COL_STATUS   = "status";
    public static final String COL_LAT      = "latitude";
    public static final String COL_LNG      = "longitude";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TBL_ITEMS + " (" +
            COL_ID     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NAME   + " TEXT, " +
            COL_PHONE  + " TEXT, " +
            COL_DESC   + " TEXT, " +
            COL_DATE   + " TEXT, " +
            COL_STATUS + " TEXT, " +
            COL_LAT    + " REAL, " +
            COL_LNG    + " REAL" +
            ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_ITEMS);
        onCreate(db);
    }


    public long insertItem(String name,
                           String phone,
                           String desc,
                           String date,
                           String status,
                           double lat,
                           double lng) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_PHONE, phone);
        cv.put(COL_DESC, desc);
        cv.put(COL_DATE, date);
        cv.put(COL_STATUS, status);
        cv.put(COL_LAT, lat);
        cv.put(COL_LNG, lng);
        return getWritableDatabase().insert(TBL_ITEMS, null, cv);
    }


    public List<Item> getAllItems() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TBL_ITEMS, null);
        while (c.moveToNext()) {
            Item item = new Item(
                c.getInt(c.getColumnIndexOrThrow(COL_ID)),
                c.getString(c.getColumnIndexOrThrow(COL_NAME)),
                c.getString(c.getColumnIndexOrThrow(COL_PHONE)),
                c.getString(c.getColumnIndexOrThrow(COL_DESC)),
                c.getString(c.getColumnIndexOrThrow(COL_DATE)),
                c.getString(c.getColumnIndexOrThrow(COL_STATUS)),
                c.getDouble(c.getColumnIndexOrThrow(COL_LAT)),
                c.getDouble(c.getColumnIndexOrThrow(COL_LNG))
            );
            list.add(item);
        }
        c.close();
        return list;
    }


    public Cursor getItemById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(
            TBL_ITEMS,
            null,
            COL_ID + " = ?",
            new String[]{ String.valueOf(id) },
            null, null, null
        );
    }
}
