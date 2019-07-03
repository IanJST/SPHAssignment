package com.tay.ian.sphtech;


import com.tay.ian.sphtech.Entity.DataUsageEntity;

import java.util.List;

public class DataComponent {


    private static final String TAG = DataComponent.class.getSimpleName();
    private static DataComponent dataComponent = new DataComponent();
    public static DataComponent getInstance(){
        return dataComponent;
    }


    private List<DataUsageEntity> usageList;
    private int firstIndex;

    public void setUsageList(List<DataUsageEntity> usageList){
        this.usageList= usageList;
    }

    public List<DataUsageEntity> getUsageList(){
        return usageList;
    }

    public void setFirstIndex(int firstIndex){
        this.firstIndex =firstIndex;
    }

    public int getFirstIndex(){
        return firstIndex;
    }


}
