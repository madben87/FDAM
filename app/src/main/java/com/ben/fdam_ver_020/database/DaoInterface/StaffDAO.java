package com.ben.fdam_ver_020.database.DaoInterface;

import com.ben.fdam_ver_020.bean.Staff;

import java.util.ArrayList;

public interface StaffDAO extends DAO {

    ArrayList<Staff> getStaff();

    long addStaff(Staff staff);

    void deleteStaff(Staff staff);

    long updateStaff(Staff staff);

    Staff getStaffById(int id);
}
