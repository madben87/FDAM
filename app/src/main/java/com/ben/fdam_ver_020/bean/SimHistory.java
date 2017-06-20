package com.ben.fdam_ver_020.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class SimHistory implements Parcelable {
    private int history_id;
    private int id_sim;
    private String date_install;
    private String date_uninstall;
    private String id_device;

    public SimHistory() {
    }

    protected SimHistory(Parcel in) {
        history_id = in.readInt();
        id_sim = in.readInt();
        date_install = in.readString();
        date_uninstall = in.readString();
        id_device = in.readString();
    }

    public static final Creator<SimHistory> CREATOR = new Creator<SimHistory>() {
        @Override
        public SimHistory createFromParcel(Parcel in) {
            return new SimHistory(in);
        }

        @Override
        public SimHistory[] newArray(int size) {
            return new SimHistory[size];
        }
    };

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public int getId_sim() {
        return id_sim;
    }

    public void setId_sim(int id_sim) {
        this.id_sim = id_sim;
    }

    public String getDate_install() {
        return date_install;
    }

    public void setDate_install(String date_install) {
        this.date_install = date_install;
    }

    public String getDate_uninstall() {
        return date_uninstall;
    }

    public void setDate_uninstall(String date_uninstall) {
        this.date_uninstall = date_uninstall;
    }

    public String getId_device() {
        return id_device;
    }

    public void setId_device(String id_device) {
        this.id_device = id_device;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(history_id);
        dest.writeInt(id_sim);
        dest.writeString(date_install);
        dest.writeString(date_uninstall);
        dest.writeString(id_device);
    }
}
