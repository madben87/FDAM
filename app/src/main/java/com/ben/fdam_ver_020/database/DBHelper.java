package com.ben.fdam_ver_020.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "fdam.db";
    public static final String TABLE_DEVICE = "device";
    public static final String TABLE_DESCRIPTION = "description";
    public static final String TABLE_STAFF = "staff";
    public static final String TABLE_SIM = "sim";
    public static final String TABLE_SIM_HISTORY = "sim_history";
    private static final int DB_VERSION = 1;

    public static final String COL_ID = "id";
    public static final String COL_DEVICE_ID = "device_id";
    public static final String COL_DEVICE_NAME = "device_name";
    public static final String COL_DEVICE_LOCATION = "device_location";
    public static final String COL_DEVICE_OLD_PHONE = "device_old_phone";
    public static final String COL_DEVICE_NEW_PHONE = "device_new_phone";

    public static final String COL_STAFF_ID = "staff_id";
    public static final String COL_STAFF_NAME = "staff_name";
    public static final String COL_STAFF_LAST_NAME = "staff_last_name";
    public static final String COL_STAFF_PHONE = "staff_phone";
    public static final String COL_STAFF_MANAGER = "staff_manager";

    public static final String COL_DESCRIPTION_ID = "description_id";
    public static final String COL_DESCRIPTION_DATE = "description_date";
    public static final String COL_DESCRIPTION_ITEM = "description_item";

    public static final String COL_SIM_ID = "sim_id";
    public static final String COL_SIM_NUM = "sim_num";

    public static final String COL_SIM_HISTORY_ID = "sim_history_id";
    public static final String COL_SIM_INSTALL = "sim_install";
    public static final String COL_SIM_UNINSTALL = "sim_uninstall";

    private static final String DB_DEVICE_CREATE = "create table "
            + TABLE_DEVICE + " ("
            + COL_ID + " integer primary key autoincrement, "
            + COL_DEVICE_ID + " integer not null, "
            + COL_DEVICE_NAME + " text not null, "
            + COL_DEVICE_LOCATION + " text not null, "
            + COL_STAFF_ID + " integer not null, "
            + COL_DEVICE_OLD_PHONE + " integer, "
            + COL_DEVICE_NEW_PHONE + " integer, "
            + "FOREIGN KEY(" + COL_STAFF_ID + ") REFERENCES " + TABLE_STAFF + "(" + COL_STAFF_ID + ")"
            + ");";

    private static final String DB_STAFF_CREATE = "create table "
            + TABLE_STAFF + " ("
            + COL_STAFF_ID + " integer primary key autoincrement, "
            + COL_STAFF_NAME + " text, "
            + COL_STAFF_LAST_NAME + " text not null, "
            + COL_STAFF_PHONE + " text, "
            + COL_STAFF_MANAGER + " text"
            + ");";

    private static final String DB_DESCRIPTION_CREATE = "create table "
            + TABLE_DESCRIPTION + " ("
            + COL_DESCRIPTION_ID + " integer primary key autoincrement, "
            + COL_ID + " integer not null, "
            + COL_DESCRIPTION_DATE + " text not null, "
            + COL_DESCRIPTION_ITEM + " text not null, "
            + "FOREIGN KEY (" + COL_ID + ") REFERENCES " + TABLE_DEVICE + "(" + COL_ID + ")"
            + ");";

    private static final String DB_SIM_CREATE = "create table "
            + TABLE_SIM + " ("
            + COL_SIM_ID + " integer primary key autoincrement, "
            + COL_ID + " integer not null, "
            + COL_SIM_NUM + " text not null, "
            + "FOREIGN KEY (" + COL_ID + ") REFERENCES " + TABLE_DEVICE + "(" + COL_ID + ")"
            + ");";

    private static final String DB_SIM_HISTORY_CREATE = "create table "
            + TABLE_SIM_HISTORY + " ("
            + COL_SIM_HISTORY_ID + " integer primary key autoincrement, "
            + COL_SIM_ID + " integer not null, "
            + COL_SIM_INSTALL + " text, "
            + COL_SIM_UNINSTALL + " text, "
            + "FOREIGN KEY (" + COL_SIM_ID + ") REFERENCES " + TABLE_SIM + "(" + COL_SIM_ID + ")"
            + ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_DEVICE_CREATE);
        db.execSQL(DB_STAFF_CREATE);
        db.execSQL(DB_DESCRIPTION_CREATE);
        db.execSQL(DB_SIM_CREATE);
        db.execSQL(DB_SIM_HISTORY_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_DEVICE);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_STAFF);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_DESCRIPTION);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_SIM);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_SIM_HISTORY);
        onCreate(db);
    }
}
