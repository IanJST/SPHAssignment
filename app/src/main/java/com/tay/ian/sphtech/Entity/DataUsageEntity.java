package com.tay.ian.sphtech.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Ian
 *
 * For data usage of each quarter
 *
 **/

public class DataUsageEntity {

    @Expose
    @SerializedName("volume_of_mobile_data")
    private double volumeUsage;

    @Expose
    @SerializedName("quarter")
    private String quarter;

    @Expose
    @SerializedName("_id")
    private int id;

    public double getVolumeUsage() {
        return volumeUsage;
    }

    public void setVolumeUsage(double volumeUsage) {
        this.volumeUsage = volumeUsage;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
