package com.tay.ian.sphtech.Common;

import java.text.NumberFormat;

/**
 *
 * @author Ian
 *
 **/

public class NumberFormatter {

    public static String numberFormat(double ori, int min , int max){

        String formatted = String.valueOf(ori);

        try{
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(false);
            nf.setMinimumFractionDigits(min);
            nf.setMaximumFractionDigits(max);

            formatted = nf.format(ori);

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return formatted;


    }
}
