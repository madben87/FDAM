package com.ben.fdam_ver_020.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.bean.Sim;
import com.ben.fdam_ver_020.bean.SimHistory;
import com.ben.fdam_ver_020.bean.Staff;
import com.ben.fdam_ver_020.database.DeviceDaoImpl;
import com.ben.fdam_ver_020.database.SimDaoImpl;
import com.ben.fdam_ver_020.database.StaffDaoImpl;
import com.ben.fdam_ver_020.utils.AppSettings;
import com.ben.fdam_ver_020.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.ben.fdam_ver_020.R.id.autoComplete_SimNum;

public class AddDeviceActivity extends AppCompatActivity {

    private TextInputLayout layout_id, layout_name, layout_location, layout_description, layout_new_phone;
    private EditText text_id, text_name, text_location, text_description/*, text_new_phone*/;
    private Spinner spinner_staff;
    private AutoCompleteTextView autoCompleteTextView;
    //private TextView local_code;
    //private ListView listView_autocomplete;

    private DeviceDaoImpl deviceDaoImpl = new DeviceDaoImpl(this);
    private StaffDaoImpl staffDao = new StaffDaoImpl(this);
    private SimDaoImpl simDao = new SimDaoImpl(this);
    private Device device;
    private ArrayList<Sim> sims;
    private AsyncDao asyncDao;

    public static final int ID_LENGTH = 4;
    public static final int MIN_PHONE_LENGTH = 9;
    public static final int MAX_PHONE_LENGTH = 13;

    private int spinner_staff_position;
    private ArrayList<Staff> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        asyncDao = new AsyncDao();
        asyncDao.execute();

        try {
            sims = asyncDao.get();
            Toast toast = Toast.makeText(getApplicationContext(), "Sims size " + sims.size(), Toast.LENGTH_SHORT);
            toast.show();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (Device.class.isInstance(getIntent().getParcelableExtra("Device"))) {
            initTextField((Device) getIntent().getParcelableExtra("Device"));
        }else {
            initTextField();
        }

        initSpinner();

        initFab();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*asyncDao = new AsyncDao();
        asyncDao.execute();

        try {
            sims = asyncDao.get();
            Toast toast = Toast.makeText(getApplicationContext(), "Sims size " + sims.size(), Toast.LENGTH_SHORT);
            toast.show();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/
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
        //text_new_phone = (EditText) findViewById(R.id.new_phone_text);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(autoComplete_SimNum);
        //local_code = (TextView) findViewById(R.id.label_local_code);
        //listView_autocomplete = (ListView) findViewById(R.id.listView_autocomplete);

        text_id.addTextChangedListener(new MyTextWatcher(text_id));
        text_name.addTextChangedListener(new MyTextWatcher(text_name));
        text_location.addTextChangedListener(new MyTextWatcher(text_location));
        text_description.addTextChangedListener(new MyTextWatcher(text_description));
        //text_new_phone.addTextChangedListener(new MyTextWatcher(text_new_phone));
        autoCompleteTextView.addTextChangedListener(new MyTextWatcher(autoCompleteTextView));

        //local_code.setText(AppSettings.UA_PHONE_CODE);

        /*ArrayAdapter<Sim> simArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sims);
        autoCompleteTextView.setAdapter(simArrayAdapter);*/
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
        //text_new_phone = (EditText) findViewById(R.id.new_phone_text);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(autoComplete_SimNum);
        //listView_autocomplete = (ListView) findViewById(R.id.listView_autocomplete);

        text_id.addTextChangedListener(new MyTextWatcher(text_id));
        text_name.addTextChangedListener(new MyTextWatcher(text_name));
        text_location.addTextChangedListener(new MyTextWatcher(text_location));
        text_description.addTextChangedListener(new MyTextWatcher(text_description));
        //text_new_phone.addTextChangedListener(new MyTextWatcher(text_new_phone));
        autoCompleteTextView.addTextChangedListener(new MyTextWatcher(autoCompleteTextView));
        //local_code = (TextView) findViewById(R.id.label_local_code);

        //local_code.setText(AppSettings.UA_PHONE_CODE);

        /*ArrayAdapter<Sim> simArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sims);
        autoCompleteTextView.setAdapter(simArrayAdapter);*/

        text_id.setText(String.valueOf(device.getDevice_id()));
        text_name.setText(device.getDevice_name());
        text_location.setText(device.getDevice_location());
        text_description.setText("Test Description");

        if (device.getSims().size() > 0) {
            //text_new_phone.setText(device.getSims().get(device.getSims().size()-1).getSim_num());
            autoCompleteTextView.setText(device.getSims().get(device.getSims().size()-1).getSim_num());
        }else {
            //text_new_phone.setText("No sim");
            //autoCompleteTextView.setText("No sim");
        }

        this.device = device;
    }

    private void initAutoComplete(ArrayList<Sim> sims) {
        NamesAdapter simArrayAdapter = new NamesAdapter(this, android.R.layout.simple_spinner_dropdown_item, R.id.label_sim, sims);
        autoCompleteTextView.setAdapter(simArrayAdapter);
        //autoCompleteTextView.showDropDown();
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

                boolean simIsExist = false;

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
                }else { //если номер уже существует и не установлен установить, если установлена - ошибка, если не существует - создать

                    Sim sim = new Sim();

                    //boolean simIsExist = false;

                    for (Sim elem : sims) {
                        if (elem.getSim_num().equals(autoCompleteTextView.getText().toString())) {
                            if (elem.getDevice_id() > 0) {
                                Toast toast = Toast.makeText(getApplicationContext(), elem.getSim_num() + " - Этот номер уже используется", Toast.LENGTH_SHORT);
                                toast.show();
                                return;
                            }else {
                                sim = elem;
                            }
                            simIsExist = true;
                            break;
                        }
                    }

                    if (!simIsExist) {
                        sim.setSim_num(MyUtils.phoneFormatter(MyUtils.numFilter(autoCompleteTextView.getText().toString())));
                    }

                    SimHistory simHistory = new SimHistory();
                    simHistory.setDate_install(MyUtils.currentDate());
                    simHistory.setId_device(String.valueOf(device.getDevice_id()));

                    sim.setSimHistories(MyUtils.toArrayList(simHistory));

                    device.setSims(MyUtils.toArrayList(sim));
                }

                device.setStaff_id(list.get(spinner_staff.getSelectedItemPosition()).getStaff_id());

                if (device.getId() == 0) {
                    deviceDaoImpl.addDevice(device, simIsExist);
                }else {
                    deviceDaoImpl.updateDevice(device, simIsExist);
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
                    //Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
                    //toast.show();
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
                /*case R.id.new_phone_text:
                    validNewPhone();
                    break;*/
                case autoComplete_SimNum:

                    validNewPhone();

                    ArrayList<Sim> list = new ArrayList<>();
                    String str = s.toString().replaceFirst("^0*", "");

                    if (!s.toString().equals("")) {
                        for (Sim elem : sims) {
                            if (elem.getSim_num().contains(str)) {
                                list.add(elem);

                                //Toast toast = Toast.makeText(getApplicationContext(), elem.getSim_num(), Toast.LENGTH_SHORT);
                                //toast.show();
                            }
                        }
                    }

                    initAutoComplete(list);

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
        if (autoCompleteTextView.getText().toString().trim().isEmpty() && autoCompleteTextView.getText().toString().trim().toCharArray().length < MIN_PHONE_LENGTH || autoCompleteTextView.getText().toString().trim().toCharArray().length > MAX_PHONE_LENGTH) {
            layout_new_phone.setError(getString(R.string.err_incorrect_phone));
            requestFocus(autoCompleteTextView);
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

    private class AsyncDao extends AsyncTask<Void, Void, ArrayList<Sim>> {

        @Override
        protected ArrayList<Sim> doInBackground(Void... params) {
            return simDao.getSims();
        }

        @Override
        protected void onPostExecute(ArrayList<Sim> sims) {
            super.onPostExecute(sims);
            //initAutoComplete();
        }
    }

    public class NamesAdapter extends ArrayAdapter<Sim> {

        private List<Sim> items, suggestions, tempItems;
        private Context context;
        //private int resource, textViewResourceId;

        public NamesAdapter(Context context, int resource, int textViewResourceId, List<Sim> items) {
            super(context, resource, textViewResourceId, items);
            this.context = context;
            //this.resource = resource;
            //this.textViewResourceId = textViewResourceId;
            this.items = items;
            this.suggestions = new ArrayList<Sim>();
            tempItems = new ArrayList<Sim>(items);
        }

        @Override
        public Filter getFilter() {
            return myFilter;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.autocomplete_item, parent, false);
            }

            Sim sim = items.get(position);

            if (sim != null) {
                TextView label_sim = (TextView) view.findViewById(R.id.label_sim);
                if (label_sim != null)
                    label_sim.setText(sim.getSim_num());
            }
            return view;
        }

        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                if (constraint != null) {
                    suggestions.clear();
                    for (Sim sim : tempItems) {
                        if (sim.getSim_num().contains(constraint.toString())) {
                            suggestions.add(sim);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List<Sim> filterList = (ArrayList<Sim>) results.values;
                if (results != null && results.count > 0) {
                    clear();
                    for (Sim sim : filterList) {
                        add(sim);
                        notifyDataSetChanged();
                    }
                }
            }
        };
    }

    /*public class NamesAdapter extends ArrayAdapter<Sim> {
        Context context;
        int resource, textViewResourceId;
        List<Sim> items, tempItems, suggestions;

        public NamesAdapter(Context context, int resource, int textViewResourceId, List<Sim> items) {
            super(context, resource, textViewResourceId, items);
            this.context = context;
            this.resource = resource;
            this.textViewResourceId = textViewResourceId;
            this.items = items;
            tempItems = new ArrayList<Sim>(items); // this makes the difference.
            suggestions = new ArrayList<Sim>();
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.autocomplete_item, parent, false);
            }

            Names names = items.get(position);

            if (names != null) {
                TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
                if (lblName != null)
                    lblName.setText(names.name);
            }
            return view;
        }

        @Override
        public Filter getFilter() {
            return nameFilter;
        }

        Filter nameFilter = new Filter() {

                    @Override
                    public CharSequence convertResultToString(Object resultValue) {
                        String str = ((Names) resultValue).name;
                        return str;
                    }

                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        if (constraint != null) {
                            suggestions.clear();
                            for (Names names : tempItems) {
                                if (names.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                                    suggestions.add(names);
                                }
                            }
                            FilterResults filterResults = new FilterResults();
                            filterResults.values = suggestions;
                            filterResults.count = suggestions.size();
                            return filterResults;
                        } else {
                            return new FilterResults();
                        }
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                        List<Names> filterList = (ArrayList<Names>) results.values;
                        if (results != null && results.count > 0) {
                            clear();
                            for (Names names : filterList) {
                                add(names);
                                notifyDataSetChanged();
                            }
                        }
                    }
            };}*/
}
