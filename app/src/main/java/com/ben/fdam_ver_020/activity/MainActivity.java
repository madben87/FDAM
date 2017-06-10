package com.ben.fdam_ver_020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.database.DeviceDaoImpl;
import com.ben.fdam_ver_020.database.DBHelper;
import com.ben.fdam_ver_020.fragment.DeviceListFragment;
import com.ben.fdam_ver_020.fragment.StaffListFragment;
import com.ben.fdam_ver_020.adapter.PagerAdapter;
import com.ben.fdam_ver_020.utils.Parser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private List<Device> list;
    private DBHelper dbHelper;
    private PagerAdapter adapter;
    String s;
    private DeviceDaoImpl deviceDaoImpl = new DeviceDaoImpl(this);

    private MenuItem menuItem_search;
    private MaterialSearchView materialSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        //setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);

        dbHelper.onCreate(dbHelper.getReadableDatabase());

        /*dbHelper.onUpgrade(dbHelper.getReadableDatabase(), 1, 2);*/

        initFab();

        initDrawer();

        /*initData();*/

        initViewPager(viewPager);

        materialSearchView = (MaterialSearchView) findViewById(R.id.search_view);
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s = adapter.getPageTitle(viewPager.getCurrentItem()).toString();

                Class activityClass = null;

                if (adapter.getPageTitle(viewPager.getCurrentItem()).toString().equals("Device")) {
                    activityClass = AddDeviceActivity.class;
                }else if (adapter.getPageTitle(viewPager.getCurrentItem()).toString().equals("Staff")) {
                    activityClass = AddStaffActivity.class;
                }

                Intent intent = new Intent(getApplicationContext(), activityClass);
                startActivity(intent);

                Toast toast1 = Toast.makeText(getApplicationContext(), "Create New Item = " + s, Toast.LENGTH_SHORT);
                toast1.show();
            }
        });
    }

    private void initDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DeviceListFragment(), "Device");
        adapter.addFragment(new StaffListFragment(), "Staff");
        //adapter.addFragment(new Test2(), "Test 2");
        viewPager.setAdapter(adapter);
    }

    private void initData() {

        try {

            InputStream inputStream = getAssets().open("март.xls");

            list = Parser.readFromExcel(inputStream, getApplicationContext());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_toolbar, menu);

        materialSearchView.setVoiceSearch(false);

        menuItem_search = menu.findItem(R.id.action_search);

        materialSearchView.setMenuItem(menuItem_search);

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast toast1 = Toast.makeText(getApplicationContext(), "Listener 1", Toast.LENGTH_SHORT);
                toast1.show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Toast toast1 = Toast.makeText(getApplicationContext(), "Listener 2 - " + newText, Toast.LENGTH_SHORT);
                toast1.show();

                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

                Toast toast1 = Toast.makeText(getApplicationContext(), "Listener 3", Toast.LENGTH_SHORT);
                toast1.show();
            }

            @Override
            public void onSearchViewClosed() {

                Toast toast1 = Toast.makeText(getApplicationContext(), "Listener 4", Toast.LENGTH_SHORT);
                toast1.show();
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar device_item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            Toast toast = Toast.makeText(getApplicationContext(), "Menu Settings", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view device_item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

            /*for (Device elem : list) {
                deviceDaoImpl.addDevice(elem);
            }*/

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(getApplicationContext(), InfoDeviceActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            initData();

            for (Device elem : list) {
                deviceDaoImpl.addDevice(elem);
            }

            initViewPager(viewPager);

        } else if (id == R.id.nav_send) {

            dbHelper.onUpgrade(dbHelper.getReadableDatabase(), 1, 2);

            initViewPager(viewPager);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
