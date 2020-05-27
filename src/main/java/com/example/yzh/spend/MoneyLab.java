package com.example.yzh.spend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yzh.spend.database.SpendBaseHelper;
import com.example.yzh.spend.database.SpendCursorWrapper;
import com.example.yzh.spend.database.SpendDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MoneyLab {
    private static MoneyLab sMoneyLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MoneyLab get(Context context){
        if(sMoneyLab == null){
            sMoneyLab = new MoneyLab(context);
        }

        return sMoneyLab;
    }

    private MoneyLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new SpendBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void addSpend(Spend s){
       ContentValues values = getContentValues(s);
       mDatabase.insert(SpendDbSchema.SpendTable.NAME, null, values);
    }

    public void deleteSpend(Spend s) {
        mDatabase.delete(SpendDbSchema.SpendTable.NAME, "uuid = ?", new String[] {String.valueOf(s.getId())});
    }

    public List<Spend> getSpends() {
        List<Spend> spends = new ArrayList<>();
        SpendCursorWrapper cursor = (SpendCursorWrapper) querySpends(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                spends.add(cursor.getSpend());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return spends;
    }

    public Spend getSpend(UUID id){
        SpendCursorWrapper cursor = (SpendCursorWrapper) querySpends(
                SpendDbSchema.SpendTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getSpend();
        } finally {
            cursor.close();
        }
    }

    public void updateSpend(Spend spend){
        String uuidString = spend.getId().toString();
        ContentValues values = getContentValues(spend);
        mDatabase.update(SpendDbSchema.SpendTable.NAME, values,
                SpendDbSchema.SpendTable.Cols.UUID +" = ?",
                new String[] { uuidString });
    }

    private SpendCursorWrapper querySpends(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                SpendDbSchema.SpendTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new SpendCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Spend spend){
        ContentValues values = new ContentValues();
        values.put(SpendDbSchema.SpendTable.Cols.UUID, spend.getId().toString());
        values.put(SpendDbSchema.SpendTable.Cols.TITLE, spend.getType());
        values.put(SpendDbSchema.SpendTable.Cols.DATE, spend.getDate().getTime());
        values.put(SpendDbSchema.SpendTable.Cols.COST, spend.getCost());
        values.put(SpendDbSchema.SpendTable.Cols.DETAIL, spend.getDetail());
        values.put(SpendDbSchema.SpendTable.Cols.ADDRESS, spend.getAddress());
        return values;
    }



}
