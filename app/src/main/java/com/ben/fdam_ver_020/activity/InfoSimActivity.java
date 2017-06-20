package com.ben.fdam_ver_020.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.adapter.SimHistoryAdapter;
import com.ben.fdam_ver_020.adapter.SimInfoAdapter;
import com.ben.fdam_ver_020.bean.Sim;
import com.ben.fdam_ver_020.database.SimDaoImpl;

import java.util.ArrayList;

public class InfoSimActivity extends AppCompatActivity/* implements View.OnClickListener*/ {

    private CardView cardView;
    private TextView textView_sim_num;
    private ImageButton view_info_delete, view_info_description, view_info_edit;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SimInfoAdapter simInfoAdapter;

    private Sim sim;
    private SimDaoImpl simDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sim);

        sim = getIntent().getParcelableExtra("Sim");
        simDao = new SimDaoImpl(this);

        initView(sim);
        initView();
        initResView();
    }

    private void initView(Sim sim) {

        textView_sim_num = (TextView) findViewById(R.id.textView_info_sim_num);

        view_info_delete = (ImageButton) findViewById(R.id.imageButton_sim_delete);
        view_info_description = (ImageButton) findViewById(R.id.imageButton_sim_description);
        view_info_edit = (ImageButton) findViewById(R.id.imageButton_sim_edit);

        textView_sim_num.setText(sim.getSim_num());
    }

    private void initResView() {

        simInfoAdapter = new SimInfoAdapter(sim.getSimHistories());

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(simInfoAdapter);
    }

    private void initView() {

        recyclerView = (RecyclerView) findViewById(R.id.res_info_sim);
    }

    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_sim_delete:

                simDao.deleteSim(sim);

                Intent intentDelete = new Intent(v.getContext(), MainActivity.class);
                intentDelete.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// FLAG_ACTIVITY_CLEAR_TOP - clear all activities after the called
                v.getContext().startActivity(intentDelete);

                s = "Delete device : " + device.getDevice_id();
                break;

            case R.id.imageButton_sim_description:

                s = "Description";
                break;

            case R.id.imageButton_sim_edit:

                Intent intentEdit = new Intent(v.getContext(), AddDeviceActivity.class);
                intentEdit.putExtra("Device", device);
                v.getContext().startActivity(intentEdit);

                s = "Edit device : " + device.getDevice_id();
                break;
        }
    }*/
}
