package com.ben.fdam_ver_020.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.activity.AddDeviceActivity;
import com.ben.fdam_ver_020.activity.InfoDeviceActivity;
import com.ben.fdam_ver_020.bean.Description;
import com.ben.fdam_ver_020.database.DescriptionDaoImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.DatePickerDialog.*;

public class AddDescriptionDialog extends DialogFragment implements OnClickListener, View.OnClickListener {

    private View view;
    private TextInputLayout layout_add_date, layout_add_description;
    private EditText dialog_add_date, dialog_add_description;
    private ImageButton setDate;
    private Button save, close;
    private int my_day, my_month, my_year;
    private int device_id;
    //private Description description;

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

            if (bundle.getInt("deviceId") != 0) {
                device_id = bundle.getInt("deviceId");
            }

            /*if (bundle.getParcelable("description") != null) {
                this.description = bundle.getParcelable("description");
            }*/
        }

        view = inflater.inflate(R.layout.dialog_add_description, null);
        dialog_add_date = (EditText) view.findViewById(R.id.textEdit_dialod_add_date);
        dialog_add_description = (EditText) view.findViewById(R.id.textEdit_dialog_description);
        layout_add_date = (TextInputLayout) view.findViewById(R.id.textInput_dialod_add_date);
        layout_add_description = (TextInputLayout) view.findViewById(R.id.textInput_dialog_description);
        setDate = (ImageButton) view.findViewById(R.id.imageButton_date_picker);
        save = (Button) view.findViewById(R.id.button_dialog_save);
        close = (Button) view.findViewById(R.id.button_dialog_cancel);

        dialog_add_date.addTextChangedListener(new AddDescriptionDialog.MyTextWatcher(dialog_add_date));
        dialog_add_description.addTextChangedListener(new AddDescriptionDialog.MyTextWatcher(dialog_add_description));

        /*if (description != null) {
            dialog_add_date.setText(description.getDescription_date());
            dialog_add_description.setText(description.getDescription_item());
            this.device_id = description.getDevice_id();
        }*/

        save.setOnClickListener(this);
        close.setOnClickListener(this);
        setDate.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(false);

        return builder.create();
    }

    @Override
    public void onClick(View v) {

        OnDateSetListener dateSetListener;
        final Calendar calendar = Calendar.getInstance();

        switch (v.getId()) {
            case R.id.button_dialog_save:

                Toast toast = Toast.makeText(getActivity(), dialog_add_date.getText().toString(), Toast.LENGTH_SHORT);
                toast.show();

                DescriptionDaoImpl descriptionDao = new DescriptionDaoImpl(getActivity());

                Description description = new Description();
                description.setDevice_id(device_id);

                if (!validDate()) {
                    return;
                }else {
                    description.setDescription_date(dialog_add_date.getText().toString());
                }

                if (!validDescription()) {
                    return;
                }else {
                    description.setDescription_item(dialog_add_description.getText().toString());
                }

                descriptionDao.addDescription(description);

                dialogClose.reloadDescription();

                dismiss();

                break;

            case R.id.button_dialog_cancel:

                dismiss();

                break;

            case R.id.imageButton_date_picker:

                my_day = calendar.get(Calendar.DAY_OF_MONTH);
                my_month = calendar.get(Calendar.MONTH);
                my_year = calendar.get(Calendar.YEAR);

                dateSetListener = new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //my_day = dayOfMonth;
                        //my_month = month;
                        //my_year = year;

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

                        String date = dateFormat.format(calendar.getTime()); //my_day + "." + my_month + "." + my_year;

                        dialog_add_date.setText(date);
                    }
                };

                Dialog datePicker = new DatePickerDialog(getActivity(), dateSetListener, my_year, my_month, my_day);

                datePicker.show();

                break;
        }
    }

    private boolean validDate() {
        if (dialog_add_date.getText().toString().trim().isEmpty()) {
            layout_add_date.setError(getString(R.string.err_empty_date));
            return false;
        }else {
            layout_add_date.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validDescription() {
        if (dialog_add_description.getText().toString().trim().isEmpty()) {
            layout_add_description.setError(getString(R.string.err_empty_description));
            return false;
        }else {
            layout_add_description.setErrorEnabled(false);
        }

        return true;
    }

    public interface DialogClose {
        void reloadDescription();
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
                case R.id.textEdit_dialod_add_date:
                    validDate();
                    break;
                case R.id.textEdit_dialog_description:
                    validDescription();
                    break;
            }
        }
    }
}
