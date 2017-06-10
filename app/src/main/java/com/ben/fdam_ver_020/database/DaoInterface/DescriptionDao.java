package com.ben.fdam_ver_020.database.DaoInterface;

import com.ben.fdam_ver_020.bean.Description;
import com.ben.fdam_ver_020.bean.Device;

import java.util.ArrayList;

public interface DescriptionDao extends DAO {

    ArrayList<Description> getDescription(int deviceId);

    void addDescription(Description description);

    void deleteDescription(Description description);

    void updateDescription(Description description);
}
