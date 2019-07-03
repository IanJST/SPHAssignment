package com.tay.ian.sphtech.Network;

import com.tay.ian.sphtech.Entity.DataEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * @author Ian
 *
 * List of service/api call
 *
 **/

public interface RequestService {

    @GET("/api/action/datastore_search")
    Call<BaseResponse<DataEntity>> getData(@Query("resource_id") String resourceID);

    @GET("/api/action/datastore_search")
    Call<BaseResponse<DataEntity>> getDataByWord(@Query("resource_id") String resourceID,
                                           @Query("q") String word);


}
