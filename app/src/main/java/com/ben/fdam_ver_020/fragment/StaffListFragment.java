package com.ben.fdam_ver_020.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.adapter.DeviceResViewAdapter;
import com.ben.fdam_ver_020.adapter.StaffResViewAdapter;
import com.ben.fdam_ver_020.bean.Staff;
import com.ben.fdam_ver_020.database.DeviceDaoImpl;
import com.ben.fdam_ver_020.database.StaffDaoImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StaffListFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private StaffResViewAdapter staffResViewAdapter;

    public StaffListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.staff_list_fragment, container, false);

        initView(view);

        initResView();

        return view;
    }

    private void initResView() {

        StaffDaoImpl staffDao = new StaffDaoImpl(getActivity());

        staffResViewAdapter = new StaffResViewAdapter(staffDao.getStaff());

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(staffResViewAdapter);
    }

    private void initView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.resViewStaff);
    }
}
