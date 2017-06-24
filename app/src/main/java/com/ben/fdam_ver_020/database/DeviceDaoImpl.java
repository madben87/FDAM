package com.ben.fdam_ver_020.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.bean.Sim;
import com.ben.fdam_ver_020.database.DaoInterface.DeviceDAO;
import com.ben.fdam_ver_020.utils.MyLog;

import java.util.ArrayList;

public class DeviceDaoImpl implements DeviceDAO {

    private static final String LOG_TAG = "myLogs";
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private Context context;
    private SimDaoImpl simDao;

    public DeviceDaoImpl(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
        simDao = new SimDaoImpl(context);
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

    private String[] allDeviceCol = {DBHelper.COL_ID, DBHelper.COL_DEVICE_ID, DBHelper.COL_DEVICE_NAME, DBHelper.COL_DEVICE_LOCATION, /*DBHelper.COL_DEVICE_OLD_PHONE, DBHelper.COL_DEVICE_NEW_PHONE,*/ DBHelper.COL_STAFF_ID};

    public long addDevice(Device device) {

        if (sqLiteDatabase == null) {
            open();
        }

        ContentValues contentValuesDevice = new ContentValues();
        ContentValues contentValuesSim = new ContentValues();

        contentValuesDevice.put(DBHelper.COL_DEVICE_ID, device.getDevice_id());
        contentValuesDevice.put(DBHelper.COL_DEVICE_NAME, device.getDevice_name());
        contentValuesDevice.put(DBHelper.COL_DEVICE_LOCATION, device.getDevice_location());
        contentValuesDevice.put(DBHelper.COL_STAFF_ID, device.getStaff_id());

        long idDevice = sqLiteDatabase.insert(DBHelper.TABLE_DEVICE, null, contentValuesDevice);

        if (device.getSims()!= null) {

            for (Sim elem : device.getSims()) {

                if (elem.getDevice_id() >= 0) {
                    elem.setDevice_id((int) idDevice);
                }

                simDao.addSimWithHistoryFromParser(elem);
            }
        }

        return idDevice;
    }

    public long addDevice(Device device, boolean simIsExist) {

        if (sqLiteDatabase == null) {
            open();
        }

        ContentValues contentValuesDevice = new ContentValues();
        //ContentValues contentValuesSim = new ContentValues();

        contentValuesDevice.put(DBHelper.COL_DEVICE_ID, device.getDevice_id());
        contentValuesDevice.put(DBHelper.COL_DEVICE_NAME, device.getDevice_name());
        contentValuesDevice.put(DBHelper.COL_DEVICE_LOCATION, device.getDevice_location());
        contentValuesDevice.put(DBHelper.COL_STAFF_ID, device.getStaff_id());

        long idDevice = sqLiteDatabase.insert(DBHelper.TABLE_DEVICE, null, contentValuesDevice);

        if (device.getSims()!= null) {

            for (Sim elem : device.getSims()) {

                if (simIsExist) {
                    simDao.updateSimWithSimHistory(elem, (int) idDevice);
                }else {
                    elem.setDevice_id((int) idDevice);
                    simDao.addSimWithHistoryFromParser(elem);
                }

                //elem.setDevice_id((int) idDevice);
                //simDao.addSimWithHistoryFromParser(elem);
            }
        }

        return idDevice;
    }

    @Override
    public long deleteDevice(int id) {
        if (sqLiteDatabase == null) {
            open();
        }

        return sqLiteDatabase.delete(DBHelper.TABLE_DEVICE, "id = " + id, null);
    }


    public long updateDevice(Device device) {

        if (sqLiteDatabase == null) {
            open();
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.COL_DEVICE_ID, device.getDevice_id());
        contentValues.put(DBHelper.COL_DEVICE_NAME, device.getDevice_name());
        contentValues.put(DBHelper.COL_DEVICE_LOCATION, device.getDevice_location());
        contentValues.put(DBHelper.COL_STAFF_ID, device.getStaff_id());

        long idDevice = sqLiteDatabase.update(DBHelper.TABLE_DEVICE, contentValues, "id = ?", new String[] {String.valueOf(device.getId())});

        simDao.uninstallSimWithHistoryByDeviceId(device);

        if (device.getSims()!= null) {

            for (Sim elem : device.getSims()) {
            //Sim sim = device.getSims().get(device.getSims().size()-1);
            elem.setDevice_id(device.getId());
            simDao.addSimWithHistoryFromParser(elem);
            }
        }

        return idDevice;
    }

    public long updateDevice(Device device, boolean simIsExist) {

        if (sqLiteDatabase == null) {
            open();
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.COL_DEVICE_ID, device.getDevice_id());
        contentValues.put(DBHelper.COL_DEVICE_NAME, device.getDevice_name());
        contentValues.put(DBHelper.COL_DEVICE_LOCATION, device.getDevice_location());
        contentValues.put(DBHelper.COL_STAFF_ID, device.getStaff_id());

        long idDevice = sqLiteDatabase.update(DBHelper.TABLE_DEVICE, contentValues, "id = ?", new String[] {String.valueOf(device.getId())});

        simDao.uninstallSimWithHistoryByDeviceId(device);

        if (device.getSims()!= null) {

            for (Sim elem : device.getSims()) {
                //Sim sim = device.getSims().get(device.getSims().size()-1);
                elem.setDevice_id(device.getId());

                if (!simIsExist) {
                    simDao.addSimWithHistoryFromParser(elem);
                }else {
                    simDao.updateSimWithSimHistory(elem, device.getId());
                }
            }
        }

        return idDevice;
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

        new MyLog("getDevice", cursor);

        cursor.close();

        return list;
    }

    public ArrayList<Device> getDevicesWithSim() {

        if (sqLiteDatabase == null) {
            open();
        }

        String queryDevicesWithSim = "select *, (select * from " + DBHelper.TABLE_SIM + " SIM inner join "
                + DBHelper.TABLE_SIM_HISTORY + " SH where SIM.id = Device.id) from " + DBHelper.TABLE_DEVICE
                + " Device inner join " + DBHelper.TABLE_SIM + " Sim where Device.id = ?";

        ArrayList<Device> list = new ArrayList<>();

        Cursor cursor;

        cursor = sqLiteDatabase.query(DBHelper.TABLE_DEVICE, allDeviceCol, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Device device = cursorToDevice(cursor);

            try {
                device.setSims(simDao.getSimListWithHistoryByDeviceId(device.getId()));
            }catch (Exception e){e.printStackTrace();}

            list.add(device);
            cursor.moveToNext();
        }

        new MyLog("getDevice", cursor);

        cursor.close();

        return list;
    }

    public Device getDeviceById(int id) {

        if (sqLiteDatabase == null) {
            open();
        }

        String queryDeviceById = "select * from " + DBHelper.TABLE_DEVICE + " Device where Device.id = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(queryDeviceById, new String[]{String.valueOf(id)});

        cursor.moveToFirst();

        Device device = cursorToDevice(cursor);

        cursor.close();

        return device;
    }

    private Device cursorToDevice(Cursor cursor) {

        Device device = new Device();

        device.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_ID)));
        device.setDevice_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_DEVICE_ID)));
        device.setDevice_name(cursor.getString(cursor.getColumnIndex(DBHelper.COL_DEVICE_NAME)));
        device.setDevice_location(cursor.getString(cursor.getColumnIndex(DBHelper.COL_DEVICE_LOCATION)));
        device.setStaff_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_STAFF_ID)));

        return device;
    }
}
