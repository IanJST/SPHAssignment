package com.tay.ian.sphtech.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tay.ian.sphtech.Common.NumberFormatter;
import com.tay.ian.sphtech.Entity.AnnualUsageEntity;
import com.tay.ian.sphtech.Entity.DataUsageEntity;
import com.tay.ian.sphtech.R;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Ian
 *
 **/

public class UsageAdapter extends RecyclerView.Adapter<UsageAdapter.ViewHolder> {


    public interface OnItemClickListener{
        void onItemClick(AnnualUsageEntity item);
    }

    private final List<AnnualUsageEntity> dataUsageList;
    private final OnItemClickListener listener;

    public UsageAdapter(List<AnnualUsageEntity> dataUsageList, OnItemClickListener listener){
        this.dataUsageList =dataUsageList;
        this.listener =listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataUsageList.get(position), listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.layout_usage_card, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return dataUsageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvYearVal;
        private TextView tvUsageVal;
        private ImageView ivDeclined;

        public ViewHolder(View v) {
            super(v);
            tvYearVal = (TextView)  v.findViewById(R.id.tvYearValue);
            tvUsageVal = (TextView)  v.findViewById(R.id.tvUsageValue);
            ivDeclined = (ImageView) v.findViewById(R.id.ivDeclined);
        }

        public void bind(final AnnualUsageEntity usage, final OnItemClickListener listener){

            tvYearVal.setText(usage.getYear());
            tvUsageVal.setText(NumberFormatter.numberFormat(usage.getUsage(), 0,4));

            List<DataUsageEntity> quarterList = new ArrayList<>();
            quarterList = usage.getQuarterList();

            boolean isDeclined = false;

            try{

                for(int i = 1; i < quarterList.size(); i++){

                    if(quarterList.get(i).getVolumeUsage() < quarterList.get(i-1).getVolumeUsage()){
                        isDeclined= true;
                    }
                }

            }
            catch(IndexOutOfBoundsException e){
                e.printStackTrace();
            }

            if(isDeclined){
                ivDeclined.setVisibility(View.VISIBLE);
                ivDeclined.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(usage);
                    }
                });
            }else{
                ivDeclined.setVisibility(View.GONE);
            }


        }
    }
}
