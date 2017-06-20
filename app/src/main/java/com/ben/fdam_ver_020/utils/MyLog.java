package com.ben.fdam_ver_020.utils;

import android.database.Cursor;
import android.util.Log;

public class MyLog {

    private String title;

    public MyLog(String title, Cursor cursor) {

        this.title = title;
        String msg = "------------" + title + "------------";
        Log.d("MyLog", msg);
        logCursor(cursor);
        Log.d("MyLog", msg);
    }

    private void logCursor(Cursor c) {
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(title, str);
                } while (c.moveToNext());
            }
        } else
            Log.d(title, "Cursor is null");
    }
}
