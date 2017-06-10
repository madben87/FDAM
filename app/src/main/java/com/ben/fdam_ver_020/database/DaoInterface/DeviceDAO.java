package com.ben.fdam_ver_020.database.DaoInterface;

import com.ben.fdam_ver_020.bean.Device;

import java.util.ArrayList;

public interface DeviceDAO extends DAO {

    ArrayList<Device> getDevices();

    void addDevice(Device device);

    void deleteDevice(Device device);

    void updateDevice(Device device);
}
