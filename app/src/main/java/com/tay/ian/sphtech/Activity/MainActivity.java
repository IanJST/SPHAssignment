package com.tay.ian.sphtech.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tay.ian.sphtech.DataComponent;
import com.tay.ian.sphtech.Network.BaseResponse;
import com.tay.ian.sphtech.Common.Constants;
import com.tay.ian.sphtech.Entity.AnnualUsageEntity;
import com.tay.ian.sphtech.Entity.DataEntity;
import com.tay.ian.sphtech.Entity.DataUsageEntity;
import com.tay.ian.sphtech.Network.OkHttpHelper;
import com.tay.ian.sphtech.R;
import com.tay.ian.sphtech.Adapter.UsageAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author Ian
 *
 * Main Acitivty
 *
 **/

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGet;
    private RecyclerView rvDataUsage;
    private UsageAdapter usageAdapter;
    private Call call;

    private static List<DataUsageEntity> usageData;
    private static int firstIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpHelper.init();

        if(isNetworkConnected()){
            getAllUsage();
        }else{
           usageData = DataComponent.getInstance().getUsageList();
           firstIndex = DataComponent.getInstance().getFirstIndex();
        }

        initView();

    }

    // initialize view components
    private void initView(){

        btnGet = findViewById(R.id.btnGet);
        btnGet.setOnClickListener(this);

        rvDataUsage = findViewById(R.id.rvCardList);
        rvDataUsage.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvDataUsage.setLayoutManager(llm);

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnGet:

                if (isNetworkConnected()){
                    if (usageData != null){
                        getDataUsage();
                    }else{
                        getAllUsage();
                        getDataUsage();
                    }
                }
                // use offline cache
                else if(isNetworkConnected() == false && usageData != null && firstIndex != 0) {
                    displayList(firstIndex);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Kindly check your internet connection", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    // get all data
    private void getAllUsage(){
        try{
            call = OkHttpHelper.getRequestService().getData(Constants.RESOURCE_ID);
            call.enqueue(new Callback<BaseResponse<DataEntity>>() {
                @Override
                public void onResponse(Call<BaseResponse<DataEntity>> call, Response<BaseResponse<DataEntity>> response) {
                    BaseResponse<DataEntity> result = response.body();
                    if(result!=null && result.getResult().getRecords().size() > 0){
                        usageData = result.getResult().getRecords();
                        DataComponent.getInstance().setUsageList(usageData);

                    }else{

                        if(result!=null){
                            Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Invalid response", Toast.LENGTH_LONG).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<DataEntity>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Failed to retrieve data. Check your internet connection", Toast.LENGTH_LONG).show();
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    // get data from 2008
    private void getDataUsage(){
        if(usageData != null){

            try{
                call = OkHttpHelper.getRequestService().getDataByWord(Constants.RESOURCE_ID, "2008");
                call.enqueue(new Callback<BaseResponse<DataEntity>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<DataEntity>> call, Response<BaseResponse<DataEntity>> response) {
                        BaseResponse<DataEntity> result = response.body();
                        if(result!=null && result.getResult().getRecords().size() > 0){

                            firstIndex = result.getResult().getRecords().get(0).getId();
                            DataComponent.getInstance().setFirstIndex(firstIndex);

                            displayList(firstIndex);


                        }else{

                            if(result!=null){
                                Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Invalid response", Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<DataEntity>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed to retrieve data. Check your internet connection", Toast.LENGTH_LONG).show();
                    }
                });

            }catch(Exception e){
                e.printStackTrace();
            }

        }else{
            Toast.makeText(getApplicationContext(), "Try again later", Toast.LENGTH_LONG).show();
            return;
        }


    }



    // calculate quarterly usage and group as annual record and display
    private void displayList(int firstIndex){

        List<DataUsageEntity>  dataList = new ArrayList<>();

        for(int start = firstIndex-1; start < usageData.size(); start ++){
            dataList.add(usageData.get(start));
        }


        List<AnnualUsageEntity> annualList =  new ArrayList<>();
        List<DataUsageEntity> quarterList = new ArrayList<>();
        double annualSum = 0;
        int totalSet = dataList.size()/4;
        int totalRemainder = dataList.size() % 4;
        int j = 0;

        for(int i = 1; i <= dataList.size(); i++){

            annualSum = annualSum + dataList.get(i-1).getVolumeUsage();

            quarterList.add(dataList.get(i-1));

            if(i % 4 == 0 && j <= totalSet){
                String year = dataList.get(i-1).getQuarter().substring(0,4);
                AnnualUsageEntity annualUsage = new AnnualUsageEntity(year, annualSum, quarterList );
                annualList.add(annualUsage);
                j++;
                annualSum = 0;
                quarterList = new ArrayList<>();
            }

            if (i % 4 == totalRemainder && j == totalSet){
                String year = dataList.get(i-1).getQuarter().substring(0,4);
                AnnualUsageEntity annualUsage = new AnnualUsageEntity(year, annualSum,quarterList);
                annualList.add(annualUsage);
            }

        }

        usageAdapter = new UsageAdapter(annualList, new UsageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AnnualUsageEntity item) {
                String result = "Result : ";

                for(int i=0; i < item.getQuarterList().size(); i ++){
                    result = result + "\n" + item.getQuarterList().get(i).getQuarter() + ": " + item.getQuarterList().get(i).getVolumeUsage() ;
                }
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
        rvDataUsage.setAdapter(usageAdapter);

    }


    // check internet connection
    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
