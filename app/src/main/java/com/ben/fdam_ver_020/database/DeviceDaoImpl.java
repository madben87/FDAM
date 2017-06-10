package com.ben.fdam_ver_020.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.database.DaoInterface.DeviceDAO;

import java.util.ArrayList;

public class DeviceDaoImpl implements DeviceDAO {

    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;

    public DeviceDaoImpl(Context context) {
        dbHelper = new DBHelper(context);
        //open();
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

    private String[] allDeviceCol = {DBHelper.COL_ID, DBHelper.COL_DEVICE_ID, DBHelper.COL_DEVICE_NAME, DBHelper.COL_DEVICE_LOCATION, DBHelper.COL_DEVICE_OLD_PHONE, DBHelper.COL_DEVICE_NEW_PHONE, DBHelper.COL_STAFF_ID};

    public void addDevice(Device device) {

        if (sqLiteDatabase == null) {
            open();
        }

        ContentValues contentValues = new ContentValues();
        //contentValues.put(DBHelper.COL_ID, device.getId());
        contentValues.put(DBHelper.COL_DEVICE_ID, device.getDevice_id());
        contentValues.put(DBHelper.COL_DEVICE_NAME, device.getDevice_name());
        contentValues.put(DBHelper.COL_DEVICE_LOCATION, device.getDevice_location());
        contentValues.put(DBHelper.COL_DEVICE_OLD_PHONE, device.getDevice_old_phone());
        contentValues.put(DBHelper.COL_DEVICE_NEW_PHONE, device.getDevice_new_phone());
        contentValues.put(DBHelper.COL_STAFF_ID, device.getStaff_id());

        long insertId = sqLiteDatabase.insert(DBHelper.TABLE_DEVICE, null, contentValues);
    }

    @Override
    public void deleteDevice(Device device) {
        if (sqLiteDatabase == null) {
            open();
        }

        long deleteId = sqLiteDatabase.delete(DBHelper.TABLE_DEVICE, "id = " + device.getId(), null);
    }


    public void updateDevice(Device device) {

        if (sqLiteDatabase == null) {
            open();
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.COL_DEVICE_ID, device.getDevice_id());
        contentValues.put(DBHelper.COL_DEVICE_NAME, device.getDevice_name());
        contentValues.put(DBHelper.COL_DEVICE_LOCATION, device.getDevice_location());
        contentValues.put(DBHelper.COL_DEVICE_OLD_PHONE, device.getDevice_old_phone());
        contentValues.put(DBHelper.COL_DEVICE_NEW_PHONE, device.getDevice_new_phone());
        contentValues.put(DBHelper.COL_STAFF_ID, device.getStaff_id());

        long insertId = sqLiteDatabase.update(DBHelper.TABLE_DEVICE, contentValues, "id = ?", new String[] {String.valueOf(device.getId())});
    }

    public ArrayList<Device> getDevices() {

        if (sqLiteDatabase == null) {
            open();
        }

        ArrayList<Device> list = new ArrayList<>();

        Cursor cursor;

        cursor = sqLiteDatabase.query(DBHelper.TABLE_DEVICE, allDeviceCol, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Device device = cursorToDevice(cursor);
            list.add(device);
            cursor.moveToNext();
        }

        cursor.close();

        return list;
    }

    private Device cursorToDevice(Cursor cursor) {

        Device device = new Device();

        device.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_ID)));
        device.setDevice_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_DEVICE_ID)));
        device.setDevice_name(cursor.getString(cursor.getColumnIndex(DBHelper.COL_DEVICE_NAME)));
        device.setDevice_location(cursor.getString(cursor.getColumnIndex(DBHelper.COL_DEVICE_LOCATION)));
        device.setDevice_old_phone(cursor.getString(cursor.getColumnIndex(DBHelper.COL_DEVICE_OLD_PHONE)));
        device.setDevice_new_phone(cursor.getString(cursor.getColumnIndex(DBHelper.COL_DEVICE_NEW_PHONE)));
        device.setStaff_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_STAFF_ID)));

        return device;
    }
}
