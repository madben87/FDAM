package com.ben.fdam_ver_020.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Description implements Parcelable {

    private int description_id;
    private int device_id;
    private String description_date;
    private String description_item;

    public Description() {
    }

    public int getDescription_id() {
        return description_id;
    }

    public void setDescription_id(int description_id) {
        this.description_id = description_id;
    }

    public String getDescription_date() {
        return description_date;
    }

    public void setDescription_date(String description_date) {
        this.description_date = description_date;
    }

    public String getDescription_item() {
        return description_item;
    }

    public void setDescription_item(String description_item) {
        this.description_item = description_item;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public Description(Parcel in) {
        description_id = in.readInt();
        device_id = in.readInt();
        description_date = in.readString();
        description_item = in.readString();
    }

    public static final Creator<Description> CREATOR = new Creator<Description>() {
        @Override
        public Description createFromParcel(Parcel in) {
            return new Description(in);
        }

        @Override
        public Description[] newArray(int size) {
            return new Description[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(description_id);
        dest.writeInt(device_id);
        dest.writeString(description_date);
        dest.writeString(description_item);
    }
}
