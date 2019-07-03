package com.tay.ian.sphtech.Network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 *
 * @author Ian
 *
 * Base Response for each http call
 *
**/

public class BaseResponse<T> implements Serializable {


    @Expose
    @SerializedName("success")
    private boolean success;

    @Expose
    @SerializedName("result")
    private T result;

    public boolean getSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }


    public boolean isSuccess() {
        return true;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                ", success='" + success +
                ", result=" + result +
                '}';
    }
}
