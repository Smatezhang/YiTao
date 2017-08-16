package com.zhuoxin.zhang.yitao.medol.network;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/15.
 */

public abstract class UICallBack implements Callback{

   Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * @param call
     * @param e
     */
    @Override
    public void onFailure(final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailureUI(call, e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        if (!response.isSuccessful()){
            throw  new IOException("请求失败！！！");
        }else {
            final String mString = response.body().string();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onResponseUI(call,mString);
                }
            });
        }

    }

   public abstract void onFailureUI(Call call, IOException e);
   public abstract void onResponseUI(Call call, String response);


}
