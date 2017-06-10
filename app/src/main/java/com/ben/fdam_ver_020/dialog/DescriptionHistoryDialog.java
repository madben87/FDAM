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
import com.ben.fdam_ver_020.database.DescriptionDaoImpl;

public class DescriptionHistoryDialog extends DialogFragment {

    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DescriptionHistoryAdapter descriptionHistoryAdapter;
    private int device_id;

    public DescriptionHistoryDialog(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.dialog_history_description, null);

        initView(view);

        initResView();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        return builder.create();
    }

    private void initResView() {

        DescriptionDaoImpl descriptionDao = new DescriptionDaoImpl(getActivity());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            device_id = bundle.getInt("deviceId");
        }

        descriptionHistoryAdapter = new DescriptionHistoryAdapter(descriptionDao.getDescription(device_id));

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(descriptionHistoryAdapter);
    }

    private void initView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.resView_history_description);
    }
}
