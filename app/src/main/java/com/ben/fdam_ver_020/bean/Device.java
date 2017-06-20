package com.ben.fdam_ver_020.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Device implements Parcelable {

    private int id;
    private int device_id;
    private String device_name;
    private String device_location;
    private int staff_id;
    private ArrayList<Sim> sims;

    public Device(Parcel in) {
        id = in.readInt();
        device_id = in.readInt();
        device_name = in.readString();
        device_location = in.readString();
        staff_id = in.readInt();
        sims = new ArrayList<>();
        in.readList(sims, getClass().getClassLoader());
    }

    public Device() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_location() {
        return device_location;
    }

    public void setDevice_location(String device_location) {
        this.device_location = device_location;
    }

    public ArrayList<Sim> getSims() {
        return sims;
    }

    public void setSims(ArrayList<Sim> sim) {
        this.sims = sim;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(device_id);
        dest.writeString(device_name);
        dest.writeString(device_location);
        dest.writeInt(staff_id);
        dest.writeList(sims);
    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
}
