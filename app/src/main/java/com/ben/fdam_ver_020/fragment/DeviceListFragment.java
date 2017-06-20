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
import com.ben.fdam_ver_020.database.DeviceDaoImpl;

public class DeviceListFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DeviceResViewAdapter deviceResViewAdapter;

    public DeviceListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.device_list_fragment_layout, container, false);

        initView(view);

        initResView();

        return view;
    }

    private void initResView() {

        DeviceDaoImpl deviceDao = new DeviceDaoImpl(getActivity());

        deviceResViewAdapter = new DeviceResViewAdapter(deviceDao.getDevicesWithSim());

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(deviceResViewAdapter);
    }

    private void initView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.deviceResView);
    }
}
