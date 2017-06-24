package com.ben.fdam_ver_020.utils;

import com.ben.fdam_ver_020.bean.Description;
import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.bean.Sim;
import com.ben.fdam_ver_020.bean.SimHistory;
import com.ben.fdam_ver_020.bean.Staff;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MyUtils {

    /*Parse String to Phone number*/

    public static String numFilter(String string) {

        StringBuilder out = new StringBuilder();

        for (char elem : string.toCharArray()) {

            if (Character.isDigit(elem)) {

                out.append(elem);
            }
        }

        return out.toString();
    }

    /*Get current Date*/

    public static String currentDate() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        return dateFormat.format(calendar.getTime()); //calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
    }

    /*Create ArrayList*/

    public static ArrayList<Staff> toArrayList(Staff object) {

        ArrayList<Staff> arrayList = new ArrayList<>();
        arrayList.add(object);

        return arrayList;
    }

    public static ArrayList<Device> toArrayList(Device object) {

        ArrayList<Device> arrayList = new ArrayList<>();
        arrayList.add(object);

        return arrayList;
    }

    public static ArrayList<Description> toArrayList(Description object) {

        ArrayList<Description> arrayList = new ArrayList<>();
        arrayList.add(object);

        return arrayList;
    }

    public static ArrayList<Sim> toArrayList(Sim object) {

        ArrayList<Sim> arrayList = new ArrayList<>();
        arrayList.add(object);

        return arrayList;
    }

    public static ArrayList<SimHistory> toArrayList(SimHistory object) {

        ArrayList<SimHistory> arrayList = new ArrayList<>();
        arrayList.add(object);

        return arrayList;
    }

    /*Format phone number to local format*/
    public static String phoneFormatter(String num) {
        //String s = num.replaceFirst("^0*", "");
        return num.replaceFirst("^0*", "");
    }
}
