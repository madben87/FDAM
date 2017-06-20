package com.ben.fdam_ver_020.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Sim implements Parcelable {

    private int id_sim;
    private String sim_num;
    private int device_id;
    private ArrayList<SimHistory> simHistories;

    protected Sim(Parcel in) {
        id_sim = in.readInt();
        sim_num = in.readString();
        device_id = in.readInt();
        simHistories = new ArrayList<>();
        in.readList(simHistories, getClass().getClassLoader());
    }

    public Sim() {}

    public static final Creator<Sim> CREATOR = new Creator<Sim>() {
        @Override
        public Sim createFromParcel(Parcel in) {
            return new Sim(in);
        }

        @Override
        public Sim[] newArray(int size) {
            return new Sim[size];
        }
    };

    public int getId_sim() {
        return id_sim;
    }

    public void setId_sim(int id_sim) {
        this.id_sim = id_sim;
    }

    public String getSim_num() {
        return sim_num;
    }

    public void setSim_num(String sim_num) {
        this.sim_num = sim_num;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public ArrayList<SimHistory> getSimHistories() {
        return simHistories;
    }

    public void setSimHistories(ArrayList<SimHistory> simHistories) {
        this.simHistories = simHistories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_sim);
        dest.writeString(sim_num);
        dest.writeInt(device_id);
        dest.writeList(simHistories);
    }
}
