package com.ben.fdam_ver_020.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ben.fdam_ver_020.bean.Description;
import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.database.DaoInterface.DescriptionDao;

import java.util.ArrayList;

public class DescriptionDaoImpl implements DescriptionDao {

    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private String[] allDescriptionCol = {DBHelper.COL_DESCRIPTION_ID, DBHelper.COL_ID, DBHelper.COL_DESCRIPTION_DATE, DBHelper.COL_DESCRIPTION_ITEM};

    public DescriptionDaoImpl(Context context) {dbHelper = new DBHelper(context);}

    @Override
    public void open() {
        try {
            sqLiteDatabase = dbHelper.getWritableDatabase();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    @Override
    public boolean isOpen() {
        return sqLiteDatabase.isOpen();
    }

    @Override
    public ArrayList<Description> getDescription(int deviceId) {

        if (sqLiteDatabase == null) {
            open();
        }

        ArrayList<Description> list = new ArrayList<>();

        Cursor cursor;

        String[] id = {String.valueOf(deviceId)};

        cursor = sqLiteDatabase.query(DBHelper.TABLE_DESCRIPTION, allDescriptionCol,  "id = ?", id, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Description description = cursorToDescription(cursor);
            list.add(description);
            cursor.moveToNext();
        }

        cursor.close();

        return list;
    }

    @Override
    public void addDescription(Description description) {

        if (sqLiteDatabase == null) {
            open();
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.COL_ID, description.getDevice_id());
        contentValues.put(DBHelper.COL_DESCRIPTION_DATE, description.getDescription_date());
        contentValues.put(DBHelper.COL_DESCRIPTION_ITEM, description.getDescription_item());

        long insertId = sqLiteDatabase.insert(DBHelper.TABLE_DESCRIPTION, null, contentValues);
    }

    @Override
    public void deleteDescription(Description description) {

        if (sqLiteDatabase == null) {
            open();
        }

        long deleteId = sqLiteDatabase.delete(DBHelper.TABLE_DESCRIPTION, "description_id = " + description.getDescription_id(), null);
    }

    @Override
    public void updateDescription(Description description) {

        if (sqLiteDatabase == null) {
            open();
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.COL_DESCRIPTION_ID, description.getDescription_id());
        contentValues.put(DBHelper.COL_DESCRIPTION_DATE, description.getDescription_date());
        contentValues.put(DBHelper.COL_DESCRIPTION_ITEM, description.getDescription_item());

        long insertId = sqLiteDatabase.update(DBHelper.TABLE_DESCRIPTION, contentValues, "description_id = ?", new String[] {String.valueOf(description.getDescription_id())});

    }

    private Description cursorToDescription(Cursor cursor) {

        Description description = new Description();

        description.setDescription_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_DESCRIPTION_ID)));
        description.setDevice_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_ID)));
        description.setDescription_date(cursor.getString(cursor.getColumnIndex(DBHelper.COL_DESCRIPTION_DATE)));
        description.setDescription_item(cursor.getString(cursor.getColumnIndex(DBHelper.COL_DESCRIPTION_ITEM)));

        return description;
    }
}
