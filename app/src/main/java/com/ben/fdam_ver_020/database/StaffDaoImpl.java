package com.ben.fdam_ver_020.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.bean.Staff;
import com.ben.fdam_ver_020.database.DaoInterface.DeviceDAO;
import com.ben.fdam_ver_020.database.DaoInterface.StaffDAO;

import java.util.ArrayList;

public class StaffDaoImpl implements StaffDAO {

    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;

    public StaffDaoImpl(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void close() {
        dbHelper.close();
    }

    public void open() {
        try {
            sqLiteDatabase = dbHelper.getWritableDatabase();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isOpen() {
        return sqLiteDatabase.isOpen();
    }

    private String[] allStaffCol = {DBHelper.COL_STAFF_ID, DBHelper.COL_STAFF_NAME, DBHelper.COL_STAFF_LAST_NAME, DBHelper.COL_STAFF_PHONE, DBHelper.COL_STAFF_MANAGER};

    public long addStaff(Staff staff) {

        if (sqLiteDatabase == null) {
            open();
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.COL_STAFF_NAME, staff.getStaff_name());
        contentValues.put(DBHelper.COL_STAFF_LAST_NAME, staff.getStaff_last_name());
        contentValues.put(DBHelper.COL_STAFF_PHONE, staff.getStaff_phone());
        contentValues.put(DBHelper.COL_STAFF_MANAGER, staff.getStaff_manager());

        return sqLiteDatabase.insert(DBHelper.TABLE_STAFF, null, contentValues);
    }

    @Override
    public void deleteStaff(Staff staff) {

        if (sqLiteDatabase == null) {
            open();
        }

        sqLiteDatabase.delete(DBHelper.TABLE_STAFF, "staff_id = " + staff.getStaff_id(), null);

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COL_STAFF_ID, 0);

        sqLiteDatabase.update(DBHelper.TABLE_DEVICE, contentValues, "staff_id = ?", new String[] {String.valueOf(staff.getStaff_id())});
    }

    public long updateStaff(Staff staff) {

        if (sqLiteDatabase == null) {
            open();
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.COL_STAFF_NAME, staff.getStaff_name());
        contentValues.put(DBHelper.COL_STAFF_LAST_NAME, staff.getStaff_last_name());
        contentValues.put(DBHelper.COL_STAFF_PHONE, staff.getStaff_phone());
        contentValues.put(DBHelper.COL_STAFF_MANAGER, staff.getStaff_manager());

        return (long) sqLiteDatabase.update(DBHelper.TABLE_STAFF, contentValues, "staff_id = ?", new String[] {String.valueOf(staff.getStaff_id())});
    }

    @Override
    public Staff getStaffById(int id) {

        if (sqLiteDatabase == null) {
            open();
        }

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_STAFF, allStaffCol, "staff_id = ?", new String[] {String.valueOf(id)}, null, null, null);

        cursor.moveToFirst();

        return cursorToStaff(cursor);
    }

    @Override
    public Staff getStaffByLastName(String name) {

        if (sqLiteDatabase == null) {
            open();
        }

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_STAFF, null, "staff_last_name = ?", new String[] {String.valueOf(name)}, null, null, null);

        cursor.moveToFirst();

        return cursorToStaff(cursor);
    }

    public ArrayList<Staff> getStaff() {

        if (sqLiteDatabase == null) {
            open();
        }

        ArrayList<Staff> list = new ArrayList<>();

        Cursor cursor;

        cursor = sqLiteDatabase.query(DBHelper.TABLE_STAFF, allStaffCol, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Staff staff = cursorToStaff(cursor);
            list.add(staff);
            cursor.moveToNext();
        }

        cursor.close();

        return list;
    }

    private  Staff cursorToStaff(Cursor cursor) {

        Staff staff = new Staff();

        staff.setStaff_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_STAFF_ID)));
        staff.setStaff_name(cursor.getString(cursor.getColumnIndex(DBHelper.COL_STAFF_NAME)));
        staff.setStaff_last_name(cursor.getString(cursor.getColumnIndex(DBHelper.COL_STAFF_LAST_NAME)));
        staff.setStaff_phone(cursor.getString(cursor.getColumnIndex(DBHelper.COL_STAFF_PHONE)));
        staff.setStaff_manager(cursor.getString(cursor.getColumnIndex(DBHelper.COL_STAFF_MANAGER)));

        return staff;
    }
}
