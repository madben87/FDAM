package com.ben.fdam_ver_020.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.bean.Sim;
import com.ben.fdam_ver_020.bean.SimHistory;
import com.ben.fdam_ver_020.database.DaoInterface.SimDAO;
import com.ben.fdam_ver_020.utils.MyLog;
import com.ben.fdam_ver_020.utils.MyUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class SimDaoImpl implements SimDAO {

    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private String[] getSimsColumn = {DBHelper.COL_SIM_ID, DBHelper.COL_ID, DBHelper.COL_SIM_NUM};
    private String[] getSimWithHistoryColumn = {DBHelper.COL_SIM_ID, DBHelper.COL_ID, DBHelper.COL_SIM_NUM, DBHelper.COL_SIM_HISTORY_ID, DBHelper.COL_SIM_ID, DBHelper.COL_SIM_INSTALL, DBHelper.COL_SIM_UNINSTALL};

    public SimDaoImpl(Context context) {
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

    @Override
    public ArrayList<Sim> getSims() {
        if (sqLiteDatabase == null) {
            open();
        }
        return null;
    }

    @Override
    public Sim getSimById(int id) {
        if (sqLiteDatabase == null) {
            open();
        }

        String query = "select * from " + DBHelper.TABLE_SIM + " SIM where SIM.sim_id = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(id)});

        cursor.moveToFirst();

        return cursorToSim(cursor);
    }

    @Override
    public Sim getSimByDeviceId(int id) {
        if (sqLiteDatabase == null) {
            open();
        }

        String query = "select * from " + DBHelper.TABLE_SIM + " SIM where SIM.id = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(id)});

        cursor.moveToFirst();

        return cursorToSim(cursor);
    }

    @Override
    public ArrayList<SimHistory> getSimHistoryByDeviceId(int id) {
        if (sqLiteDatabase == null) {
            open();
        }

        String query = "select * from " + DBHelper.TABLE_SIM_HISTORY + " SIM_HISTORY where SIM_HISTORY.device_id = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(id)});

        ArrayList<SimHistory> simHistories = new ArrayList<>();

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            SimHistory simHistory = cursorToSimHistory(cursor);
            simHistories.add(simHistory);
            cursor.moveToNext();
        }

        cursor.close();

        return simHistories;
    }

    @Override
    public Sim getSimWithHistoryById(int id) {
        if (sqLiteDatabase == null) {
            open();
        }

        String querySim = "select * from " + DBHelper.TABLE_SIM + " SIM where SIM.sim_id = ?";
        String querySimHistory = "select * from " + DBHelper.TABLE_SIM_HISTORY + " SIM_HISTORY where SIM_HISTORY.sim_id = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(querySim, new String[]{String.valueOf(id)});

        cursor.moveToFirst();

        Sim sim = cursorToSim(cursor);

        Cursor cursor_history = sqLiteDatabase.rawQuery(querySimHistory, new String[]{String.valueOf(sim.getId_sim())});

        cursor_history.moveToFirst();

        ArrayList<SimHistory> list = new ArrayList<>();

        while (!cursor_history.isAfterLast()) {
            SimHistory simHistory = cursorToSimHistory(cursor_history);
            list.add(simHistory);
            cursor_history.moveToNext();
        }

        sim.setSimHistories(list);

        return sim;
    }

    @Override
    public Sim getSimWithHistoryByDeviceId(int id) {
        if (sqLiteDatabase == null) {
            open();
        }

        String querySim = "select * from " + DBHelper.TABLE_SIM + " SIM where SIM.id = ?";
        String querySimHistory = "select * from " + DBHelper.TABLE_SIM_HISTORY + " SIM_HISTORY where SIM_HISTORY.sim_id = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(querySim, new String[]{String.valueOf(id)});

        cursor.moveToFirst();

        Sim sim = cursorToSim(cursor);

        Cursor cursor_history = sqLiteDatabase.rawQuery(querySimHistory, new String[]{String.valueOf(sim.getId_sim())});

        cursor_history.moveToFirst();

        ArrayList<SimHistory> list = new ArrayList<>();

        while (!cursor_history.isAfterLast()) {
            SimHistory simHistory = cursorToSimHistory(cursor_history);
            list.add(simHistory);
            cursor_history.moveToNext();
        }

        sim.setSimHistories(list);

        return sim;
    }

    @Override
    public ArrayList<Sim> getSimListWithHistoryByDeviceId(int id) {
        if (sqLiteDatabase == null) {
            open();
        }

        Cursor cursor;

        Cursor cursor_history;

        String querySim = "select * from " + DBHelper.TABLE_SIM + " SIM where SIM.id = ?";
        String querySimHistory = "select * from " + DBHelper.TABLE_SIM_HISTORY + " SIM_HISTORY where SIM_HISTORY.sim_id = ?";

        cursor = sqLiteDatabase.rawQuery(querySim, new String[]{String.valueOf(id)});

        new MyLog("getSimListWithHistoryByDeviceId", cursor);

        ArrayList<Sim> list_sim = new ArrayList<>();

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Sim sim = cursorToSim(cursor);

            cursor_history = sqLiteDatabase.rawQuery(querySimHistory, new String[]{String.valueOf(sim.getId_sim())});
            cursor_history.moveToFirst();
            //new MyLog("TestHistory", cursor_history);
            ArrayList<SimHistory> list_history = new ArrayList<>();

            while (!cursor_history.isAfterLast()) {
                SimHistory simHistory = cursorToSimHistory(cursor_history);
                list_history.add(simHistory);
                cursor_history.moveToNext();
            }
            sim.setSimHistories(list_history);
            list_sim.add(sim);
            cursor.moveToNext();
            cursor_history.close();
        }

        cursor.close();

        return list_sim;
    }

    @Override
    public ArrayList<SimHistory> getSimHistory(int id) {
        if (sqLiteDatabase == null) {
            open();
        }

        Cursor cursor_history;

        String querySimHistory = "select * from " + DBHelper.TABLE_SIM_HISTORY + " SIM_HISTORY where SIM_HISTORY.sim_id = ?";

        cursor_history = sqLiteDatabase.rawQuery(querySimHistory, new String[]{String.valueOf(id)});

        ArrayList<SimHistory> list_history = new ArrayList<>();

        cursor_history.moveToFirst();

        while (!cursor_history.isAfterLast()) {
            SimHistory simHistory = cursorToSimHistory(cursor_history);
            list_history.add(simHistory);
            cursor_history.moveToNext();
        }

        cursor_history.close();

        return list_history;
    }

    @Override
    public ArrayList<Sim> getDeviceSimHistory(int id) {
        if (sqLiteDatabase == null) {
            open();
        }

        Cursor cursor_history;

        String sh = "select * from " + DBHelper.TABLE_SIM_HISTORY + " SIM_HISTORY where SIM_HISTORY.device_id = ?";

        cursor_history = sqLiteDatabase.rawQuery(sh, new String[]{String.valueOf(id)});

        ArrayList<SimHistory> list_history = new ArrayList<>();

        cursor_history.moveToFirst();

        while (!cursor_history.isAfterLast()) {
            SimHistory simHistory = cursorToSimHistory(cursor_history);
            list_history.add(simHistory);
            cursor_history.moveToNext();
        }

        ArrayList<Sim> list_sim = new ArrayList<>();

        for (SimHistory elem : list_history) {
            Sim sim = getSimWithHistoryById(elem.getId_sim());
            list_sim.add(sim);
        }

        cursor_history.close();

        return list_sim;
    }

    @Override
    public void addSim(Sim sim) {
        if (sqLiteDatabase == null) {
            open();
        }

    }

    public void addSimWithHistoryFromParser(Sim sim) {
        if (sqLiteDatabase == null) {
            open();
        }

        ContentValues contentValuesSim = new ContentValues();
        contentValuesSim.put(DBHelper.COL_ID, sim.getDevice_id());
        contentValuesSim.put(DBHelper.COL_SIM_NUM, sim.getSim_num());

        long simId = sqLiteDatabase.insert(DBHelper.TABLE_SIM, null, contentValuesSim);

        for (SimHistory elem : sim.getSimHistories()) {
            ContentValues contentValuesSimHistory = new ContentValues();
            contentValuesSimHistory.put(DBHelper.COL_SIM_ID, simId);

            if (elem.getDate_uninstall() != null) {
                contentValuesSimHistory.put(DBHelper.COL_SIM_UNINSTALL, elem.getDate_uninstall());
            }else if (elem.getDate_install() != null) {
                contentValuesSimHistory.put(DBHelper.COL_SIM_INSTALL, elem.getDate_install());
            }else {
                contentValuesSimHistory.put(DBHelper.COL_SIM_INSTALL, MyUtils.currentDate());
            }

            contentValuesSimHistory.put(DBHelper.COL_DEVICE_ID, elem.getId_device());

            sqLiteDatabase.insert(DBHelper.TABLE_SIM_HISTORY, null, contentValuesSimHistory);
        }
    }

    @Override
    public void updateSimWithHistoryBiDeviceId(Device device) {
        if (sqLiteDatabase == null) {
            open();
        }

        Sim sim = getSimWithHistoryByDeviceId(device.getId());

        sim.setDevice_id(0);
        ContentValues contentValuesSim = new ContentValues();
        contentValuesSim.put(DBHelper.COL_ID, sim.getDevice_id());
        //contentValuesSim.put(DBHelper.COL_SIM_NUM, sim.getSim_num());

        sqLiteDatabase.update(DBHelper.TABLE_SIM, contentValuesSim, "id = ?", new String[] {String.valueOf(sim.getId_sim())});

        for (SimHistory elem : sim.getSimHistories()) {

            if (elem.getId_device().equals(String.valueOf(device.getDevice_id()))) {
                elem.setDate_uninstall(MyUtils.currentDate());

                ContentValues contentValuesSimHistory = new ContentValues();
                contentValuesSimHistory.put(DBHelper.COL_SIM_UNINSTALL, elem.getDate_uninstall());

                sqLiteDatabase.update(DBHelper.TABLE_SIM_HISTORY, contentValuesSimHistory, "sim_history_id = ?", new String[] {String.valueOf(elem.getHistory_id())});
            }
        }
    }

    @Override
    public void updateSim(Sim sim) {
        if (sqLiteDatabase == null) {
            open();
        }
    }

    @Override
    public void deleteSim(Sim sim) {
        if (sqLiteDatabase == null) {
            open();
        }
    }

    private Sim cursorToSim(Cursor cursor) {

        Sim sim = new Sim();

        sim.setId_sim(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_SIM_ID)));
        sim.setSim_num(cursor.getString(cursor.getColumnIndex(DBHelper.COL_SIM_NUM)));
        sim.setDevice_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_ID)));

        return sim;
    }

    private SimHistory cursorToSimHistory(Cursor cursor) {

        SimHistory simHistory = new SimHistory();

        simHistory.setHistory_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_SIM_HISTORY_ID)));
        simHistory.setId_sim(cursor.getInt(cursor.getColumnIndex(DBHelper.COL_SIM_ID)));
        simHistory.setDate_install(cursor.getString(cursor.getColumnIndex(DBHelper.COL_SIM_INSTALL)));
        simHistory.setDate_uninstall(cursor.getString(cursor.getColumnIndex(DBHelper.COL_SIM_UNINSTALL)));
        simHistory.setId_device(cursor.getString(cursor.getColumnIndex(DBHelper.COL_DEVICE_ID)));

        return simHistory;
    }
}
