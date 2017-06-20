package com.ben.fdam_ver_020.activity;

import android.content.Intent;
import android.app.DialogFragment;
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
import com.ben.fdam_ver_020.dialog.AddDescriptionDialog;

public class InfoDescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView description_info_date, description_info_item;
    private ImageButton view_description_info_delete, view_description_info_edit;
    private CardView card_info;

    private Description description;
    private DeviceDaoImpl deviceDao = new DeviceDaoImpl(this);
    private DescriptionDaoImpl descriptionDao = new DescriptionDaoImpl(this);
    //private DialogFragment editDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_description);

        description = getIntent().getParcelableExtra("Description");

        initView(description);

        //editDialog = new AddDescriptionDialog();

        /*Bundle bundle = new Bundle();
        bundle.putParcelable("description", description);
        editDialog.setArguments(bundle);*/
    }

    private void initView(Description description) {

        card_info = (CardView) findViewById(R.id.card_description_info);
        card_info.setPreventCornerOverlap(false);

        description_info_date = (TextView) findViewById(R.id.textView_description_date);
        description_info_item = (TextView) findViewById(R.id.textView_info_description_item);

        description_info_date.setText(description.getDescription_date());
        description_info_item.setText(description.getDescription_item());

        view_description_info_delete = (ImageButton) findViewById(R.id.imageButton_description_delete);
        view_description_info_edit = (ImageButton) findViewById(R.id.imageButton_description_edit);


        view_description_info_delete.setOnClickListener(this);
        view_description_info_edit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String s = "";

        switch (v.getId()) {
            case R.id.imageButton_description_delete:

                descriptionDao.deleteDescription(description);
                Intent intentDelete = new Intent(v.getContext(), InfoDeviceActivity.class);
                intentDelete.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// FLAG_ACTIVITY_CLEAR_TOP - clear all activities after the called
                intentDelete.putExtra("Device", deviceDao.getDeviceById(description.getDevice_id()));
                v.getContext().startActivity(intentDelete);

                s = "Press Delete";
                break;

            case R.id.imageButton_description_edit:

                //editDialog.show(getFragmentManager(), "dialogDescriptionAdd");

                Intent intentEdit = new Intent(v.getContext(), AddDescriptionActivity.class);
                //intentEdit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// FLAG_ACTIVITY_CLEAR_TOP - clear all activities after the called
                intentEdit.putExtra("Description", description);
                v.getContext().startActivity(intentEdit);

                s = "Press Edit";
                break;
        }

        Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast.show();
    }
}
