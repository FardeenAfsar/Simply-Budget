package com.fardeen.budget_planner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String HISTORY_TABLE = "HISTORY_TABLE";
    public static final String COLUMN_HISTORY_TOTAL = "HISTORY_TOTAL";
    public static final String COLUMN_HISTORY_CHANGE = "HISTORY_CHANGE";
    public static final String COLUMN_HISTORY_TYPE = "HISTORY_TYPE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_DATE = "DATE";
    public Database(@Nullable Context context) {
        super(context, "history.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + HISTORY_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_HISTORY_TOTAL + " INT, " + COLUMN_HISTORY_CHANGE + " INT, " + COLUMN_HISTORY_TYPE + " TEXT, " + COLUMN_DATE + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(History history) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_HISTORY_TYPE, history.getType());
        cv.put(COLUMN_HISTORY_TOTAL, history.getTotal());
        cv.put(COLUMN_HISTORY_CHANGE, history.getChange());
        cv.put(COLUMN_DATE, history.getDate());

        long insert = db.insert(HISTORY_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }else {
            return true;
        }
    }

    //Cursor return
    Cursor readData () {
        String query = "SELECT * FROM " + HISTORY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

}
