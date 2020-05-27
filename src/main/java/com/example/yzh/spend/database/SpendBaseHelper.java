package com.example.yzh.spend.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yzh.spend.database.SpendDbSchema.SpendTable;

public class SpendBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "spendBase.db";

    public SpendBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SpendTable.NAME + "(" +
                        "_id integer primary key autoincrement, " +
                        SpendTable.Cols.UUID + ", " +
                        SpendTable.Cols.TITLE + ", " +
                        SpendTable.Cols.DATE + ", " +
                        SpendTable.Cols.COST + ", " +
                        SpendTable.Cols.DETAIL + ", " +
                        SpendTable.Cols.ADDRESS +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
