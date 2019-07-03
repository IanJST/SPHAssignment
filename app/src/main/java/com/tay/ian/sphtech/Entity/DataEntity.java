package com.tay.ian.sphtech.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 *
 * @author Ian
 *
 * For data retrieved
 *
 **/

public class DataEntity {

    @Expose
    @SerializedName("records")
    private List<DataUsageEntity> records;

    @Expose
    @SerializedName("total")
    private int total;

    public List<DataUsageEntity> getRecords() {
        return records;
    }

    public void setRecords(List<DataUsageEntity> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
