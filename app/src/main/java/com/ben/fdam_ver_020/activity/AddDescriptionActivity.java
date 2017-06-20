package com.ben.fdam_ver_020.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.bean.Description;
import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.bean.Sim;
import com.ben.fdam_ver_020.bean.SimHistory;
import com.ben.fdam_ver_020.database.DescriptionDaoImpl;
import com.ben.fdam_ver_020.utils.MyUtils;

import java.util.Calendar;

public class AddDescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout textInput_add_date, textInput_dialog_description;
    private EditText textEdit_add_date, textEdit_dialog_description;
    private ImageButton datePicker;
    private int my_day, my_month, my_year;
    private Description description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description);

        description = getIntent().getParcelableExtra("Description");

        initView(description);

        initFab();
    }

    private void initView(Description description) {

        textInput_add_date = (TextInputLayout) findViewById(R.id.textInput_add_date);
        textInput_dialog_description = (TextInputLayout) findViewById(R.id.textInput_dialog_description);

        textEdit_add_date = (EditText) findViewById(R.id.textEdit_add_date);
        textEdit_dialog_description = (EditText) findViewById(R.id.textEdit_dialog_description);

        datePicker = (ImageButton) findViewById(R.id.imageButton_activity_date_picker);

        textEdit_add_date.setText(description.getDescription_date());
        textEdit_dialog_description.setText(description.getDescription_item());
        datePicker.setOnClickListener(this);
    }

    private void initFab() {
        FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.fab_add_description);
        fab_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DatePickerDialog.OnDateSetListener dateSetListener;
        Calendar calendar = Calendar.getInstance();

        switch (v.getId()) {
            case R.id.fab_add_description:

                Toast toast = Toast.makeText(this, textEdit_add_date.getText().toString(), Toast.LENGTH_SHORT);
                toast.show();

                DescriptionDaoImpl descriptionDao = new DescriptionDaoImpl(this);

                if (!validDate()) {
                    return;
                } else {
                    description.setDescription_date(textEdit_add_date.getText().toString());
                }

                if (!validDescription()) {
                    return;
                } else {
                    description.setDescription_item(textEdit_dialog_description.getText().toString());
                }

                descriptionDao.updateDescription(description);

                Intent intentInfo = new Intent(v.getContext(), InfoDescriptionActivity.class);
                intentInfo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// FLAG_ACTIVITY_CLEAR_TOP - clear all activities after the called
                intentInfo.putExtra("Description", description);
                v.getContext().startActivity(intentInfo);

                break;

            case R.id.imageButton_activity_date_picker:

                my_day = calendar.get(Calendar.DAY_OF_MONTH);
                my_month = calendar.get(Calendar.MONTH);
                my_year = calendar.get(Calendar.YEAR);

                dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        my_day = dayOfMonth;
                        my_month = month;
                        my_year = year;
                        String date = my_day + "." + my_month + "." + my_year;
                        textEdit_add_date.setText(date);
                    }
                };

                Dialog datePicker = new DatePickerDialog(this, dateSetListener, my_year, my_month, my_day);

                datePicker.show();

                break;
        }
    }

        private boolean validDate() {
            if (textEdit_add_date.getText().toString().trim().isEmpty()) {
                textInput_add_date.setError(getString(R.string.err_empty_date));
                return false;
            }else {
                textInput_add_date.setErrorEnabled(false);
            }

            return true;
        }

        private boolean validDescription() {
            if (textEdit_dialog_description.getText().toString().trim().isEmpty()) {
                textInput_dialog_description.setError(getString(R.string.err_empty_description));
                return false;
            }else {
                textInput_dialog_description.setErrorEnabled(false);
            }

            return true;
        }
}
