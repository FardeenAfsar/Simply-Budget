package com.example.budget_planner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static final String HISTORY_TABLE = "HISTORY_TABLE";
    public static final String COLUMN_HISTORY_TOTAL = "HISTORY_TOTAL";
    public static final String COLUMN_HISTORY_CHANGE = "HISTORY_CHANGE";
    public static final String COLUMN_HISTORY_TYPE = "HISTORY_TYPE";
    public static final String COLUMN_ID = "ID";

    public Database(@Nullable Context context) {
        super(context, "history.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + HISTORY_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_HISTORY_TOTAL + " INT, " + COLUMN_HISTORY_CHANGE + " INT, " + COLUMN_HISTORY_TYPE + " TEXT)";

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

        long insert = db.insert(HISTORY_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }else {
            return true;
        }
    }

    public List<History> getAll() {
        List<History> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + HISTORY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                Float amountTotal = cursor.getFloat(1);
                Float amountChange = cursor.getFloat(2);
                String amountType = cursor.getString(3);
                History newHistory = new History(amountTotal, amountChange, amountType);
                returnList.add(newHistory);
            }while (cursor.moveToNext());
        }else {
        }
        cursor.close();
        db.close();
        return returnList;
    }
}
