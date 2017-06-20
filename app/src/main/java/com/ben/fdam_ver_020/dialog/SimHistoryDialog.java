package com.ben.fdam_ver_020.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.adapter.DescriptionHistoryAdapter;
import com.ben.fdam_ver_020.adapter.SimHistoryAdapter;
import com.ben.fdam_ver_020.bean.Sim;
import com.ben.fdam_ver_020.database.DescriptionDaoImpl;
import com.ben.fdam_ver_020.database.SimDaoImpl;

import java.util.ArrayList;

public class SimHistoryDialog extends DialogFragment {

    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SimHistoryAdapter simHistoryAdapter;
    private int device_id;
    //private int sim_id;
    private SimDaoImpl simDao;

    public SimHistoryDialog() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        simDao = new SimDaoImpl(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.dialog_history_sim, null);

        initView(view);

        initResView();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        return builder.create();
    }

    private void initResView() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            device_id = bundle.getInt("DeviceId");
            //sim_id = bundle.getInt("SimId");
        }

        ArrayList<Sim> list = simDao.getDeviceSimHistory(device_id);

        simHistoryAdapter = new SimHistoryAdapter(list/*, device_id*/);

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(simHistoryAdapter);
    }

    private void initView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.resView_history_sim);
    }
}
