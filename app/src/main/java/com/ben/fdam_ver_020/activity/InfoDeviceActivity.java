package com.ben.fdam_ver_020.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.bean.Description;
import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.database.DescriptionDaoImpl;
import com.ben.fdam_ver_020.database.DeviceDaoImpl;
import com.ben.fdam_ver_020.database.StaffDaoImpl;
import com.ben.fdam_ver_020.dialog.AddDescriptionDialog;
import com.ben.fdam_ver_020.dialog.DescriptionHistoryDialog;

import java.util.ArrayList;

public class InfoDeviceActivity extends AppCompatActivity implements View.OnClickListener, AddDescriptionDialog.DialogClose {

    private CardView card_info;
    private TextView text_info_id;
    private TextView text_info_name;
    private TextView text_info_location;
    private TextView text_info_phone;
    private TextView text_info_staff;
    private TextView text_info_description;
    private ImageButton view_info_delete;
    private ImageButton view_info_description;
    private ImageButton view_info_edit;
    private ImageButton view_info_phone_history;
    private ImageButton view_info_description_history;
    private ImageButton view_info_description_add;

    private Device device;
    private DeviceDaoImpl deviceDao = new DeviceDaoImpl(this);
    private StaffDaoImpl staffDao = new StaffDaoImpl(this);
    private DescriptionDaoImpl descriptionDao = new DescriptionDaoImpl(this);
    private DialogFragment add_dialog;

    public static boolean CURRENT_DESCRIPTION;

    @Override
    protected void onResume() {
        super.onResume();
        text_info_description.setText(lastDescription(device));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);

        device = getIntent().getParcelableExtra("Device");
        //description = descriptionDao.getDescription(device);

        initView(device);

        add_dialog = new AddDescriptionDialog();

        Bundle bundle = new Bundle();
        bundle.putInt("deviceId", device.getId());
        add_dialog.setArguments(bundle);
    }

    @Override
    public void onClick(View v) {

        String s = "";

        switch (v.getId()) {
            case R.id.imageButton_info_delete:

                deviceDao.deleteDevice(device);
                Intent intentDelete = new Intent(v.getContext(), MainActivity.class);
                intentDelete.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//clear stack activity
                v.getContext().startActivity(intentDelete);

                s = "Delete device : " + device.getDevice_id();
                break;

            case R.id.imageButton_info_description:

                s = "Description";
                break;

            case R.id.imageButton_info_edit:

                Intent intentEdit = new Intent(v.getContext(), AddDeviceActivity.class);
                intentEdit.putExtra("Device", device);
                v.getContext().startActivity(intentEdit);

                s = "Edit device : " + device.getDevice_id();
                break;

            case R.id.imageButton_info_phone_history:

                s = "Phone history";
                break;

            case R.id.imageButton_info_desc_history:

                Bundle bundle = new Bundle();
                bundle.putInt("deviceId", device.getId());

                DialogFragment dialog = new DescriptionHistoryDialog();

                dialog.setArguments(bundle);

                dialog.show(getFragmentManager(), "dialogDescriptionHistory");

                s = "Description history";
                break;

            case R.id.textView_info_staff:

                Intent intentInfo = new Intent(v.getContext(), InfoStaffActivity.class);
                intentInfo.putExtra("Staff", staffDao.getStaffById(device.getStaff_id()));
                v.getContext().startActivity(intentInfo);

                s = "Description history";
                break;

            case R.id.imageButton_info_desc_add:

                add_dialog.show(getFragmentManager(), "dialogDescriptionAdd");

                s = "Add Description";

                break;
        }

        Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void initView(Device device) {

        card_info = (CardView) findViewById(R.id.card_device_info);
        card_info.setPreventCornerOverlap(false); //Instead, it adds padding to avoid such intersection (See setPreventCornerOverlap(boolean) to change this behavior)

        text_info_id = (TextView) findViewById(R.id.textView_info_id);
        text_info_name = (TextView) findViewById(R.id.textView_info_name);
        text_info_location = (TextView) findViewById(R.id.textView_info_location);
        text_info_phone = (TextView) findViewById(R.id.textView_info_phone);
        text_info_staff = (TextView) findViewById(R.id.textView_info_staff);
        text_info_description = (TextView) findViewById(R.id.textView_info_description);

        view_info_delete = (ImageButton) findViewById(R.id.imageButton_info_delete);
        view_info_description = (ImageButton) findViewById(R.id.imageButton_info_description);
        view_info_edit = (ImageButton) findViewById(R.id.imageButton_info_edit);
        view_info_phone_history = (ImageButton) findViewById(R.id.imageButton_info_phone_history);
        view_info_description_history = (ImageButton) findViewById(R.id.imageButton_info_desc_history);
        view_info_description_add = (ImageButton) findViewById(R.id.imageButton_info_desc_add);
        view_info_delete.setOnClickListener(this);
        view_info_description.setOnClickListener(this);
        view_info_edit.setOnClickListener(this);
        view_info_phone_history.setOnClickListener(this);
        view_info_description_history.setOnClickListener(this);
        view_info_description_add.setOnClickListener(this);

        text_info_id.setText(String.valueOf(device.getDevice_id()));
        text_info_name.setText(device.getDevice_name());
        text_info_location.setText(device.getDevice_location());
        text_info_phone.setText(device.getDevice_old_phone());
        text_info_description.setText(/*descriptions.get(descriptions.size()-1).getDescription_item()*/lastDescription(device));

        text_info_staff.setText(staffName(device.getStaff_id()));
        text_info_staff.setOnClickListener(this);
    }

    private String staffName(int position) {

        if (position != 0) {

            String name = staffDao.getStaffById(position).getStaff_last_name();

            if (!name.equals("")) {

                return name;
            }
        }
        return "No staff";
    }

    private String lastDescription(Device device) {

        ArrayList<Description> description;

        description = descriptionDao.getDescription(device.getId());

        if (description.size() > 0) {

            return description.get(description.size()-1).getDescription_item();
        }

        return "No descriptions";
    }

    private void reload() {
        text_info_description.setText(lastDescription(device));
    }

    @Override
    public void reloadDescription() {
        text_info_description.setText(lastDescription(device));
    }
}
