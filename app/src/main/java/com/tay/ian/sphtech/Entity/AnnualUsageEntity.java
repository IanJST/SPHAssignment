package com.tay.ian.sphtech.Entity;

import java.util.List;

/**
 *
 * @author Ian
 *
 * For annual data usage
 *
 **/

public class AnnualUsageEntity {

    private String year;
    private double usage;
    private List<DataUsageEntity> quarterList;

    public AnnualUsageEntity(String year, double usage, List<DataUsageEntity> quarterList ){
        this.year = year;
        this.usage = usage;
        this.quarterList = quarterList;
    }


    public String getYear() {
        return year;
    }

    public double getUsage() {
        return usage;
    }

    public List<DataUsageEntity> getQuarterList() { return quarterList; }
}
