package com.ben.fdam_ver_020.database.DaoInterface;

import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.bean.Sim;
import com.ben.fdam_ver_020.bean.SimHistory;

import java.util.ArrayList;

public interface SimDAO extends DAO {

    ArrayList<Sim> getSims();

    //ArrayList<Sim> getSimsById(int id);

    Sim getSimById(int id);

    Sim getSimByDeviceId(int id);

    Sim getSimWithHistoryById(int id);

    Sim getSimWithHistoryByDeviceId(int id);

    ArrayList<SimHistory> getSimHistoryByDeviceId(int id);

    ArrayList<Sim> getSimListWithHistoryByDeviceId(int id);

    ArrayList<SimHistory> getSimHistory(int id);

    ArrayList<Sim> getDeviceSimHistory(int id);

    void addSim(Sim sim);

    void updateSim(Sim sim);

    void updateSimWithHistoryBiDeviceId(Device device);

    void deleteSim(Sim sim);
}
