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
import android.widget.EditText;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.bean.Staff;
import com.ben.fdam_ver_020.database.StaffDaoImpl;
import com.ben.fdam_ver_020.utils.MyUtils;

public class AddStaffActivity extends AppCompatActivity {

    private TextInputLayout layout_staff_name, layout_staff_last_name, layout_staff_phone, layout_staff_manager;
    private EditText text_staff_name, text_staff_last_name, text_staff_phone, text_staff_manager;

    private StaffDaoImpl staffDao = new StaffDaoImpl(this);
    private Staff staff;

    public static final int MIN_PHONE_LENGTH = 9;
    public static final int MAX_PHONE_LENGTH = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        if (Staff.class.isInstance(getIntent().getParcelableExtra("Staff"))) {
            initFields((Staff) getIntent().getParcelableExtra("Staff"));
        }else {
            initFields();
        }

        initFab();
    }

    private void initFields() {

        layout_staff_name = (TextInputLayout) findViewById(R.id.staff_name_layout);
        layout_staff_last_name = (TextInputLayout) findViewById(R.id.last_name_staff_layout);
        layout_staff_phone = (TextInputLayout) findViewById(R.id.phone_staff_layout);
        layout_staff_manager = (TextInputLayout) findViewById(R.id.manager_staff_layout);
        text_staff_name = (EditText) findViewById(R.id.name_staff_text);
        text_staff_last_name = (EditText) findViewById(R.id.lastName_staff_text);
        text_staff_phone = (EditText) findViewById(R.id.phone_staff_text);
        text_staff_manager = (EditText) findViewById(R.id.manager_staff_text);

        text_staff_name.addTextChangedListener(new MyTextWatcher(text_staff_name));
        text_staff_last_name.addTextChangedListener(new MyTextWatcher(text_staff_last_name));
        text_staff_phone.addTextChangedListener(new MyTextWatcher(text_staff_phone));
        text_staff_manager.addTextChangedListener(new MyTextWatcher(text_staff_manager));
    }

    private void initFields(Staff staff) {

        layout_staff_name = (TextInputLayout) findViewById(R.id.staff_name_layout);
        layout_staff_last_name = (TextInputLayout) findViewById(R.id.last_name_staff_layout);
        layout_staff_phone = (TextInputLayout) findViewById(R.id.phone_staff_layout);
        layout_staff_manager = (TextInputLayout) findViewById(R.id.manager_staff_layout);
        text_staff_name = (EditText) findViewById(R.id.name_staff_text);
        text_staff_last_name = (EditText) findViewById(R.id.lastName_staff_text);
        text_staff_phone = (EditText) findViewById(R.id.phone_staff_text);
        text_staff_manager = (EditText) findViewById(R.id.manager_staff_text);

        text_staff_name.addTextChangedListener(new MyTextWatcher(text_staff_name));
        text_staff_last_name.addTextChangedListener(new MyTextWatcher(text_staff_last_name));
        text_staff_phone.addTextChangedListener(new MyTextWatcher(text_staff_phone));
        text_staff_manager.addTextChangedListener(new MyTextWatcher(text_staff_manager));

        text_staff_name.setText(staff.getStaff_name());
        text_staff_last_name.setText(staff.getStaff_last_name());
        text_staff_phone.setText(staff.getStaff_phone());
        text_staff_manager.setText(staff.getStaff_manager());

        this.staff = staff;
    }

    private void initFab() {
        FloatingActionButton fab_staff = (FloatingActionButton) findViewById(R.id.fab_add_staff);
        fab_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (staff == null) {
                    staff = new Staff();
                }

                staff.setStaff_name(text_staff_name.getText().toString());

                if (!validStaffLastName()) {
                    return;
                }else {
                    staff.setStaff_last_name(text_staff_last_name.getText().toString());
                }

                if (!validStaffPhone()) {
                    return;
                }else {
                    staff.setStaff_phone(text_staff_phone.getText().toString().equals("") ? "no phone" : MyUtils.numFilter(text_staff_phone.getText().toString()));
                }

                staff.setStaff_manager(text_staff_manager.getText().toString());

                if (staff.getStaff_id() == 0) {
                    staffDao.addStaff(staff);
                }else {
                    staffDao.updateStaff(staff);
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
                case R.id.lastName_staff_text:
                    validStaffLastName();
                    break;
                case R.id.phone_staff_text:
                    validStaffPhone();
                    break;
            }
        }
    }

    private boolean validStaffName() {
        if (text_staff_name.getText().toString().trim().isEmpty()) {
            layout_staff_name.setError(getString(R.string.err_empty_staff_name));
            requestFocus(text_staff_name);
            return false;
        }else {
            layout_staff_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validStaffLastName() {
        if (text_staff_last_name.getText().toString().trim().isEmpty()) {
            layout_staff_last_name.setError(getString(R.string.err_empty_staff_last_name));
            requestFocus(text_staff_last_name);
            return false;
        }else {
            layout_staff_last_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validStaffPhone() {
        if (!text_staff_phone.getText().toString().trim().isEmpty() && (text_staff_phone.getText().toString().trim().toCharArray().length < MIN_PHONE_LENGTH || text_staff_phone.getText().toString().trim().toCharArray().length > MAX_PHONE_LENGTH)) {
            layout_staff_phone.setError(getString(R.string.err_incorrect_phone));
            requestFocus(text_staff_phone);
            return false;
        }else {
            layout_staff_phone.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validStaffManager() {
        if (text_staff_manager.getText().toString().trim().isEmpty()) {
            layout_staff_manager.setError(getString(R.string.err_empty_staff_manager));
            requestFocus(text_staff_manager);
            return false;
        }else {
            layout_staff_manager.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
