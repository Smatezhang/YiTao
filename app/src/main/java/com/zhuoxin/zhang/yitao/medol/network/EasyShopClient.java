package com.zhuoxin.zhang.yitao.medol.network;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

    /**
     * 网络请求客户端
     */
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

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public Call login(String username,String password){
        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.LOGIN)
                .post(body)
                .build();

        return mClient.newCall(request);
    }

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    public Call register(String username,String password){
        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.REGISTER)
                .post(body)
                .build();

        return mClient.newCall(request);
    }


    public Call loadGoods(int page,String type){
        FormBody body = new FormBody.Builder()
                .add("pageNo", ""+page)
                .add("type", type)
                .build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.GETGOODS)
                .post(body)
                .build();

        return mClient.newCall(request);

    }

}
