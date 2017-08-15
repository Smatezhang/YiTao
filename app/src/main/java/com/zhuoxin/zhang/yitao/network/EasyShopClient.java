package com.zhuoxin.zhang.yitao.network;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/8/15.
 */

public class EasyShopClient {
    private static EasyShopClient mEasyShopClient;
    private OkHttpClient mClient;
    private Gson mGson;
    protected Retrofit mRetrofit;

    public static EasyShopClient getInstance(){
        if (mEasyShopClient == null){
            synchronized (EasyShopClient.class){
                if (mEasyShopClient == null){
                    mEasyShopClient = new EasyShopClient();
                }
            }
        }
        return mEasyShopClient;
    }

    private  EasyShopClient(){
        //日志拦截器
        HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor();
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mClient = new OkHttpClient.Builder()
                .addInterceptor(mHttpLoggingInterceptor)
                .build();
        mGson = new Gson();
    }
    private  EasyShopClient(int i){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(EasyShopApi.BASE_URL)
                .build();

    }



}
