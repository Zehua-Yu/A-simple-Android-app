package com.example.yzh.spend.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.yzh.spend.Spend;
import java.util.Date;
import java.util.UUID;

public class SpendCursorWrapper extends CursorWrapper {

    public SpendCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Spend getSpend(){
        String uuidString = getString(getColumnIndex(SpendDbSchema.SpendTable.Cols.UUID));
        String title = getString(getColumnIndex(SpendDbSchema.SpendTable.Cols.TITLE));
        long date = getLong(getColumnIndex(SpendDbSchema.SpendTable.Cols.DATE));
        String cost = getString(getColumnIndex(SpendDbSchema.SpendTable.Cols.COST));
        String detail = getString(getColumnIndex(SpendDbSchema.SpendTable.Cols.DETAIL));
        String address = getString(getColumnIndex(SpendDbSchema.SpendTable.Cols.ADDRESS));


        Spend spend = new Spend(UUID.fromString(uuidString));
        spend.setType(title);
        spend.setDate(new Date(date));
        spend.setCost(cost);
        spend.setDetail(detail);
        spend.setAddress(address);
        return spend;
    }
}
