package com.ben.fdam_ver_020.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.activity.InfoDeviceActivity;
import com.ben.fdam_ver_020.bean.Description;
import com.ben.fdam_ver_020.database.DescriptionDaoImpl;

import java.util.Calendar;

import static android.app.DatePickerDialog.*;

public class AddDescriptionDialog extends DialogFragment implements OnClickListener, View.OnClickListener {

    private View view;
    private EditText dialog_add_date, dialog_add_description;
    private ImageButton setDate;
    private Button save, close;
    private int my_day, my_month, my_year;
    private int device_id;

    DialogClose dialogClose;

    public AddDescriptionDialog() {}

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        dialogClose = (DialogClose) getActivity();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            device_id = bundle.getInt("deviceId");
        }

        view = inflater.inflate(R.layout.dialog_add_description, null);
        dialog_add_date = (EditText) view.findViewById(R.id.textEdit_dialod_add_date);
        dialog_add_description = (EditText) view.findViewById(R.id.textEdit_dialog_description);
        setDate = (ImageButton) view.findViewById(R.id.imageButton_date_picker);
        save = (Button) view.findViewById(R.id.button_dialog_save);
        close = (Button) view.findViewById(R.id.button_dialog_cancel);

        save.setOnClickListener(this);
        close.setOnClickListener(this);
        setDate.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(/*inflater.inflate(R.layout.dialog_add_description, null)*/view)
                .setCancelable(false);

        return builder.create();
    }

    @Override
    public void onClick(View v) {

        OnDateSetListener dateSetListener;
        Calendar calendar = Calendar.getInstance();

        switch (v.getId()) {
            case R.id.button_dialog_save:

                Toast toast = Toast.makeText(getActivity(), dialog_add_date.getText().toString(), Toast.LENGTH_SHORT);
                toast.show();

                DescriptionDaoImpl descriptionDao = new DescriptionDaoImpl(getActivity());

                Description description = new Description();
                description.setDevice_id(device_id);
                description.setDescription_date(dialog_add_date.getText().toString());
                description.setDescription_item(dialog_add_description.getText().toString());

                descriptionDao.addDescription(description);

                dialogClose.reloadDescription();

                dismiss();

                break;

            case R.id.button_dialog_cancel:

                dismiss();

                break;

            case R.id.imageButton_date_picker:
                //DialogFragment picker = new MyDatePicker();
                //picker.show(getFragmentManager(), "datePicker");

                my_day = calendar.get(Calendar.DAY_OF_MONTH);
                my_month = calendar.get(Calendar.MONTH);
                my_year = calendar.get(Calendar.YEAR);

                dateSetListener = new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        my_day = dayOfMonth;
                        my_month = month;
                        my_year = year;
                        String date = my_day + "." + my_month + "." + my_year;
                        dialog_add_date.setText(date);
                    }
                };

                Dialog datePicker = new DatePickerDialog(getActivity(), dateSetListener, my_year, my_month, my_day);

                datePicker.show();

                break;
        }
    }

    public interface DialogClose {
        void reloadDescription();
    }
}
