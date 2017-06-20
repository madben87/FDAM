package com.ben.fdam_ver_020.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Staff implements Parcelable {

    private int staff_id;
    private String staff_name;
    private String staff_last_name;
    private String staff_phone;
    private String staff_manager;

    protected Staff(Parcel in) {
        staff_id = in.readInt();
        staff_name = in.readString();
        staff_last_name = in.readString();
        staff_phone = in.readString();
        staff_manager = in.readString();
    }

    public Staff() {}

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getStaff_last_name() {
        return staff_last_name;
    }

    public void setStaff_last_name(String staff_last_name) {
        this.staff_last_name = staff_last_name;
    }

    public String getStaff_phone() {
        return staff_phone;
    }

    public void setStaff_phone(String staff_phone) {
        this.staff_phone = staff_phone;
    }

    public String getStaff_manager() {
        return staff_manager;
    }

    public void setStaff_manager(String staff_manager) {
        this.staff_manager = staff_manager;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(staff_id);
        dest.writeString(staff_name);
        dest.writeString(staff_last_name);
        dest.writeString(staff_phone);
        dest.writeString(staff_manager);
    }

    public static final Creator<Staff> CREATOR = new Creator<Staff>() {
        @Override
        public Staff createFromParcel(Parcel in) {
            return new Staff(in);
        }

        @Override
        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };

    @Override
    public String toString() {
        return staff_last_name;
    }
}
