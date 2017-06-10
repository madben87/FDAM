package com.ben.fdam_ver_020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.bean.Staff;
import com.ben.fdam_ver_020.database.StaffDaoImpl;

public class InfoStaffActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView card_staff_info;
    private TextView text_info_staff_lastName;
    private TextView text_info_staff_name;
    private TextView text_info_staff_phone;
    private TextView text_info_staff_manager;
    private ImageButton view_info_staff_delete;
    private ImageButton view_info_staff_description;
    private ImageButton view_info_staff_edit;

    private Staff staff;
    private StaffDaoImpl staffDao = new StaffDaoImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_detail);

        staff = getIntent().getParcelableExtra("Staff");

        initView(staff);
    }

    @Override
    public void onClick(View v) {

        String s = "";

        switch (v.getId()) {
            case R.id.imageButton_info_staff_delete:

                staffDao.deleteStaff(staff);
                Intent intentDelete = new Intent(v.getContext(), MainActivity.class);
                intentDelete.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//clear stack activity
                v.getContext().startActivity(intentDelete);

                s = "delete staff : " + staff.getStaff_last_name();

                break;
            case R.id.imageButton_info_staff_description:
                s = "Description";
                break;
            case R.id.imageButton_info_staff_edit:

                Intent intentEdit = new Intent(v.getContext(), AddStaffActivity.class);
                intentEdit.putExtra("Staff", staff);
                v.getContext().startActivity(intentEdit);

                s = "edit staff : " + staff.getStaff_last_name();

                break;
        }

        Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void initView(Staff staff) {

        card_staff_info = (CardView) findViewById(R.id.card_staff_info);
        card_staff_info.setPreventCornerOverlap(false); //Instead, it adds padding to avoid such intersection (See setPreventCornerOverlap(boolean) to change this behavior)

        text_info_staff_lastName = (TextView) findViewById(R.id.textView_info_staff_lastName);
        text_info_staff_name = (TextView) findViewById(R.id.textView_info_staff_name);
        text_info_staff_phone = (TextView) findViewById(R.id.textView_info_staff_phone);
        text_info_staff_manager = (TextView) findViewById(R.id.textView_info_staff_maneger);

        view_info_staff_delete = (ImageButton) findViewById(R.id.imageButton_info_staff_delete);
        view_info_staff_description = (ImageButton) findViewById(R.id.imageButton_info_staff_description);
        view_info_staff_edit = (ImageButton) findViewById(R.id.imageButton_info_staff_edit);
        //view_info_phone_history = (ImageButton) findViewById(R.id.imageButton_info_phone_history);
        //view_info_description_history = (ImageButton) findViewById(R.id.imageButton_info_desc_history);
        view_info_staff_delete.setOnClickListener(this);
        view_info_staff_description.setOnClickListener(this);
        view_info_staff_edit.setOnClickListener(this);
        //view_info_phone_history.setOnClickListener(this);
        //view_info_description_history.setOnClickListener(this);

        text_info_staff_lastName.setText(String.valueOf(staff.getStaff_last_name()));
        text_info_staff_name.setText(staff.getStaff_name());
        text_info_staff_phone.setText(staff.getStaff_phone());
        text_info_staff_manager.setText(staff.getStaff_manager());
    }
}
