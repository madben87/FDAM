package com.ben.fdam_ver_020.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.bean.Sim;
import com.ben.fdam_ver_020.bean.SimHistory;
import com.ben.fdam_ver_020.bean.Staff;
import com.ben.fdam_ver_020.database.DeviceDaoImpl;
import com.ben.fdam_ver_020.database.StaffDaoImpl;
import com.ben.fdam_ver_020.utils.MyUtils;

import java.util.ArrayList;

public class AddDeviceActivity extends AppCompatActivity {

    private TextInputLayout layout_id, layout_name, layout_location, layout_description, layout_new_phone;
    private EditText text_id, text_name, text_location, text_description, text_new_phone;
    private Spinner spinner_staff;

    private DeviceDaoImpl deviceDaoImpl = new DeviceDaoImpl(this);
    private StaffDaoImpl staffDao = new StaffDaoImpl(this);
    private Device device;

    public static final int ID_LENGTH = 4;
    public static final int MIN_PHONE_LENGTH = 9;
    public static final int MAX_PHONE_LENGTH = 11;

    private int spinner_staff_position;
    private ArrayList<Staff> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        if (Device.class.isInstance(getIntent().getParcelableExtra("Device"))) {
            initTextField((Device) getIntent().getParcelableExtra("Device"));
        }else {
            initTextField();
        }

        initSpinner();

        initFab();
    }

    private void initTextField() {

        layout_id = (TextInputLayout) findViewById(R.id.id_layout_text);
        layout_name = (TextInputLayout) findViewById(R.id.name_layout_text);
        layout_location = (TextInputLayout) findViewById(R.id.location_layout_text);
        layout_description = (TextInputLayout) findViewById(R.id.description_layout_text);
        layout_new_phone = (TextInputLayout) findViewById(R.id.new_phone_layout_text);
        text_id = (EditText) findViewById(R.id.id_text);
        text_name = (EditText) findViewById(R.id.name_text);
        text_location = (EditText) findViewById(R.id.location_text);
        text_description = (EditText) findViewById(R.id.description_text);
        text_new_phone = (EditText) findViewById(R.id.new_phone_text);

        text_id.addTextChangedListener(new MyTextWatcher(text_id));
        text_name.addTextChangedListener(new MyTextWatcher(text_name));
        text_location.addTextChangedListener(new MyTextWatcher(text_location));
        text_description.addTextChangedListener(new MyTextWatcher(text_description));
        text_new_phone.addTextChangedListener(new MyTextWatcher(text_new_phone));
    }

    private void initTextField(Device device) {

        layout_id = (TextInputLayout) findViewById(R.id.id_layout_text);
        layout_name = (TextInputLayout) findViewById(R.id.name_layout_text);
        layout_location = (TextInputLayout) findViewById(R.id.location_layout_text);
        layout_description = (TextInputLayout) findViewById(R.id.description_layout_text);
        layout_new_phone = (TextInputLayout) findViewById(R.id.new_phone_layout_text);
        text_id = (EditText) findViewById(R.id.id_text);
        text_name = (EditText) findViewById(R.id.name_text);
        text_location = (EditText) findViewById(R.id.location_text);
        text_description = (EditText) findViewById(R.id.description_text);
        text_new_phone = (EditText) findViewById(R.id.new_phone_text);

        text_id.addTextChangedListener(new MyTextWatcher(text_id));
        text_name.addTextChangedListener(new MyTextWatcher(text_name));
        text_location.addTextChangedListener(new MyTextWatcher(text_location));
        text_description.addTextChangedListener(new MyTextWatcher(text_description));
        text_new_phone.addTextChangedListener(new MyTextWatcher(text_new_phone));

        text_id.setText(String.valueOf(device.getDevice_id()));
        text_name.setText(device.getDevice_name());
        text_location.setText(device.getDevice_location());
        text_description.setText("Test Description");

        if (device.getSims().size() > 0) {
            text_new_phone.setText(device.getSims().get(device.getSims().size()-1).getSim_num());
        }else {
            text_new_phone.setText("No sim");
        }

        this.device = device;
    }

    private void initSpinner() {

        Staff staffNull = new Staff();
        staffNull.setStaff_last_name("Пусто");

        list = new ArrayList<>();

        list.add(staffNull);

        for (Staff elem : staffDao.getStaff()) {

            list.add(elem);

            if (device != null) {
                if (elem.getStaff_id() == device.getStaff_id()) {
                    spinner_staff_position = list.indexOf(elem);
                }
            }
        }

        ArrayAdapter<Staff> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_staff = (Spinner) findViewById(R.id.spinner_staff);
        spinner_staff.setAdapter(arrayAdapter);

        spinner_staff.setSelection(spinner_staff_position);
    }

    private void initFab() {
        FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.fab_add_edit);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (device == null) {
                    device = new Device();
                }

                if (!validId()) {
                    return;
                }else {
                    device.setDevice_id(Integer.parseInt(text_id.getText().toString()));
                }

                if (!validName()) {
                    return;
                }else {
                    device.setDevice_name(text_name.getText().toString());
                }

                if (!validLocation()) {
                    return;
                }else {
                    device.setDevice_location(text_location.getText().toString());
                }

                if (!validNewPhone()) {
                    return;
                }else {
                    Sim sim = new Sim();
                    sim.setSim_num(MyUtils.numFilter(text_new_phone.getText().toString()));

                    SimHistory simHistory = new SimHistory();
                    simHistory.setDate_install(MyUtils.currentDate());
                    simHistory.setId_device(String.valueOf(device.getDevice_id()));

                    sim.setSimHistories(MyUtils.toArrayList(simHistory));

                    device.setSims(MyUtils.toArrayList(sim));
                }

                device.setStaff_id(list.get(spinner_staff.getSelectedItemPosition()).getStaff_id());

                if (device.getId() == 0) {
                    deviceDaoImpl.addDevice(device);
                }else {
                    deviceDaoImpl.updateDevice(device);
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//clear stack activity
                startActivity(intent);
            }
        });
    }

    private class MyTextWatcher implements TextWatcher {

        View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            switch (view.getId()) {
                case R.id.id_text:
                    validId();
                    break;
                case R.id.name_text:
                    validName();
                    break;
                case R.id.location_text:
                    validLocation();
                    break;
                case R.id.description_text:
                    validDescription();
                    break;
                case R.id.new_phone_text:
                    validNewPhone();
                    break;
            }
        }
    }

    private boolean validId() {
        if (text_id.getText().toString().trim().isEmpty()) {
            layout_id.setError(getString(R.string.err_empty_id));
            requestFocus(text_id);
            return false;
        }else if (text_id.getText().toString().trim().toCharArray().length != ID_LENGTH) {
            layout_id.setError(getString(R.string.err_incorrect_id));
            requestFocus(text_id);
            return false;
        }else {
            layout_id.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validName() {
        if (text_name.getText().toString().trim().isEmpty()) {
            layout_name.setError(getString(R.string.err_empty_name));
            requestFocus(text_name);
            return false;
        }else {
            layout_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validLocation() {
        if (text_location.getText().toString().trim().isEmpty()) {
            layout_location.setError(getString(R.string.err_empty_location));
            requestFocus(text_location);
            return false;
        }else {
            layout_location.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validDescription() {
        if (text_description.getText().toString().trim().isEmpty()) {
            layout_description.setError(getString(R.string.err_empty_description));
            requestFocus(text_description);
            return false;
        }else {
            layout_description.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validNewPhone() {
        if (text_new_phone.getText().toString().trim().isEmpty() && text_new_phone.getText().toString().trim().toCharArray().length < MIN_PHONE_LENGTH || text_new_phone.getText().toString().trim().toCharArray().length > MAX_PHONE_LENGTH) {
            layout_new_phone.setError(getString(R.string.err_incorrect_phone));
            requestFocus(text_new_phone);
            return false;
        }else {
            layout_new_phone.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
