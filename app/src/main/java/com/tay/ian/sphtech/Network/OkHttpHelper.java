package com.tay.ian.sphtech.Network;

import android.util.Log;

import com.tay.ian.sphtech.Common.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author Ian
 *
 * Http call handling
 *
 **/
public class OkHttpHelper {

    private static final String TAG = OkHttpHelper.class.getSimpleName();
    private static RequestService requestService;

    public static void init() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().
                addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        String host = request.url().uri().getHost();
                        String path = request.url().uri().getPath();
                        int port = request.url().uri().getPort();
                        Request newRequest = request.newBuilder().build();
                        String querys =  newRequest.url().uri().getQuery();
                        Response response = chain.proceed(newRequest);
                        int statusCode = response.code();
                        boolean isSuccess = response.isSuccessful();
                        MediaType mediaType = response.body().contentType();
                        byte[] bodys = response.body().bytes();
                        String body = new String(bodys);


                        Response returnResponse = response.newBuilder().
                                body(ResponseBody.create(mediaType,bodys)).build();
                        Log.d(TAG, "URL:"+String.format("%s:%d%s?",host,port,path)+querys+", result"+body);
                        return returnResponse;
                    }
                }).connectTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS).readTimeout(Constants.HTTP_TIMEOUT,TimeUnit.MILLISECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        requestService = retrofit.create(RequestService.class);
    }

    public static RequestService getRequestService() {
        return requestService;
    }

}
